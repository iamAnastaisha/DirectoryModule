package com.bsu.garkavaya.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "Visa")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Visa implements Serializable {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "issue_date")
    private Date issueDate;

    @Column(name = "validity_period")
    private Double validityPeriod;

    @Column(name = "type")
    private Character type;

    @Column(name = "countries")
    private String countries;

    @ManyToOne
    @JoinColumn(name = "person_passport_number", referencedColumnName = "passport_number")
    private Person person;


}
