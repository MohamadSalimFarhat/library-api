package com.maidscc.library2.controller;

import com.maidscc.library2.model.Patron;
import com.maidscc.library2.services.PatronService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PatronController.class)
class PatronControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PatronService patronService;

    @Test
    void getAllPatrons() throws Exception {
        when(patronService.getAllPatrons()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/patrons"))
            .andExpect(status().isOk())
            .andExpect(content().json("[]"));

        verify(patronService, times(1)).getAllPatrons();
    }


    @Test
    void getPatronById() throws Exception {
        // Arrange
        Patron patron = new Patron();
        patron.setId(1L);
        patron.setName("John Doe");

        when(patronService.getPatronById(1L)).thenReturn(patron);

        // Act & Assert
        mockMvc.perform(get("/api/patrons/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value("John Doe"));
        verify(patronService, times(1)).getPatronById(1L);
    }

    @Test
    void addPatron() throws Exception {
        // Arrange
        Patron patron = new Patron();
        patron.setName("John Doe");

        when(patronService.savePatron(any(Patron.class))).thenReturn(patron);

        // Act & Assert
        mockMvc.perform(post("/api/patrons")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"John Doe\"}"))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.name").value("John Doe"));
        verify(patronService, times(1)).savePatron(any(Patron.class));
    }

    @Test
    void deletePatron() throws Exception {
        // Arrange
        when(patronService.deletePatron(1L)).thenReturn(true);

        // Act & Assert
        mockMvc.perform(delete("/api/patrons/1"))
            .andExpect(status().isOk());

        verify(patronService, times(1)).deletePatron(1L);
    }


}