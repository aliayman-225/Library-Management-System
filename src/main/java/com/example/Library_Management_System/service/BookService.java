package com.example.Library_Management_System.service;

import com.example.Library_Management_System.entity.Book;
import com.example.Library_Management_System.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;


@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    @Cacheable("books")
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Cacheable(value = "books", key = "#id")
    public Optional<Book> getBookById(Long id) {
        return Optional.ofNullable(bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Book not found with id " + id)));
    }

    @Transactional
    @CachePut(value = "books", key = "#book.id")
    public Book addBook(Book book) {
        return bookRepository.save(book);
    }

    @Transactional
    @CachePut(value = "books", key = "#id")
    public Book updateBook(Long id, Book updatedBook) {
        return bookRepository.findById(id)
                .map(book -> {
                    book.setTitle(updatedBook.getTitle());
                    book.setAuthor(updatedBook.getAuthor());
                    book.setPublicationYear(updatedBook.getPublicationYear());
                    book.setIsbn(updatedBook.getIsbn());
                    return bookRepository.save(book);
                })
                .orElseThrow(() -> new RuntimeException("Book not found with id " + id));
    }

    @Transactional
    @CacheEvict(value = "books", key = "#id")
    public void deleteBook(Long id) {
        //bookRepository.deleteById(id);
        if (!bookRepository.existsById(id)) {
            throw new RuntimeException("Book not found with id " + id);
        }
        bookRepository.deleteById(id);
    }
}









