package com.example.hrm.model.employee;

import com.example.hrm.model.SimpleEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
public class Employee extends SimpleEntity {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private LocalDate recruitment;
}
