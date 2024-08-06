package com.example.Library_Management_System.controller;


import com.example.Library_Management_System.entity.BorrowingRecord;
import com.example.Library_Management_System.service.BorrowingRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class BorrowingRecordController {
    @Autowired
    private BorrowingRecordService borrowingRecordService;

    @PostMapping("/api/borrow/{bookId}/patron/{patronId}")
    public BorrowingRecord borrowBook(@PathVariable Long bookId, @PathVariable Long patronId) {
        return borrowingRecordService.borrowBook(bookId, patronId);
    }

    @PutMapping("/api/return/{bookId}/patron/{patronId}")
    public BorrowingRecord returnBook(@PathVariable Long bookId, @PathVariable Long patronId) {
        return borrowingRecordService.returnBook(bookId, patronId);
    }
}

