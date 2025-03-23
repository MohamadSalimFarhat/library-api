package com.maidscc.library2.controller;

import com.maidscc.library2.exception.ResourceNotFoundException;
import com.maidscc.library2.model.BorrowingRecord;
import com.maidscc.library2.services.BorrowingRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/borrow")
public class BorrowingController {
    private final BorrowingRecordService borrowingRecordService;

    @Autowired
    public BorrowingController(BorrowingRecordService borrowingRecordService) {
        this.borrowingRecordService = borrowingRecordService;
    }

    @PostMapping("/{bookId}/patron/{patronId}")
    public ResponseEntity<BorrowingRecord> borrowBook(
        @PathVariable("bookId") Long bookId,
        @PathVariable("patronId") Long patronId
    ) {
        BorrowingRecord borrowingRecord = borrowingRecordService.borrowBook(bookId, patronId);
        return ResponseEntity.ok(borrowingRecord);
    }

    @PutMapping("/return/{bookId}/patron/{patronId}")
    public ResponseEntity<BorrowingRecord> returnBook(
        @PathVariable("bookId") Long bookId,
        @PathVariable("patronId") Long patronId
    ) {
        BorrowingRecord borrowingRecord = borrowingRecordService.returnBook(bookId, patronId);
        return ResponseEntity.ok(borrowingRecord);
    }
}