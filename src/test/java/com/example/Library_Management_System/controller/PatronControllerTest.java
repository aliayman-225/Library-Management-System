package com.example.Library_Management_System.controller;

import com.example.Library_Management_System.entity.Patron;
import com.example.Library_Management_System.service.PatronService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class PatronControllerTest {

    private MockMvc mockMvc;

    @Mock
    private PatronService patronService;

    @InjectMocks
    private PatronController patronController;

    public PatronControllerTest() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(patronController).build();
    }

    @Test
    void testGetAllPatrons() throws Exception {
        mockMvc.perform(get("/api/patrons"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetPatronById() throws Exception {
        Patron patron = new Patron();
        patron.setId(1L);
        patron.setName("John Doe");

        when(patronService.getPatronById(anyLong())).thenReturn(Optional.of(patron));

        mockMvc.perform(get("/api/patrons/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Doe"));
    }

    @Test
    public void testGetPatronByIdNotFound() throws Exception {
        when(patronService.getPatronById(anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/patrons/1"))
                .andExpect(status().isNotFound());

        verify(patronService, times(1)).getPatronById(1L);
    }

    @Test
    void testAddPatron() throws Exception {
        Patron patron = new Patron();
        patron.setName("Jane Doe");

        when(patronService.addPatron(any(Patron.class))).thenReturn(patron);

        mockMvc.perform(post("/api/patrons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Jane Doe\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Jane Doe"));
    }

    @Test
    void testUpdatePatron() throws Exception {
        Patron patron = new Patron();
        patron.setId(1L);
        patron.setName("Updated Name");

        when(patronService.updatePatron(anyLong(), any(Patron.class))).thenReturn(patron);

        mockMvc.perform(put("/api/patrons/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Updated Name\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Name"));
    }

    @Test
    void testDeletePatron() throws Exception {
        mockMvc.perform(delete("/api/patrons/1"))
                .andExpect(status().isNoContent());
    }
}
