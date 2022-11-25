package com.epam.exhibitions.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "halls")
public class Hall {

    // variables or columns
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long hallId;
    private String hallName;
    private String hallCity;
    private String hallCountry;
    @ManyToMany(mappedBy = "halls")
    @JsonIgnore
    private Set<Exhibition> exhibitions;

}
