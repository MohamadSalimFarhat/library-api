package com.maidscc.library2.services;

import java.util.List;

import com.maidscc.library2.exception.ResourceNotFoundException;
import com.maidscc.library2.model.Book;
import com.maidscc.library2.repository.BookRepository;
import com.maidscc.library2.repository.BorrowingRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BorrowingRecordRepository borrowingRecordRepository;

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book getBookById(Long id) {
        return bookRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));
    }
    
    public Book getBookByIsbn(String isbn) {
        return bookRepository.findByIsbn(isbn)
            .orElseThrow(() -> new ResourceNotFoundException("Book not found with ISBN: " + isbn));
    }

    public Book saveBook(Book book) {
        Optional<Book> existingBook = bookRepository.findByIsbn(book.getIsbn());
        if (existingBook.isPresent()) {
            throw new ResourceNotFoundException("A book with ISBN " + book.getIsbn() + " already exists.");
        }
        
        return bookRepository.save(book);
    }

    public void deleteBook(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));

        // Check if the book is being borrowed by a patron
        if (isBookBorrowed(book)) {
            throw new IllegalStateException("This book is currently borrowed by a patron and cannot be deleted.");
        }

        bookRepository.deleteById(id);
    }

    // A method to check if the book is borrowed
    private boolean isBookBorrowed(Book book) {
        // Check if there is any active borrowing record
        return borrowingRecordRepository.existsByBookIdAndReturnDateIsNull(book.getId());
    }

    // Method to decrement quantity and update availability
    public void decrementQuantity(Long bookId) {
        Book book = getBookById(bookId);
        if (book.getQuantity() <= 0) {
            throw new IllegalStateException("No available copies of the book.");
        }
        book.setQuantity(book.getQuantity() - 1);
        book.updateAvailability();
        bookRepository.save(book);
    }

    // Method to increment quantity and update availability
    public void incrementQuantity(Long bookId) {
        Book book = getBookById(bookId);
        book.setQuantity(book.getQuantity() + 1);
        book.updateAvailability();
        bookRepository.save(book);
    }
}
