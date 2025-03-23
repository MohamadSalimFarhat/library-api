package com.maidscc.library2.services;

import com.maidscc.library2.exception.ResourceNotFoundException;
import com.maidscc.library2.model.Book;
import com.maidscc.library2.model.BorrowingRecord;
import com.maidscc.library2.model.Patron;
import com.maidscc.library2.repository.BorrowingRecordRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BorrowingRecordServiceTest {

    @Mock
    private BorrowingRecordRepository borrowingRecordRepository;

    @Mock
    private BookService bookService;

    @Mock
    private PatronService patronService;

    @InjectMocks
    private BorrowingRecordService borrowingRecordService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize mocks
    }

    @Test
    void borrowBook() {
        // Arrange
        Book book = new Book();
        book.setId(1L);
        book.setAvailable(true);

        Patron patron = new Patron();
        patron.setId(1L);

        when(bookService.getBookById(1L)).thenReturn(book);
        when(patronService.getPatronById(1L)).thenReturn(patron);
        when(borrowingRecordRepository.findByBookIdAndPatronIdAndReturnDateIsNull(1L, 1L)).thenReturn(Optional.empty());

        BorrowingRecord borrowingRecord = new BorrowingRecord();
        borrowingRecord.setBook(book);
        borrowingRecord.setPatron(patron);
        borrowingRecord.setBorrowingDate(LocalDate.now());

        when(borrowingRecordRepository.save(any(BorrowingRecord.class))).thenReturn(borrowingRecord);

        // Act
        BorrowingRecord result = borrowingRecordService.borrowBook(1L, 1L);

        // Assert
        assertNotNull(result);
        assertEquals(book, result.getBook());
        assertEquals(patron, result.getPatron());
        verify(bookService, times(1)).getBookById(1L);
        verify(patronService, times(1)).getPatronById(1L);
        verify(borrowingRecordRepository, times(1)).save(any(BorrowingRecord.class));
    }

    @Test
    void returnBook() {
        // Arrange
        Book book = new Book();
        book.setId(1L);

        Patron patron = new Patron();
        patron.setId(1L);

        BorrowingRecord borrowingRecord = new BorrowingRecord();
        borrowingRecord.setBook(book);
        borrowingRecord.setPatron(patron);
        borrowingRecord.setBorrowingDate(LocalDate.now());

        when(borrowingRecordRepository.findByBookIdAndPatronIdAndReturnDateIsNull(1L, 1L)).thenReturn(Optional.of(borrowingRecord));
        when(borrowingRecordRepository.save(any(BorrowingRecord.class))).thenReturn(borrowingRecord);

        // Act
        BorrowingRecord result = borrowingRecordService.returnBook(1L, 1L);

        // Assert
        assertNotNull(result);
        assertNotNull(result.getReturnDate());
        verify(borrowingRecordRepository, times(1)).save(any(BorrowingRecord.class));
    }

    @Test
    void returnBook_NotFound() {
        // Arrange
        when(borrowingRecordRepository.findByBookIdAndPatronIdAndReturnDateIsNull(1L, 1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> borrowingRecordService.returnBook(1L, 1L));
        verify(borrowingRecordRepository, never()).save(any(BorrowingRecord.class));
    }
}