package org.example.dto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PersonDTO {
    private String passportNumber;

    private String firstName;

    private String lastName;

    private Date birthDate;

    private String nationality;

    public PersonDTO(String passportNumber) {
        this.passportNumber = passportNumber;
    }
}
