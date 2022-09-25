package com.example.hrm.service.dayoff;

import com.example.hrm.exception.BadRequestException;
import com.example.hrm.exception.dayoff.DayOffExceptionCode;
import com.example.hrm.model.dayoff.DayOffStatus;
import com.example.hrm.model.employee.Employee;
import com.example.hrm.service.employee.EmployeeCalculationService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.concurrent.ThreadLocalRandom;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class DayOffCheckServiceTest {

    private static final LocalDate DATE_FROM = LocalDate.now();
    private static final LocalDate DATE_TO = DATE_FROM.plusDays(1);
    private static final Employee EMPLOYEE = new Employee();
    private static final int RANDOM_NUMBER = ThreadLocalRandom.current().nextInt(0, Integer.MAX_VALUE / 2);
    private static final long ID = 1L;

    @Autowired
    private DayOffCheckService service;

    @MockBean
    private DayOffReadService readService;
    @MockBean
    private EmployeeCalculationService calculationService;

    @Test
    void givenAlreadyExistRequest_WhenCheckExistence_ShouldThrow() {
        // Assert
        Mockito.when(readService.isOverlap(ID, DATE_FROM, DATE_TO)).thenReturn(true);

        // Act
        Exception exception = assertThrows(BadRequestException.class, () -> service.checkExistence(ID, DATE_FROM, DATE_TO));

        // Assert
        assertThat(exception.getMessage()).contains(DayOffExceptionCode.ALREADY_REQUESTED.toString());
    }

    @Test
    void givenAmountIsZero_WhenCheckAmount_ShouldThrow() {
        // Act
        Exception exception = assertThrows(BadRequestException.class, () -> service.checkAmount(0));

        // Assert
        assertThat(exception.getMessage()).contains(DayOffExceptionCode.INVALID_AMOUNT.toString());
    }

    @Test
    void givenDateFromIsAfterDateTo_WhenCheckDates_ShouldThrow() {
        // Act
        Exception exception = assertThrows(BadRequestException.class, () -> service.checkDates(DATE_TO, DATE_FROM));

        // Assert
        assertThat(exception.getMessage()).contains(DayOffExceptionCode.DATE_FROM_IS_AFTER_DATE_TO.toString());
    }

    @Test
    void givenDateFromIsPast_WhenCheckDates_ShouldThrow() {
        // Arrange
        LocalDate dateFrom = LocalDate.now().minusDays(1);

        // Act
        Exception exception = assertThrows(BadRequestException.class, () -> service.checkDates(dateFrom, DATE_TO));

        // Assert
        assertThat(exception.getMessage()).contains(DayOffExceptionCode.DATE_IS_PAST.toString());
    }

    @Test
    void givenEmployeeWithHavingZeroPermission_WhenCheckPermission_ShouldThrow() {
        // Arrange
        Mockito.when(readService.getDayOffUsed(EMPLOYEE)).thenReturn(RANDOM_NUMBER);
        Mockito.when(calculationService.getDayOffEarned(EMPLOYEE)).thenReturn(RANDOM_NUMBER);

        // Act
        Exception exception = assertThrows(BadRequestException.class, () -> service.checkPermission(EMPLOYEE, 0));

        // Assert
        assertThat(exception.getMessage()).contains(DayOffExceptionCode.ALREADY_TAKEN.toString());
    }

    @Test
    void givenRequestWithExcessiveAmount_WhenCheckPermission_ShouldThrow() {
        // Arrange
        Mockito.when(readService.getDayOffUsed(EMPLOYEE)).thenReturn(0);
        Mockito.when(calculationService.getDayOffEarned(EMPLOYEE)).thenReturn(RANDOM_NUMBER);

        // Act
        Exception exception = assertThrows(BadRequestException.class, () -> service.checkPermission(EMPLOYEE, RANDOM_NUMBER + 1));

        // Assert
        assertThat(exception.getMessage()).contains(DayOffExceptionCode.EXCESSIVE_DURATION.toString());
    }

    @Test
    void givenStatusApproved_WhenCheckProcessStep_ShouldThrow() {
        // Act
        Exception exception = assertThrows(BadRequestException.class, () -> service.checkProcessStep(DayOffStatus.APPROVED));

        // Assert
        assertThat(exception.getMessage()).contains(DayOffExceptionCode.ALREADY_DECIDED.toString());
    }

    @Test
    void givenStatusRejected_WhenCheckProcessStep_ShouldThrow() {
        // Act
        Exception exception = assertThrows(BadRequestException.class, () -> service.checkProcessStep(DayOffStatus.REJECTED));

        // Assert
        assertThat(exception.getMessage()).contains(DayOffExceptionCode.ALREADY_DECIDED.toString());
    }
}