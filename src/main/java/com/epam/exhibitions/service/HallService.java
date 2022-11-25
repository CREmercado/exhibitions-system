package com.epam.exhibitions.service;


import com.epam.exhibitions.entity.Hall;

import java.util.List;

public interface HallService {

    List<Hall> listHalls();

    Hall getHallById(Long id);

    Hall getHallByName(String hallName);

    void addHall(Hall hall) throws Exception;

}
