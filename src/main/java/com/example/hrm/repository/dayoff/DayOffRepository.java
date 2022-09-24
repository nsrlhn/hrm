package com.example.hrm.repository.dayoff;

import com.example.hrm.model.dayoff.DayOff;
import com.example.hrm.model.dayoff.DayOffStatus;
import com.example.hrm.model.employee.Employee;
import com.example.hrm.repository.BaseRepository;

import java.util.List;

public interface DayOffRepository extends BaseRepository<DayOff> {

    List<DayOff> findAllByEmployeeAndStatus(Employee employee, DayOffStatus status);
}
