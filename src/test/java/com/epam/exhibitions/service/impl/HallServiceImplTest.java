package com.epam.exhibitions.service.impl;

import com.epam.exhibitions.entity.*;
import com.epam.exhibitions.repository.HallRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HallServiceImplTest {

    @Mock
    private HallRepository hallRepository;

    @InjectMocks
    private HallServiceImpl hallServiceImpl;

    private Hall hall;

    private Exhibition exhibition;

    @BeforeEach
    void setUp() {
        hall = new Hall(1L, "Hall", "Dallas", "USA", new HashSet<Exhibition>(Collections.singletonList(exhibition)));
        exhibition = new Exhibition(1L, "Art", LocalDate.now(), LocalDate.now().plusMonths(5L),
                LocalDateTime.now(), LocalDateTime.now().plusHours(8L), 400.5, Boolean.TRUE,
                new HashSet<>());
    }

    @Test
    void listHalls_shouldReturnNotNull_shouldReturnHalls() {
        when(hallRepository.findAll()).thenReturn(Collections.singletonList(hall));
        assertNotNull(hallServiceImpl.listHalls());
        assertEquals(Collections.singletonList(hall), hallServiceImpl.listHalls());
    }

    @Test
    void getHallById_shouldReturnNotNull_shouldReturnHall() {
        when(hallRepository.findById(hall.getHallId())).thenReturn(Optional.ofNullable(hall));
        assertNotNull(hallServiceImpl.getHallById(hall.getHallId()));
        assertEquals(hall, hallServiceImpl.getHallById(hall.getHallId()));
    }

    @Test
    void getHallByName_shouldReturnNotNull_shouldReturnHall() {
        when(hallRepository.findHallByHallName(hall.getHallName())).thenReturn(hall);
        assertNotNull(hallServiceImpl.getHallByName(hall.getHallName()));
        assertEquals(hall, hallServiceImpl.getHallByName(hall.getHallName()));
    }

    @Test
    void addHall_shouldNotChangeUser_shouldThrowException() throws Exception {
        // test for checking the not changing of main object
        Hall hallSpy = Mockito.spy(hall);
        when(hallRepository.save(hall)).thenReturn(hallSpy);
        hallServiceImpl.addHall(hall);
        assertEquals(hallSpy.getHallId(), hall.getHallId());

        // test for checking the trowing fo exception
        HallServiceImpl hallServiceImplMock =  Mockito.mock(HallServiceImpl.class);
        doThrow(Exception.class).when(hallServiceImplMock).addHall(isNull());
        assertThrows(Exception.class, () -> hallServiceImplMock.addHall(isNull()));
    }
}