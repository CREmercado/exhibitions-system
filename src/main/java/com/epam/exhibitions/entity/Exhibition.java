package com.epam.exhibitions.entity;

import com.epam.exhibitions.entity.validator.LocalDateNotEmpty;
import com.epam.exhibitions.entity.validator.LocalDateTimeNotEmpty;
import lombok.*;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.*;
import java.util.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "exhibitions")
public class Exhibition {

    // variables or columns
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long exhibitionId;
    @NotEmpty(message = "Theme is mandatory")
    private String theme;
    @LocalDateNotEmpty
    private LocalDate startDate;
    @LocalDateNotEmpty
    private LocalDate endDate;
    @LocalDateTimeNotEmpty
    private LocalDateTime openingTime;
    @LocalDateTimeNotEmpty
    private LocalDateTime closingTime;
    @Range(max = 10000, min = 0, message = "Ticket price is mandatory. Enter a valid ticket price between 0 and 10,000")
    @NotNull(message = "Ticket price is mandatory. Enter a valid ticket price between 0 and 10,000")
    private Double ticketPrice;
    private Boolean state;
    @ManyToMany
    @JoinTable(
            name = "exhibitions_halls",
            joinColumns = @JoinColumn(name = "exhibition_id"),
            inverseJoinColumns = @JoinColumn(name = "hall_id")
    )
    @NotEmpty(message = "Halls are mandatory")
    private Set<Hall> halls;

}
