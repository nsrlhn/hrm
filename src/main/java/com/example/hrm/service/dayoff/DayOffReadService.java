package com.example.hrm.service.dayoff;

import com.example.hrm.exception.BadRequestException;
import com.example.hrm.exception.dayoff.DayOffExceptionCode;
import com.example.hrm.model.dayoff.DayOff;
import com.example.hrm.model.dayoff.DayOffStatus;
import com.example.hrm.model.employee.Employee;
import com.example.hrm.repository.dayoff.DayOffRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DayOffReadService {

    private final DayOffRepository repository;

    /**
     * Calculation made by assuming day-off is taken including both dateFrom and dateTo
     */
    public long getDayOffUsed(Employee employee) {
        // TODO : improve code with Stream expression

        List<DayOff> approved = repository.findAllByEmployeeAndStatus(employee, DayOffStatus.APPROVED);
        long dayOffUsed = 0;
        for (DayOff dayOff : approved) {
            dayOffUsed += ChronoUnit.DAYS.between(dayOff.getDateFrom(), dayOff.getDateTo()) + 1;
        }
        return dayOffUsed;
    }

    public DayOff getOrThrow(Long id) {
        return repository.findById(id).orElseThrow(() -> new BadRequestException(DayOffExceptionCode.NOT_FOUND));
    }
}
