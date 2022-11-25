package com.epam.exhibitions.repository;

import com.epam.exhibitions.entity.Hall;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface HallRepository extends JpaRepository<Hall,Long> {

    @Query("FROM Hall a WHERE a.hallName = :hallName")
    Hall findHallByHallName(String hallName);

}
