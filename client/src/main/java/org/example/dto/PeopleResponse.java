package org.example.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class PeopleResponse {
    private List<PersonDTO> people;
}
