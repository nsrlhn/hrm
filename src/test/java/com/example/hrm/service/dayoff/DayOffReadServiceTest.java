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
import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class DayOffReadServiceTest {

    private static final long ID = 1L;
    private static final Employee EMPLOYEE = new Employee();

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
        int days1 = 2;
        int days2 = 3;

        Mockito.when(repository.findAllByEmployeeAndStatus(EMPLOYEE, DayOffStatus.APPROVED))
                .thenReturn(Arrays.asList(
                        prepareDayOffByDays(days1),
                        prepareDayOffByDays(days2)
                ));

        // Act
        long result = service.getDayOffUsed(EMPLOYEE);

        // Assert
        assertThat(result).isEqualTo(days1 + days2);
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

    private DayOff prepareDayOffByDays(int days) {
        DayOff dayOff = new DayOff();
        dayOff.setEmployee(EMPLOYEE);
        dayOff.setDateFrom(LocalDate.now());
        dayOff.setDateTo(LocalDate.now().plusDays(days - 1));
        dayOff.setStatus(DayOffStatus.APPROVED);
        return dayOff;
    }

}