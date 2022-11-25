package com.epam.exhibitions.controller;

import com.epam.exhibitions.entity.*;
import com.epam.exhibitions.entity.model.Dates;
import com.epam.exhibitions.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.Validate;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping(value = "/api/exhibitions", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Slf4j
public class ExhibitionController {

    private final ExhibitionService exhibitionService;
    private final HallService hallService;

    @GetMapping
    public List<Exhibition> getExhibitions() {
        return exhibitionService.listExhibitions();
    }

    @GetMapping("/{exhibitionId}")
    public Exhibition getExhibitionById(@PathVariable(name = "exhibitionId") Long id) {
        Validate.isTrue(id != null, "Id should not be null");
        return exhibitionService.getExhibitionById(id);
    }

    @GetMapping("/active")
    public List<Exhibition> getActiveExhibitions() {
        return exhibitionService.listActiveExhibitionsServiceCall();
    }

    @GetMapping("/active/byDate")
    public List<Exhibition> getActiveExhibitionsFilterByDate(@Valid @RequestBody Dates dates) {
        return exhibitionService.listActiveExhibitionsFilterByDateServiceCall(dates.getStartDate(), dates.getEndDate());
    }

    @GetMapping("/halls/{exhibitionId}")
    public Set<Long> getExhibitionHallsById(@PathVariable(name = "exhibitionId") Long id) {
        Validate.isTrue(id != null, "Id should not be null");
        Set<Long> exhibitionHalls = new HashSet<>();
        var halls = exhibitionService.listExhibitionHallsByIdServiceCall(id);
        halls.forEach(x -> exhibitionHalls.add(x.getHallId()));
        return exhibitionHalls;
    }

    @PostMapping
    public ResponseEntity<String> addExhibition(@Valid @RequestBody Exhibition exhibition) throws Exception {
        var halls = exhibition.getHalls();
        exhibition.setHalls(new HashSet<>());
        for (Hall hall : halls) {
            var hll = hallService.getHallByName(hall.getHallName());
            if (hll != null) {
                hll.getExhibitions().add(exhibition);
                exhibition.getHalls().add(hll);
            } else {
                hallService.addHall(hall);
                exhibition.getHalls().add(hall);
            }
        }
        exhibitionService.addExhibition(exhibition);
        log.info("Exhibition: " + exhibition.getTheme() + ", saved successfully!");
        return ResponseEntity.ok("Exhibition is valid");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
