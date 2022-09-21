package com.example.hrm.service.employee;

import com.example.hrm.model.employee.Employee;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
public class EmployeeCalculationService {

    public long getDayOffEarned(Employee employee) {
        long years = this.getYearsOfEmployment(employee);

        // Permissions
        final int P1 = 5;
        final int P2 = 15;
        final int P3 = 18;
        final int P4 = 24;

        // Years
        final int Y1 = 0;
        final int Y2 = 5;
        final int Y3 = 10;

        if (years < 0) {
            return 0;
        }
        if (years == Y1) {
            return P1;
        }
        if (years <= Y2) {
            return P1 + years * P2;
        }
        if (years <= Y3) {
            return P1 + (Y2 - Y1) * P2 + (years - Y2) * P3;
        }
        return P1 + (Y2 - Y1) * P2 + (Y3 - Y2) * P3 + (years - Y3) * P4;
    }

    public long getYearsOfEmployment(Employee employee) {
        return ChronoUnit.YEARS.between(employee.getRecruitment(), LocalDate.now());
    }
}
