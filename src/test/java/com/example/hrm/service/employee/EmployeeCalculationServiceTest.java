package com.example.hrm.service.employee;

import com.example.hrm.model.employee.Employee;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.Period;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class EmployeeCalculationServiceTest {

    // Permissions
    private static final int P1 = 5;
    private static final int P2 = 15;
    private static final int P3 = 18;
    private static final int P4 = 24;

    // Years
    private static final int Y2 = 5;
    private static final int Y3 = 10;

    // Periods
    private static final Period LONG_PERIOD = Period.ofYears(50);

    @Autowired
    private EmployeeCalculationService service;

    private Employee prepareEmployee(Period workingPeriod) {
        Employee employee = new Employee();
        employee.setRecruitment(LocalDate.now().minus(workingPeriod));
        return employee;
    }

    /**
     * Working Period: -1 year
     */
    @Test
    void test1() {
        // Arrange
        Employee employee = prepareEmployee(Period.ofYears(-1));

        // Act
        int dayOffEarned = service.getDayOffEarned(employee);

        // Assert
        assertThat(dayOffEarned).isZero();
    }

    /**
     * Working Period: 0
     */
    @Test
    void test2() {
        // Arrange
        Employee employee = prepareEmployee(Period.ZERO);

        // Act
        int dayOffEarned = service.getDayOffEarned(employee);

        // Assert
        assertThat(dayOffEarned).isEqualTo(P1);
    }

    /**
     * Working Period: 355 days
     */
    @Test
    void test3() {
        // Arrange
        Employee employee = prepareEmployee(Period.ofYears(1).minusDays(1));

        // Act
        int dayOffEarned = service.getDayOffEarned(employee);

        // Assert
        assertThat(dayOffEarned).isEqualTo(P1);
    }

    /**
     * Working Period: 1 year
     */
    @Test
    void test4() {
        // Arrange
        Employee employee = prepareEmployee(Period.ofYears(1));

        // Act
        int dayOffEarned = service.getDayOffEarned(employee);

        // Assert
        assertThat(dayOffEarned).isEqualTo(P2);
    }

    /**
     * Working Period: {@value Y2} years & 355 days
     */
    @Test
    void test5() {
        // Arrange
        Employee employee = prepareEmployee(Period.ofYears(Y2 + 1).minusDays(1));

        // Act
        int dayOffEarned = service.getDayOffEarned(employee);

        // Assert
        assertThat(dayOffEarned).isEqualTo(Y2 * P2);
    }

    /**
     * Working Period: {@value Y2} years + 1 year
     */
    @Test
    void test6() {
        // Arrange
        Employee employee = prepareEmployee(Period.ofYears(Y2 + 1));

        // Act
        int dayOffEarned = service.getDayOffEarned(employee);

        // Assert
        assertThat(dayOffEarned).isEqualTo(Y2 * P2 + P3);
    }

    /**
     * Working Period: {@value Y3} years & 355 days
     */
    @Test
    void test7() {
        // Arrange
        Employee employee = prepareEmployee(Period.ofYears(Y3 + 1).minusDays(1));

        // Act
        int dayOffEarned = service.getDayOffEarned(employee);

        // Assert
        assertThat(dayOffEarned).isEqualTo(Y2 * P2 + (Y3 - Y2) * P3);
    }

    /**
     * Working Period: {@value Y3} years + 1 year
     */
    @Test
    void test8() {
        // Arrange
        Employee employee = prepareEmployee(Period.ofYears(Y3 + 1));

        // Act
        int dayOffEarned = service.getDayOffEarned(employee);

        // Assert
        assertThat(dayOffEarned).isEqualTo(Y2 * P2 + (Y3 - Y2) * P3 + P4);
    }

    /**
     * Working Period: Long Period
     */
    @Test
    void test9() {
        // Arrange
        Employee employee = prepareEmployee(LONG_PERIOD);

        // Act
        int dayOffEarned = service.getDayOffEarned(employee);

        // Assert
        assertThat(dayOffEarned).isEqualTo(Y2 * P2 + (Y3 - Y2) * P3 + (50 - Y3) * P4);
    }
}