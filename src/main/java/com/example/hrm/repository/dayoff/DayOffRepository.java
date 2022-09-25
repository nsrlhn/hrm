package com.example.hrm.repository.dayoff;

import com.example.hrm.model.dayoff.DayOff;
import com.example.hrm.repository.BaseRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;

public interface DayOffRepository extends BaseRepository<DayOff> {

    @Query(value = "SELECT EXISTS(SELECT * FROM day_off AS d " +
            "WHERE d.employee_id = ?1 " +
            "AND d.status != 2 " +
            "AND ((d.date_from <= ?2 AND ?2 <= d.date_to) OR (d.date_from <= ?3 AND ?3 <= d.date_to)))",
            nativeQuery = true)
    boolean numberOfOverlap(Long employeeId, LocalDate start, LocalDate end);

    @Query(value = "SELECT SUM(d.amount) FROM DAY_OFF as d " +
            "WHERE d.employee_id = ?1 " +
            "AND d.status = 1",
            nativeQuery = true)
    Integer totalAmountOfDayOffApproved(Long employeeId);
}
