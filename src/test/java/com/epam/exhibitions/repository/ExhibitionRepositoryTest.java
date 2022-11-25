package com.epam.exhibitions.repository;

import com.epam.exhibitions.entity.Exhibition;
import com.epam.exhibitions.entity.Hall;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ExhibitionRepositoryTest {

    @Mock
    private ExhibitionRepository exhibitionRepository;
    private Exhibition exhibition;
    private Hall hall;

    @BeforeEach
    void setUp() {
        exhibition = new Exhibition(1L, "Art", LocalDate.now(), LocalDate.now().plusMonths(5L),
                LocalDateTime.now(), LocalDateTime.now().plusHours(8L), 400.5, Boolean.TRUE,
                new HashSet<>());
        hall = new Hall(1L, "Hall", "Dallas", "USA", new HashSet<Exhibition>(List.of(exhibition)));
    }

    @Test
    void listActiveExhibitions_shouldReturnNotNull_shouldReturnExhibitions() {
        when(exhibitionRepository.listActiveExhibitions()).thenReturn(Collections.singletonList(exhibition));
        assertNotNull(exhibitionRepository.listActiveExhibitions());
        assertEquals(Collections.singletonList(exhibition), exhibitionRepository.listActiveExhibitions());
    }

    @Test
    void listActiveExhibitionsFilterByDate_shouldReturnNotNull_shouldReturnExhibitions() {
        LocalDate startDate = LocalDate.now().minusMonths(10);
        LocalDate endDate = LocalDate.now().plusMonths(10);
        when(exhibitionRepository.listActiveExhibitionsFilterByDate(startDate, endDate)).thenReturn(Collections.singletonList(exhibition));
        assertNotNull(exhibitionRepository.listActiveExhibitionsFilterByDate(startDate, endDate));
        assertEquals(Collections.singletonList(exhibition), exhibitionRepository.listActiveExhibitionsFilterByDate(startDate, endDate));
    }

    @Test
    void listExhibitionHallsByID_shouldReturnNotNull_shouldReturnExhibitions() {
        Long id = 1L;
        when(exhibitionRepository.listExhibitionHallsById(id)).thenReturn(Collections.singletonList(hall));
        assertNotNull(exhibitionRepository.listExhibitionHallsById(id));
        assertEquals(Collections.singletonList(hall), exhibitionRepository.listExhibitionHallsById(id));
    }
}