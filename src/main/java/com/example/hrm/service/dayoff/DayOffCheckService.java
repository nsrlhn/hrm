package com.example.hrm.service.dayoff;

import com.example.hrm.exception.BadRequestException;
import com.example.hrm.exception.dayoff.DayOffExceptionCode;
import com.example.hrm.model.dayoff.DayOffStatus;
import com.example.hrm.model.employee.Employee;
import com.example.hrm.service.employee.EmployeeCalculationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class DayOffCheckService {

    private final DayOffReadService readService;
    private final EmployeeCalculationService calculationService;

    public void checkAmount(int dayOffAmount) {
        if (dayOffAmount <= 0) {
            throw new BadRequestException(DayOffExceptionCode.INVALID_AMOUNT);
        }
    }

    public void checkDates(LocalDate dateFrom, LocalDate dateTo) {
        if (dateFrom.isBefore(LocalDate.now())) {
            throw new BadRequestException(DayOffExceptionCode.DATE_IS_PAST);
        }
        if (dateFrom.isAfter(dateTo)) {
            throw new BadRequestException(DayOffExceptionCode.DATE_FROM_IS_AFTER_DATE_TO);
        }
    }

    public void checkExistence(Long employeeId, LocalDate dateFrom, LocalDate dateTo) {
        if (readService.isOverlap(employeeId, dateFrom, dateTo)) {
            throw new BadRequestException(DayOffExceptionCode.ALREADY_REQUESTED);
        }
    }

    public void checkPermission(Employee employee, int requestedAmount) {
        int dayOffUsed = readService.getDayOffUsed(employee);
        int dayOffEarned = calculationService.getDayOffEarned(employee);
        int dayOffUsable = dayOffEarned - dayOffUsed;

        if (dayOffUsable <= 0) {
            throw new BadRequestException(DayOffExceptionCode.ALREADY_TAKEN);
        }

        if (requestedAmount > dayOffUsable) {
            throw new BadRequestException(DayOffExceptionCode.EXCESSIVE_DURATION);
        }
    }

    public void checkProcessStep(DayOffStatus status) {
        if (status != DayOffStatus.WAITING) {
            throw new BadRequestException(DayOffExceptionCode.ALREADY_DECIDED);
        }
    }
}
