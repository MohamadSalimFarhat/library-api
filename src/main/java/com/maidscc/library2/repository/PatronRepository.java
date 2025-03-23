package com.maidscc.library2.repository;

import com.maidscc.library2.model.Patron;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatronRepository extends JpaRepository<Patron, Long> {
}