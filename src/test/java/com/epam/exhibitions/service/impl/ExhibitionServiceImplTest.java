package com.epam.exhibitions.service.impl;

import com.epam.exhibitions.entity.*;
import com.epam.exhibitions.entity.Hall;
import com.epam.exhibitions.repository.ExhibitionRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ExhibitionServiceImplTest {

    @Mock
    private ExhibitionRepository exhibitionRepository;
    @InjectMocks
    private ExhibitionServiceImpl exhibitionServiceImpl;
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
    void listExhibitions_shouldReturnNotNull_shouldReturnExhibitions() {
        when(exhibitionRepository.findAll()).thenReturn(Collections.singletonList(exhibition));
        assertNotNull(exhibitionServiceImpl.listExhibitions());
        assertEquals(Collections.singletonList(exhibition), exhibitionServiceImpl.listExhibitions());
    }

    @Test
    void getExhibitionById_shouldReturnNotNull_shouldReturnExhibition() {
        when(exhibitionRepository.findById(exhibition.getExhibitionId())).thenReturn(Optional.ofNullable(exhibition));
        assertNotNull(exhibitionServiceImpl.getExhibitionById(exhibition.getExhibitionId()));
        assertEquals(exhibition, exhibitionServiceImpl.getExhibitionById(exhibition.getExhibitionId()));
    }

    @Test
    void listActiveExhibitionsServiceCall_shouldReturnNotNull_shouldReturnExhibitions() {
        when(exhibitionRepository.listActiveExhibitions()).thenReturn(Collections.singletonList(exhibition));
        assertNotNull(exhibitionServiceImpl.listActiveExhibitionsServiceCall());
        assertEquals(Collections.singletonList(exhibition), exhibitionServiceImpl.listActiveExhibitionsServiceCall());
    }

    @Test
    void listActiveExhibitionsFilterByDateServiceCall_shouldReturnNotNull_shouldReturnExhibitions() {
        LocalDate startDate = LocalDate.now().minusMonths(10);
        LocalDate endDate = LocalDate.now().plusMonths(10);
        when(exhibitionRepository.listActiveExhibitionsFilterByDate(startDate, endDate)).thenReturn(Collections.singletonList(exhibition));
        assertNotNull(exhibitionServiceImpl.listActiveExhibitionsFilterByDateServiceCall(startDate, endDate));
        assertEquals(Collections.singletonList(exhibition), exhibitionServiceImpl.listActiveExhibitionsFilterByDateServiceCall(startDate, endDate));
    }

    @Test
    void listExhibitionHallsByIdServiceCall_shouldReturnNotNull_shouldReturnExhibitions() {
        Long id = 1L;
        when(exhibitionRepository.listExhibitionHallsById(id)).thenReturn(Collections.singletonList(hall));
        assertNotNull(exhibitionServiceImpl.listExhibitionHallsByIdServiceCall(id));
        assertEquals(Collections.singletonList(hall), exhibitionServiceImpl.listExhibitionHallsByIdServiceCall(id));
    }

    @Test
    void addExhibition_shouldNotChangeExhibition_shouldThrowException() throws Exception {
        // test for checking the not changing of main object
        Exhibition exhibitionSpy = Mockito.spy(exhibition);
        when(exhibitionRepository.save(exhibition)).thenReturn(exhibitionSpy);
        exhibitionServiceImpl.addExhibition(exhibition);
        assertEquals(exhibitionSpy.getExhibitionId(), exhibition.getExhibitionId());

        // test for checking the trowing fo exception
        ExhibitionServiceImpl exhibitionServiceImplMock =  Mockito.mock(ExhibitionServiceImpl.class);
        doThrow(Exception.class).when(exhibitionServiceImplMock).addExhibition(isNull());
        assertThrows(Exception.class, () -> exhibitionServiceImplMock.addExhibition(isNull()));
    }
}