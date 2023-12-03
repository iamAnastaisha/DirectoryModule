package org.example.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class VisasResponse {
    private List<VisaDTO> visas;
}