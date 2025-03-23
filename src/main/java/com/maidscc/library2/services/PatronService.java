package com.maidscc.library2.services;

import com.maidscc.library2.model.Book;
import com.maidscc.library2.model.Patron;
import com.maidscc.library2.repository.PatronRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatronService {
    @Autowired
    private PatronRepository patronRepository;

    public List<Patron> getAllPatrons() {
        return patronRepository.findAll();
    }

    public Patron getPatronById(Long id) {
        return patronRepository.findById(id).orElse(null);
    }

    public Patron savePatron(Patron patron) {
        return patronRepository.save(patron);
    }
    

    public boolean deletePatron(Long id) {
    	Optional<Patron> bookOptional = patronRepository.findById(id);
        if (bookOptional.isPresent()) {
            patronRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}