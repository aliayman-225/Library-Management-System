package com.example.Library_Management_System.repository;

import com.example.Library_Management_System.entity.BorrowingRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BorrowingRecordRepository extends JpaRepository<BorrowingRecord, Long> {
    Optional<BorrowingRecord> findByBookIdAndPatronId(Long bookId, Long patronId);

    BorrowingRecord findByBookIdAndReturnedAtIsNull(Long bookId);
}
