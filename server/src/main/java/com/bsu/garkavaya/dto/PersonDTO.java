package com.bsu.garkavaya.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class PersonDTO {
    private String passportNumber;

    private String firstName;

    private String lastName;

    private Date birthDate;

    private String nationality;
}
