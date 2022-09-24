package com.example.hrm.request.dayoff;

import com.example.hrm.model.dayoff.DayOffStatus;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class DayOffDecideRequest implements Serializable {

    @NotNull
    private DayOffStatus status;
}
