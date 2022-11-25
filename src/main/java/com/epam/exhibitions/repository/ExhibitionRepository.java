package com.epam.exhibitions.repository;

import com.epam.exhibitions.entity.Exhibition;
import com.epam.exhibitions.entity.Hall;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ExhibitionRepository extends JpaRepository<Exhibition,Long> {

    @Query("SELECT a FROM Exhibition a WHERE a.state = TRUE")
    List<Exhibition> listActiveExhibitions();
    @Query("SELECT a FROM Exhibition a WHERE a.state = TRUE AND a.startDate >= :startDate AND a.endDate <= :endDate")
    List<Exhibition> listActiveExhibitionsFilterByDate(LocalDate startDate, LocalDate endDate);
    @Query("SELECT a.halls FROM Exhibition a WHERE a.id = ?1")
    List<Hall> listExhibitionHallsById(Long id);

}
