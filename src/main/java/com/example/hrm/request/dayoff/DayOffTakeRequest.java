package com.example.hrm.request.dayoff;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

@Data
public class DayOffTakeRequest implements Serializable {

    @NotNull
    @Schema(example = "1")
    private Long employeeId;

    @NotNull
    private LocalDate dateFrom;

    @NotNull
    private LocalDate dateTo;
}
