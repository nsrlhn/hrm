package com.example.hrm.service.dayoff;

import com.example.hrm.model.dayoff.DayOff;
import com.example.hrm.model.dayoff.DayOffStatus;
import com.example.hrm.model.employee.Employee;
import com.example.hrm.repository.dayoff.DayOffRepository;
import com.example.hrm.request.dayoff.DayOffDecideRequest;
import com.example.hrm.request.dayoff.DayOffTakeRequest;
import com.example.hrm.service.employee.EmployeeReadService;
import com.example.hrm.util.dayoff.DayOffUtil;
import org.junit.jupiter.api.Test;
import org.mockito.AdditionalAnswers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
class DayOffServiceTest {

    private static final long ID = 1L;
    private static final Employee EMPLOYEE = new Employee();
    private static final LocalDate DATE_FROM = LocalDate.now();
    private static final LocalDate DATE_TO = LocalDate.now();

    @Autowired
    private DayOffService service;

    @MockBean
    private DayOffRepository repository;
    @MockBean
    private DayOffCheckService checkService;
    @MockBean
    private DayOffReadService readService;
    @MockBean
    private EmployeeReadService employeeService;

    @Test
    void givenParameters_WhenDecide_ShouldUpdate() {
        // Arrange
        Mockito.when(repository.save(any())).then(AdditionalAnswers.returnsFirstArg());
        Mockito.when(readService.getOrThrow(ID)).thenReturn(prepareDayOff());

        // Act
        DayOff dayOff = service.decide(ID, prepareDecideRequest());

        // Assert
        Mockito.verify(repository).save(any());
        assertThat(dayOff.getStatus()).isEqualTo(DayOffStatus.APPROVED);
    }

    @Test
    void givenParameters_WhenTake_ShouldSave() {
        // Arrange
        Mockito.when(employeeService.getOrThrow(ID)).thenReturn(EMPLOYEE);
        Mockito.when(repository.save(any())).then(AdditionalAnswers.returnsFirstArg());

        // Act
        DayOff dayOff = service.take(prepareTakeRequest());

        // Assert
        Mockito.verify(repository).save(any());
        assertThat(dayOff).hasNoNullFieldsOrPropertiesExcept("id").isEqualTo(prepareDayOff());
    }

    private DayOff prepareDayOff() {
        DayOff dayOff = new DayOff();
        dayOff.setEmployee(EMPLOYEE);
        dayOff.setDateFrom(DATE_FROM);
        dayOff.setDateTo(DATE_TO);
        dayOff.setAmount(DayOffUtil.calculateAmount(DATE_FROM, DATE_TO));
        dayOff.setStatus(DayOffStatus.WAITING);
        return dayOff;
    }

    private DayOffDecideRequest prepareDecideRequest() {
        DayOffDecideRequest request = new DayOffDecideRequest();
        request.setStatus(DayOffStatus.APPROVED);
        return request;
    }

    private DayOffTakeRequest prepareTakeRequest() {
        DayOffTakeRequest request = new DayOffTakeRequest();
        request.setEmployeeId(ID);
        request.setDateFrom(DATE_FROM);
        request.setDateTo(DATE_TO);
        return request;
    }

}