package com.bsu.garkavaya.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class PeopleResponse {
    private List<PersonDTO> people;
}
