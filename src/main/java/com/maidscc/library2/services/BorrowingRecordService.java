package com.maidscc.library2.services;

import com.maidscc.library2.exception.ResourceNotFoundException;
import com.maidscc.library2.model.Book;
import com.maidscc.library2.model.BorrowingRecord;
import com.maidscc.library2.model.Patron;
import com.maidscc.library2.repository.BorrowingRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class BorrowingRecordService {
    private final BorrowingRecordRepository borrowingRecordRepository;
    private final BookService bookService;
    private final PatronService patronService;

    @Autowired
    public BorrowingRecordService(
        BorrowingRecordRepository borrowingRecordRepository,
        BookService bookService,
        PatronService patronService
    ) {
        this.borrowingRecordRepository = borrowingRecordRepository;
        this.bookService = bookService;
        this.patronService = patronService;
    }

    @Transactional
    public BorrowingRecord borrowBook(Long bookId, Long patronId) {
        // Check if the book and patron exist
        Book book = bookService.getBookById(bookId);
        Patron patron = patronService.getPatronById(patronId);

        if (!book.isAvailable()) {
            throw new IllegalStateException("Book is not available for borrowing.");
        }

        // cehck if the patron has already borrowed this book and not returned it
        Optional<BorrowingRecord> existingRecordForPatron = borrowingRecordRepository
            .findByBookIdAndPatronIdAndReturnDateIsNull(bookId, patronId);

        if (existingRecordForPatron.isPresent()) {
            throw new IllegalStateException("Patron has already borrowed this book and not returned it.");
        }

        bookService.decrementQuantity(bookId);

        // Create a new borrowing record
        BorrowingRecord borrowingRecord = new BorrowingRecord();
        borrowingRecord.setBook(book);
        borrowingRecord.setPatron(patron);
        borrowingRecord.setBorrowingDate(LocalDate.now());
        borrowingRecord.setReturnDate(null);

        // save the borrowing record and return it
        return borrowingRecordRepository.save(borrowingRecord);
    }


    @Transactional
    public BorrowingRecord returnBook(Long bookId, Long patronId) {
        // Check if the book and patron exist
        bookService.getBookById(bookId);
        patronService.getPatronById(patronId);

        Optional<BorrowingRecord> existingRecord = borrowingRecordRepository
            .findByBookIdAndPatronIdAndReturnDateIsNull(bookId, patronId);

        if (existingRecord.isEmpty()) {
            throw new ResourceNotFoundException("No active borrowing record found for this book and patron.");
        }

        BorrowingRecord record = existingRecord.get();
        record.setReturnDate(LocalDate.now());

        bookService.incrementQuantity(bookId);

        return borrowingRecordRepository.save(record);
    }
}