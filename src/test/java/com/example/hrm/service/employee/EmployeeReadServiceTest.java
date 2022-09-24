package com.example.hrm.service.employee;

import com.example.hrm.exception.BadRequestException;
import com.example.hrm.exception.employee.EmployeeExceptionCode;
import com.example.hrm.model.employee.Employee;
import com.example.hrm.repository.employee.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class EmployeeReadServiceTest {

    private static final long ID = 1L;

    @Autowired
    private EmployeeReadService service;

    @MockBean
    private EmployeeRepository repository;

    @Test
    void givenAvailableEmployeeId_WhenGetOrThrow_ShouldReturn() {
        // Arrange
        Employee employee = new Employee();
        Mockito.when(repository.findById(ID)).thenReturn(Optional.of(employee));

        // Act
        Employee result = service.getOrThrow(ID);

        // Assert
        Mockito.verify(repository).findById(ID);
        assertThat(result).isSameAs(employee);
    }

    @Test
    void givenUnavailableEmployeeId_WhenGetOrThrow_ShouldThrowException() {
        // Arrange
        Mockito.when(repository.findById(ID)).thenReturn(Optional.empty());

        // Act
        Exception exception = assertThrows(BadRequestException.class, () -> service.getOrThrow(ID));

        // Assert
        assertThat(exception.getMessage()).contains(EmployeeExceptionCode.NOT_FOUND.toString());
    }
}