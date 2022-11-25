package com.epam.exhibitions.entity.model;

import com.epam.exhibitions.entity.validator.LocalDateNotEmpty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Dates {

    // variables
    @LocalDateNotEmpty
    private LocalDate startDate;
    @LocalDateNotEmpty
    private LocalDate endDate;

}
