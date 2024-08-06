package com.example.Library_Management_System.repository;

import com.example.Library_Management_System.entity.Patron;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatronRepository extends JpaRepository<Patron, Long> {
}
