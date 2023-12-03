package com.bsu.garkavaya.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class VisaDTO {
    private Long id;
    private Date issueDate;

    private Double validityPeriod;

    private Character type;

    private String countries;

    private PersonDTO person;
}
