package com.example.hrm.service.dayoff;

import com.example.hrm.model.dayoff.DayOff;
import com.example.hrm.model.dayoff.DayOffStatus;
import com.example.hrm.model.employee.Employee;
import com.example.hrm.repository.dayoff.DayOffRepository;
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
    void givenParameters_WhenTake_ShouldSave() {
        // Arrange
        Mockito.when(employeeService.getOrThrow(ID)).thenReturn(EMPLOYEE);
        Mockito.when(repository.save(any())).then(AdditionalAnswers.returnsFirstArg());

        // Act
        DayOff dayOff = service.take(prepareTakeRequest());

        // Assert
        Mockito.verify(repository).save(any());
        assertThat(dayOff).hasNoNullFieldsOrPropertiesExcept("id");
        assertThat(dayOff.getEmployee()).isSameAs(EMPLOYEE);
        assertThat(dayOff.getDateFrom()).isSameAs(DATE_FROM);
        assertThat(dayOff.getDateTo()).isSameAs(DATE_TO);
        assertThat(dayOff.getAmount()).isSameAs(DayOffUtil.calculateAmount(DATE_FROM, DATE_TO));
        assertThat(dayOff.getStatus()).isSameAs(DayOffStatus.WAITING);
    }

    private DayOffTakeRequest prepareTakeRequest() {
        DayOffTakeRequest request = new DayOffTakeRequest();
        request.setEmployeeId(ID);
        request.setDateFrom(DATE_FROM);
        request.setDateTo(DATE_TO);
        return request;
    }

}