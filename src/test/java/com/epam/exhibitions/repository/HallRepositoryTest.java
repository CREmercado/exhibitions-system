package com.epam.exhibitions.repository;

import com.epam.exhibitions.entity.Exhibition;
import com.epam.exhibitions.entity.Hall;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HallRepositoryTest {

    @Mock
    private HallRepository hallRepository;
    private Hall hall;

    @BeforeEach
    void setUp() {
        hall = new Hall(1L, "Hall", "Dallas", "USA", new HashSet<Exhibition>());
    }

    @Test
    void findHallByHallName_shouldReturnNotNull_shouldReturnHall() {
        when(hallRepository.findHallByHallName(hall.getHallName())).thenReturn(hall);
        assertNotNull(hallRepository.findHallByHallName(hall.getHallName()));
        assertEquals(hall, hallRepository.findHallByHallName(hall.getHallName()));
    }
}