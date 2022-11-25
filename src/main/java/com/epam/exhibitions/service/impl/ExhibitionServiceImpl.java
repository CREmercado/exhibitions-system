package com.epam.exhibitions.service.impl;

import com.epam.exhibitions.entity.Exhibition;
import com.epam.exhibitions.entity.Hall;
import com.epam.exhibitions.repository.ExhibitionRepository;
import com.epam.exhibitions.service.ExhibitionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@RequiredArgsConstructor
@Service
public class ExhibitionServiceImpl implements ExhibitionService {

    private final ExhibitionRepository exhibitionRepository;

    @Override
    public List<Exhibition> listExhibitions() {
        return exhibitionRepository.findAll();
    }

    @Override
    public Exhibition getExhibitionById(Long id) {
        Optional<Exhibition> optExhibit = exhibitionRepository.findById(id);
        return optExhibit.orElse(null);
    }

    @Override
    public List<Exhibition> listActiveExhibitionsServiceCall() {
        return exhibitionRepository.listActiveExhibitions();
    }

    @Override
    public List<Exhibition> listActiveExhibitionsFilterByDateServiceCall(LocalDate startDate, LocalDate endDate) {
        return exhibitionRepository.listActiveExhibitionsFilterByDate(startDate, endDate);
    }

    @Override
    public List<Hall> listExhibitionHallsByIdServiceCall(Long id) {
        return exhibitionRepository.listExhibitionHallsById(id);
    }

    @Override
    public void addExhibition(Exhibition exhibition) throws Exception {
        if(exhibition != null) {
            exhibitionRepository.save(exhibition);
        } else {
            throw new Exception("Not a valid hall to save");
        }
    }

    @Override
    public void setExhibitionState(Long id) throws Exception {
        if(id != null) {
            Optional<Exhibition> optExhibit = exhibitionRepository.findById(id);
            optExhibit.get().setState(!optExhibit.get().getState());
            exhibitionRepository.save(optExhibit.get());
        } else {
            throw new Exception("Id should not be null");
        }
    }

    @Override
    public void updateExhibition(Exhibition exhibition) {
        exhibitionRepository.save(exhibition);
    }
}
