package com.maidscc.library2.controller;

import com.maidscc.library2.model.Book;
import com.maidscc.library2.services.BookService;
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

@WebMvcTest(BookController.class)
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @Test
    void getAllBooks() throws Exception {
        // Arrange
        when(bookService.getAllBooks()).thenReturn(Collections.emptyList());

        // Act & Assert
        mockMvc.perform(get("/api/books"))
            .andExpect(status().isOk())
            .andExpect(content().json("[]"));
        verify(bookService, times(1)).getAllBooks();
    }

    @Test
    void getBookById() throws Exception {
        // Arrange
        Book book = new Book();
        book.setId(1L);
        book.setTitle("The Great Gatsby");

        when(bookService.getBookById(1L)).thenReturn(book);

        // Act & Assert
        mockMvc.perform(get("/api/books/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.title").value("The Great Gatsby"));
        verify(bookService, times(1)).getBookById(1L);
    }

    @Test
    void addBook() throws Exception {
        // Arrange
        Book book = new Book();
        book.setTitle("The Great Gatsby");

        when(bookService.saveBook(any(Book.class))).thenReturn(book);

        // Act & Assert
        mockMvc.perform(post("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"title\": \"The Great Gatsby\"}"))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.title").value("The Great Gatsby"));
        verify(bookService, times(1)).saveBook(any(Book.class));
    }
}