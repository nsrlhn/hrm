package com.example.hrm.service.dayoff;

import com.example.hrm.exception.BadRequestException;
import com.example.hrm.exception.dayoff.DayOffExceptionCode;
import com.example.hrm.model.dayoff.DayOff;
import com.example.hrm.model.employee.Employee;
import com.example.hrm.repository.dayoff.DayOffRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DayOffReadService {

    private final DayOffRepository repository;

    public int getDayOffUsed(Employee employee) {
        return Optional.ofNullable(repository.totalAmountOfDayOffApproved(employee.getId())).orElse(0);
    }

    public DayOff getOrThrow(Long id) {
        return repository.findById(id).orElseThrow(() -> new BadRequestException(DayOffExceptionCode.NOT_FOUND));
    }

    /**
     * @return true if there is a waiting or approved day-off between the dates
     */
    public boolean isOverlap(Long employeeId, LocalDate start, LocalDate end) {
        return repository.numberOfOverlap(employeeId, start, end);
    }
}
