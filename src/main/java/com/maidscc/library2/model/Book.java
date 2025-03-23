package com.maidscc.library2.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String author;

    @Column(name = "publication_year", nullable = false)
    private int publicationYear;

    @Column(unique = true, nullable = false)
    private String isbn;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private boolean available;

    //update function for the available field based on quantity
    public void updateAvailability() {
        this.available = this.quantity > 0;
    }
}