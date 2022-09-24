package com.example.hrm.service.dayoff;

import com.example.hrm.exception.BadRequestException;
import com.example.hrm.exception.dayoff.DayOffExceptionCode;
import com.example.hrm.model.dayoff.DayOff;
import com.example.hrm.model.dayoff.DayOffStatus;
import com.example.hrm.model.employee.Employee;
import com.example.hrm.repository.dayoff.DayOffRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.time.Period;
import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class DayOffReadServiceTest {

    private static final long ID = 1L;
    private static final Employee EMPLOYEE = new Employee();
    private static final Period PERIOD1 = Period.ofDays(2);
    private static final Period PERIOD2 = Period.ofDays(3);

    @Autowired
    private DayOffReadService service;

    @MockBean
    private DayOffRepository repository;

    @Test
    void givenAvailableDayOffId_WhenGetOrThrow_ShouldReturn() {
        // Arrange
        DayOff dayOff = new DayOff();
        Mockito.when(repository.findById(ID)).thenReturn(Optional.of(dayOff));

        // Act
        DayOff result = service.getOrThrow(ID);

        // Assert
        Mockito.verify(repository).findById(ID);
        assertThat(result).isSameAs(dayOff);
    }

    @Test
    void givenEmployee_WhenGetDayOffUsed_ShouldCalculateDayOffUsed() {
        // Arrange
        Mockito.when(repository.findAllByEmployeeAndStatus(EMPLOYEE, DayOffStatus.APPROVED))
                .thenReturn(Arrays.asList(prepareDayOff(PERIOD1), prepareDayOff(PERIOD2)));

        // Act
        long result = service.getDayOffUsed(EMPLOYEE);

        // Assert
        assertThat(result).isEqualTo(PERIOD1.getDays() + PERIOD2.getDays());
    }

    @Test
    void givenUnavailableDayOffId_WhenGetOrThrow_ShouldThrowException() {
        // Arrange
        Mockito.when(repository.findById(ID)).thenReturn(Optional.empty());

        // Act
        Exception exception = assertThrows(BadRequestException.class, () -> service.getOrThrow(ID));

        // Assert
        assertThat(exception.getMessage()).contains(DayOffExceptionCode.NOT_FOUND.toString());
    }

    private DayOff prepareDayOff(Period period) {
        DayOff dayOff = new DayOff();
        dayOff.setEmployee(EMPLOYEE);
        dayOff.setDateFrom(LocalDate.now());
        dayOff.setDateTo(LocalDate.now().plus(period));
        dayOff.setStatus(DayOffStatus.APPROVED);
        return dayOff;
    }

}