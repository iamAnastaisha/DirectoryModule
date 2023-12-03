package com.bsu.garkavaya.repository;

import com.bsu.garkavaya.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PeopleRepository extends JpaRepository<Person, String> {
    @Query(value = "select p.passport_number from person p", nativeQuery = true)
    public List<String> findAllIds();
}
