package com.epam.exhibitions.service;

import com.epam.exhibitions.entity.Exhibition;
import com.epam.exhibitions.entity.Hall;

import java.time.LocalDate;
import java.util.List;

public interface ExhibitionService {

    List<Exhibition> listExhibitions();

    Exhibition getExhibitionById(Long id);

    List<Exhibition> listActiveExhibitionsServiceCall();

    List<Exhibition> listActiveExhibitionsFilterByDateServiceCall(LocalDate startDate, LocalDate endDate);

    List<Hall> listExhibitionHallsByIdServiceCall(Long id);

    void addExhibition(Exhibition exhibition) throws Exception;

    void setExhibitionState(Long id) throws Exception;

    void updateExhibition(Exhibition exhibition);

}
