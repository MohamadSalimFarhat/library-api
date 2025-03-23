package com.maidscc.library2.controller;

import com.maidscc.library2.exception.ResourceNotFoundException;
import com.maidscc.library2.model.Patron;
import com.maidscc.library2.services.PatronService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/patrons")
public class PatronController {
    @Autowired
    private PatronService patronService;
    
    @GetMapping
    public ResponseEntity<List<Patron>> getAllPatrons() {
        List<Patron> patrons = patronService.getAllPatrons();
        return ResponseEntity.ok(patrons);
    }

    
    @GetMapping("/{id}")
    public ResponseEntity<?> getPatronById(@PathVariable("id") Long id) {
        Patron existingPatron = patronService.getPatronById(id);
        if (existingPatron == null) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Patron not found with id: " + id);
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.ok(existingPatron);
    }

    @PostMapping
    public ResponseEntity<Patron> addPatron(@RequestBody Patron patron) {
        Patron savedPatron = patronService.savePatron(patron);
        return ResponseEntity.status(201).body(savedPatron);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Patron> updatePatron(@PathVariable("id") Long id, @RequestBody Patron patron) {
        // Check if the patron exists
        Patron existingPatron = patronService.getPatronById(id);
        if (existingPatron == null) {
            throw new ResourceNotFoundException("Patron not found with id: " + id + " to update");
        }
        // Update the patron
        patron.setId(id);
        Patron updatedPatron = patronService.savePatron(patron);
        return ResponseEntity.ok(updatedPatron);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePatron(@PathVariable("id") Long id) {
        boolean isDeleted = patronService.deletePatron(id);
        if (isDeleted) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Patron with id: " + id + " deleted");
            return ResponseEntity.ok(response);
        } else {
            Map<String, String> response = new HashMap<>();
            response.put("message", "No patron with id: " + id + " to delete");
            return ResponseEntity.ok(response);
        }
    }
}