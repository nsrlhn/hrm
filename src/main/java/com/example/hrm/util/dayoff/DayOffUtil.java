package com.example.hrm.util.dayoff;

import lombok.experimental.UtilityClass;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@UtilityClass
public class DayOffUtil {

    private static final Set<DayOfWeek> HOLIDAYS = Stream.of(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY).collect(Collectors.toCollection(HashSet::new));

    /**
     * Calculation made by including both start and end
     */
    public static int calculateAmount(LocalDate start, LocalDate end) {
        // TODO : Include holidays other from weekend
        int amount = 0;
        for (LocalDate date = start; !date.isAfter(end); date = date.plusDays(1)) {
            if (!HOLIDAYS.contains(date.getDayOfWeek())) {
                amount++;
            }
        }
        return amount;
    }
}
