package com.maidscc.library2.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Patron {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(name = "contact_information", nullable = false)
    private String contactInformation;
}