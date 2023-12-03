package com.bsu.garkavaya.controller;

import com.bsu.garkavaya.dto.VisaDTO;
import com.bsu.garkavaya.dto.VisasResponse;
import com.bsu.garkavaya.model.Visa;
import com.bsu.garkavaya.repository.VisasRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/visas")
@AllArgsConstructor
public class VisasController {

    private final VisasRepository visasRepository;
    private final ModelMapper modelMapper;

    @GetMapping("/all")
    public VisasResponse getAll() {
        return new VisasResponse(visasRepository.findAll()
                .stream()
                .map(this::convertToVisaDTO)
                .collect(Collectors.toList()));
    }

    @PostMapping("/update") // also can be used for creating
    public void updateVisa(@RequestBody VisaDTO visaDTO) {
        visasRepository.save(convertToVisa(visaDTO));
    }

    @PostMapping("/delete/{id}")
    public void deleteVisa(@PathVariable("id") Long id) {
        visasRepository.deleteById(id);
    }

    private Visa convertToVisa(VisaDTO visaDTO) {
        return modelMapper.map(visaDTO, Visa.class);
    }

    private VisaDTO convertToVisaDTO(Visa visa) {
        return modelMapper.map(visa, VisaDTO.class);
    }
}
