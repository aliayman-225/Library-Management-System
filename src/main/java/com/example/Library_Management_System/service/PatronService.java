package com.example.Library_Management_System.service;

import com.example.Library_Management_System.entity.Patron;
import com.example.Library_Management_System.repository.PatronRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class PatronService {
    @Autowired
    private PatronRepository patronRepository;

    @Cacheable("patrons")
    public List<Patron> getAllPatrons() {
        return patronRepository.findAll();
    }

    @Cacheable(value = "patrons", key = "#id")
    public Optional<Patron> getPatronById(Long id) {
        return Optional.ofNullable(patronRepository.findById(id).orElseThrow(() -> new RuntimeException("Patron not found with id " + id)));

    }

    @Transactional
    @CachePut(value = "patrons", key = "#patron.id")
    public Patron addPatron(Patron patron) {
        return patronRepository.save(patron);
    }

    @Transactional
    @CachePut(value = "patrons", key = "#id")
    public Patron updatePatron(Long id, Patron updatedPatron) {
        return patronRepository.findById(id)
                .map(patron -> {
                    patron.setName(updatedPatron.getName());
                    patron.setContactInfo(updatedPatron.getContactInfo());
                    return patronRepository.save(patron);
                })
                .orElseThrow(() -> new RuntimeException("Patron not found with id " + id));
    }

    @Transactional
    @CacheEvict(value = "patrons", key = "#id")
    public void deletePatron(Long id) {
        //patronRepository.deleteById(id);
        if (!patronRepository.existsById(id)) {
            throw new RuntimeException("Patron not found with id " + id);
        }
        patronRepository.deleteById(id);
    }
}

