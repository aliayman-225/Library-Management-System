package com.example.Library_Management_System.controller;

import com.example.Library_Management_System.entity.Book;
import com.example.Library_Management_System.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class BookControllerTest {

    private MockMvc mockMvc;

    @Mock
    private BookService bookService;

    @InjectMocks
    private BookController bookController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();
    }

    @Test
    void testGetAllBooks() throws Exception {
        mockMvc.perform(get("/api/books"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetBookById() throws Exception {
        Book book = new Book();
        book.setId(1L);
        book.setTitle("Test Book");

        when(bookService.getBookById(anyLong())).thenReturn(Optional.of(book));

        mockMvc.perform(get("/api/books/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Test Book"));
    }

    @Test
    public void testGetBookByIdNotFound() throws Exception {
        when(bookService.getBookById(anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/books/1"))
                .andExpect(status().isNotFound());

        verify(bookService, times(1)).getBookById(1L);
    }

    @Test
    void testAddBook() throws Exception {
        Book book = new Book();
        book.setTitle("New Book");

        when(bookService.addBook(any(Book.class))).thenReturn(book);

        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"New Book\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("New Book"));
    }

    @Test
    void testUpdateBook() throws Exception {
        Book book = new Book();
        book.setId(1L);
        book.setTitle("Updated Book");

        when(bookService.updateBook(anyLong(), any(Book.class))).thenReturn(book);

        mockMvc.perform(put("/api/books/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"Updated Book\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated Book"));
    }

    @Test
    void testDeleteBook() throws Exception {
        mockMvc.perform(delete("/api/books/1"))
                .andExpect(status().isNoContent());
    }


}
