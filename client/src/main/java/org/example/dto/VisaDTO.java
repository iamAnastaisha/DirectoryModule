package org.example.dto;

import lombok.*;
import org.springframework.lang.Nullable;

import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class VisaDTO {
    private Long id;
    private Date issueDate;
    private Double validityPeriod;
    private Character type;
    private String countries;
    private PersonDTO person;
}
