package com.example.hrm.service.dayoff;

import com.example.hrm.model.dayoff.DayOff;
import com.example.hrm.model.dayoff.DayOffStatus;
import com.example.hrm.model.employee.Employee;
import com.example.hrm.repository.dayoff.DayOffRepository;
import com.example.hrm.request.dayoff.DayOffDecideRequest;
import com.example.hrm.request.dayoff.DayOffTakeRequest;
import com.example.hrm.service.employee.EmployeeReadService;
import com.example.hrm.util.dayoff.DayOffUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DayOffService {

    private final DayOffRepository repository;
    private final DayOffCheckService checkService;
    private final DayOffReadService readService;
    private final EmployeeReadService employeeService;

    public DayOff decide(Long id, DayOffDecideRequest request) {
        // Get
        DayOff dayOff = readService.getOrThrow(id);

        // Check
        checkService.checkProcessStep(dayOff.getStatus());
        checkService.checkDates(dayOff.getDateFrom(), dayOff.getDateTo());
        checkService.checkPermission(dayOff.getEmployee(), dayOff.getAmount());

        // Prepare
        dayOff.setStatus(request.getStatus());

        // Save
        return repository.save(dayOff);
    }

    public DayOff take(DayOffTakeRequest request) {
        // Calculate
        int amount = DayOffUtil.calculateAmount(request.getDateFrom(), request.getDateTo());

        // Get
        Employee employee = employeeService.getOrThrow(request.getEmployeeId());

        // Check
        checkService.checkDates(request.getDateFrom(), request.getDateTo());
        checkService.checkAmount(amount);
        checkService.checkExistence(employee.getId(), request.getDateFrom(), request.getDateTo());
        checkService.checkPermission(employee, amount);

        // Prepare
        DayOff dayOff = new DayOff();
        dayOff.setEmployee(employee);
        dayOff.setDateFrom(request.getDateFrom());
        dayOff.setDateTo(request.getDateTo());
        dayOff.setAmount(amount);
        dayOff.setStatus(DayOffStatus.WAITING);

        // Save
        return repository.save(dayOff);
    }

}
