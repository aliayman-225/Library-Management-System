package com.example.Library_Management_System.service;

import com.example.Library_Management_System.entity.Book;
import com.example.Library_Management_System.entity.BorrowingRecord;
import com.example.Library_Management_System.entity.Patron;
import com.example.Library_Management_System.repository.BookRepository;
import com.example.Library_Management_System.repository.BorrowingRecordRepository;
import com.example.Library_Management_System.repository.PatronRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class BorrowingRecordService {

    @Autowired
    private BorrowingRecordRepository borrowingRecordRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private PatronRepository patronRepository;

    @Transactional
    public BorrowingRecord borrowBook(Long bookId, Long patronId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found with id " + bookId));
        Patron patron = patronRepository.findById(patronId)
                .orElseThrow(() -> new RuntimeException("Patron not found with id " + patronId));

        // Check if the book is already borrowed and not returned
        BorrowingRecord existingRecord = borrowingRecordRepository.findByBookIdAndReturnedAtIsNull(bookId);
        if (existingRecord != null) {
            throw new RuntimeException("Book with id " + bookId + " is already borrowed and not returned.");
        }

        BorrowingRecord record = new BorrowingRecord();
        record.setBook(book);
        record.setPatron(patron);
        record.setBorrowedAt(LocalDateTime.now());

        return borrowingRecordRepository.save(record);
    }

    @Transactional
    public BorrowingRecord returnBook(Long bookId, Long patronId) {
        BorrowingRecord record = borrowingRecordRepository.findByBookIdAndPatronId(bookId, patronId)
                .orElseThrow(() -> new RuntimeException("Borrowing record not found for bookId " + bookId + " and patronId " + patronId));

        record.setReturnedAt(LocalDateTime.now());

        return borrowingRecordRepository.save(record);
    }
}
