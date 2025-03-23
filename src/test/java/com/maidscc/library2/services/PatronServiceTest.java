package com.maidscc.library2.services;

import com.maidscc.library2.exception.ResourceNotFoundException;
import com.maidscc.library2.model.Patron;
import com.maidscc.library2.repository.PatronRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PatronServiceTest {

    @Mock
    private PatronRepository patronRepository;

    @InjectMocks
    private PatronService patronService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllPatrons() {
        // Arrange
        Patron patron1 = new Patron();
        patron1.setId(1L);
        patron1.setName("John Doe");

        Patron patron2 = new Patron();
        patron2.setId(2L);
        patron2.setName("Jane Doe");

        when(patronRepository.findAll()).thenReturn(Arrays.asList(patron1, patron2));

        // Act
        List<Patron> patrons = patronService.getAllPatrons();

        // Assert
        assertEquals(2, patrons.size());
        verify(patronRepository, times(1)).findAll();
    }

    @Test
    void getPatronById() {
        // Arrange
        Patron patron = new Patron();
        patron.setId(1L);
        patron.setName("John Doe");

        when(patronRepository.findById(1L)).thenReturn(Optional.of(patron));

        // Act
        Patron foundPatron = patronService.getPatronById(1L);

        // Assert
        assertNotNull(foundPatron);
        assertEquals("John Doe", foundPatron.getName());
        verify(patronRepository, times(1)).findById(1L);
    }

    @Test
    void getPatronById_NotFound() {
        // Arrange
        when(patronRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> patronService.getPatronById(1L));
        verify(patronRepository, times(1)).findById(1L);
    }
    @Test
    void savePatron() {
        // Arrange
        Patron patron = new Patron();
        patron.setName("John Doe");

        when(patronRepository.save(patron)).thenReturn(patron);

        // Act
        Patron savedPatron = patronService.savePatron(patron);

        // Assert
        assertNotNull(savedPatron);
        assertEquals("John Doe", savedPatron.getName());
        verify(patronRepository, times(1)).save(patron);
    }

    @Test
    void deletePatron() {
        // Arrange
        Patron patron = new Patron();
        patron.setId(1L);

        when(patronRepository.findById(1L)).thenReturn(Optional.of(patron));
        doNothing().when(patronRepository).deleteById(1L);

        // Act
        patronService.deletePatron(1L);

        // Assert
        verify(patronRepository, times(1)).deleteById(1L);
    }

    @Test
    void deletePatron_NotFound() {
        // Arrange
        when(patronRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> patronService.deletePatron(1L));
        verify(patronRepository, times(1)).findById(1L);
        verify(patronRepository, never()).deleteById(1L);
    }
}