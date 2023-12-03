package com.bsu.garkavaya.repository;

import com.bsu.garkavaya.model.Visa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VisasRepository extends JpaRepository<Visa, Long> {
}
