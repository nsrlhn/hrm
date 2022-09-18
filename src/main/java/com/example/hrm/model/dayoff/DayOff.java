package com.example.hrm.model.dayoff;

import com.example.hrm.model.SimpleEntity;
import com.example.hrm.model.employee.Employee;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Comment;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
public class DayOff extends SimpleEntity {

    @ManyToOne
    @JoinColumn(nullable = false)
    private Employee employee;

    @Column(nullable = false)
    private LocalDate dateFrom;

    @Column(nullable = false)
    private LocalDate dateTo;

    @Column(nullable = false)
    @Comment(DayOffStatus.DESCRIPTION)
    private DayOffStatus status;
}
