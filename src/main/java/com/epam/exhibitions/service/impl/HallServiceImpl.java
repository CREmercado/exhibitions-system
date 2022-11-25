package com.epam.exhibitions.service.impl;

import com.epam.exhibitions.entity.Hall;
import com.epam.exhibitions.repository.HallRepository;
import com.epam.exhibitions.service.HallService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class HallServiceImpl implements HallService {

    private final HallRepository hallRepository;

    @Override
    public List<Hall> listHalls() {
        List<Hall> halls = hallRepository.findAll();
        return halls;
    }

    @Override
    public Hall getHallById(Long id) {
        Optional<Hall> optHall = hallRepository.findById(id);
        return optHall.orElse(null);
    }

    @Override
    public Hall getHallByName(String hallName) {
        Hall hall = hallRepository.findHallByHallName(hallName);
        return hall;
    }

    @Override
    public void addHall(Hall hall) throws Exception {
        if(hall != null) {
            hallRepository.save(hall);
        } else {
            throw new Exception("Not a valid hall to save");
        }
    }

}
