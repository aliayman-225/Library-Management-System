package com.example.Library_Management_System.controller;


import com.example.Library_Management_System.entity.BorrowingRecord;
import com.example.Library_Management_System.service.BorrowingRecordService;
import com.example.Library_Management_System.exception.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class BorrowingRecordControllerTest {

    private MockMvc mockMvc;

    @Mock
    private BorrowingRecordService borrowingRecordService;

    @InjectMocks
    private BorrowingRecordController borrowingRecordController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(borrowingRecordController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    public void testBorrowBook_Success() throws Exception {
        BorrowingRecord record = new BorrowingRecord();
        record.setId(1L);

        when(borrowingRecordService.borrowBook(1L, 1L)).thenReturn(record);

        mockMvc.perform(post("/api/borrow/1/patron/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    public void testBorrowBook_BookAlreadyBorrowed() throws Exception {
        when(borrowingRecordService.borrowBook(2L, 1L))
                .thenThrow(new RuntimeException("Book with id 2 is already borrowed and not returned."));

        mockMvc.perform(post("/api/borrow/2/patron/1"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message").value("Book with id 2 is already borrowed and not returned."));
    }

    @Test
    public void testReturnBook_Success() throws Exception {
        BorrowingRecord record = new BorrowingRecord();
        record.setId(1L);

        when(borrowingRecordService.returnBook(1L, 1L)).thenReturn(record);

        mockMvc.perform(put("/api/return/1/patron/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    public void testReturnBook_NotFound() throws Exception {
        when(borrowingRecordService.returnBook(2L, 1L))
                .thenThrow(new RuntimeException("Borrowing record not found for bookId 2 and patronId 1"));

        mockMvc.perform(put("/api/return/2/patron/1"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message").value("Borrowing record not found for bookId 2 and patronId 1"));
    }
}
