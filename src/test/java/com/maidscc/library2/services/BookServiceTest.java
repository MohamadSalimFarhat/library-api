package com.maidscc.library2.services;

import com.maidscc.library2.exception.ResourceNotFoundException;
import com.maidscc.library2.model.Book;
import com.maidscc.library2.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize mocks
    }

    @Test
    void getAllBooks() {
        // Arrange
        Book book1 = new Book();
        book1.setId(1L);
        book1.setTitle("The Great Gatsby");

        Book book2 = new Book();
        book2.setId(2L);
        book2.setTitle("1984");

        when(bookRepository.findAll()).thenReturn(Arrays.asList(book1, book2));

        // Act
        List<Book> books = bookService.getAllBooks();

        // Assert
        assertEquals(2, books.size());
        verify(bookRepository, times(1)).findAll();
    }

    @Test
    void getBookById() {
        // Arrange
        Book book = new Book();
        book.setId(1L);
        book.setTitle("The Great Gatsby");

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        // Act
        Book foundBook = bookService.getBookById(1L);

        // Assert
        assertNotNull(foundBook);
        assertEquals("The Great Gatsby", foundBook.getTitle());
        verify(bookRepository, times(1)).findById(1L);
    }

    @Test
    void getBookById_NotFound() {
        // Arrange
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> bookService.getBookById(1L));
        verify(bookRepository, times(1)).findById(1L);
    }

    @Test
    void saveBook() {
        // Arrange
        Book book = new Book();
        book.setTitle("The Great Gatsby");

        when(bookRepository.save(book)).thenReturn(book);

        // Act
        Book savedBook = bookService.saveBook(book);

        // Assert
        assertNotNull(savedBook);
        assertEquals("The Great Gatsby", savedBook.getTitle());
        verify(bookRepository, times(1)).save(book);
    }

    @Test
    void deleteBook() {
        // Arrange
        Book book = new Book();
        book.setId(1L);

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        doNothing().when(bookRepository).deleteById(1L);

        // Act
        bookService.deleteBook(1L);

        // Assert
        verify(bookRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteBook_NotFound() {
        // Arrange
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> bookService.deleteBook(1L));
        verify(bookRepository, times(1)).findById(1L);
        verify(bookRepository, never()).deleteById(1L);
    }
}