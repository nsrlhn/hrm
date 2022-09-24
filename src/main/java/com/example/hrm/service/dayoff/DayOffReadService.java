package com.example.hrm.service.dayoff;

import com.example.hrm.exception.BadRequestException;
import com.example.hrm.exception.dayoff.DayOffExceptionCode;
import com.example.hrm.model.dayoff.DayOff;
import com.example.hrm.model.dayoff.DayOffStatus;
import com.example.hrm.model.employee.Employee;
import com.example.hrm.repository.dayoff.DayOffRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DayOffReadService {

    private final DayOffRepository repository;

    /**
     * Calculation made by assuming day-off is taken including both dateFrom and dateTo
     */
    public int getDayOffUsed(Employee employee) {
        // TODO : improve code with Stream expression
        // TODO : improve code performance with better query

        List<DayOff> approved = repository.findAllByEmployeeAndStatus(employee, DayOffStatus.APPROVED);
        int dayOffUsed = 0;
        for (DayOff dayOff : approved) {
            dayOffUsed += dayOff.getAmount();
        }
        return dayOffUsed;
    }

    public DayOff getOrThrow(Long id) {
        return repository.findById(id).orElseThrow(() -> new BadRequestException(DayOffExceptionCode.NOT_FOUND));
    }
}
