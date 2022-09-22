package com.example.hrm.service.employee;

import com.example.hrm.exception.BadRequestException;
import com.example.hrm.exception.employee.EmployeeExceptionCode;
import com.example.hrm.model.employee.Employee;
import com.example.hrm.repository.employee.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeeReadService {

    private final EmployeeRepository repository;

    public Employee getOrThrow(Long id) {
        return repository.findById(id).orElseThrow(() -> new BadRequestException(EmployeeExceptionCode.NOT_FOUND));
    }
}
