package com.bsu.garkavaya.controller;

import com.bsu.garkavaya.dto.PeopleIdsResponse;
import com.bsu.garkavaya.dto.PeopleResponse;
import com.bsu.garkavaya.dto.PersonDTO;
import com.bsu.garkavaya.model.Person;
import com.bsu.garkavaya.repository.PeopleRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/people")
@AllArgsConstructor
public class PeopleController {
    private final PeopleRepository peopleRepository;
    private final ModelMapper modelMapper;

    @GetMapping("/all")
    public PeopleResponse getAll() {
        return new PeopleResponse(peopleRepository.findAll()
                .stream()
                .map(this::convertToPersonDTO)
                .collect(Collectors.toList()));
    }

    @GetMapping("/allIds")
    public PeopleIdsResponse getAllIds() {
        return new PeopleIdsResponse(peopleRepository.findAllIds());
    }

    @PostMapping("/update") // also can be used for creating
    public void updatePerson(@RequestBody PersonDTO personDTO) {
        //System.out.println(personDTO.toString());
        peopleRepository.save(convertToPerson(personDTO));
    }

    @PostMapping("/delete/{id}")
    public void deletePerson(@PathVariable("id") String id) {
        peopleRepository.deleteById(id);
    }

    private Person convertToPerson(PersonDTO personDTO) {
        return modelMapper.map(personDTO, Person.class);
    }

    private PersonDTO convertToPersonDTO(Person person) {
        return modelMapper.map(person, PersonDTO.class);
    }
}
