package com.maidscc.library2.controller;

import com.maidscc.library2.model.BorrowingRecord;
import com.maidscc.library2.services.BorrowingRecordService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BorrowingController.class)
class BorrowingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BorrowingRecordService borrowingRecordService;

    @Test
    void borrowBook() throws Exception {
        // Arrange
        BorrowingRecord borrowingRecord = new BorrowingRecord();
        borrowingRecord.setBorrowingDate(LocalDate.now());

        when(borrowingRecordService.borrowBook(1L, 1L)).thenReturn(borrowingRecord);

        // Act & Assert
        mockMvc.perform(post("/api/borrow/1/patron/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.borrowingDate").exists());
        verify(borrowingRecordService, times(1)).borrowBook(1L, 1L);
    }

    @Test
    void returnBook() throws Exception {
        // Arrange
        BorrowingRecord borrowingRecord = new BorrowingRecord();
        borrowingRecord.setReturnDate(LocalDate.now());

        when(borrowingRecordService.returnBook(1L, 1L)).thenReturn(borrowingRecord);

        // Act & Assert
        mockMvc.perform(put("/api/borrow/return/1/patron/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.returnDate").exists());
        verify(borrowingRecordService, times(1)).returnBook(1L, 1L);
    }
}
