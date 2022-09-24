package com.example.hrm.controller.dayoff;

import com.example.hrm.model.dayoff.DayOff;
import com.example.hrm.request.dayoff.DayOffDecideRequest;
import com.example.hrm.request.dayoff.DayOffTakeRequest;
import com.example.hrm.service.dayoff.DayOffService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = {"day-off"})
@Tag(name = "Day Off")
public class DayOffController {

    private final DayOffService service;

    @PutMapping("decide/{id}")
    @Operation(description = "Approve or Decline a Day Off Request")
    public DayOff decide(@PathVariable Long id, @Valid @RequestBody DayOffDecideRequest request) {
        return service.decide(id, request);
    }

    @PostMapping("take")
    @Operation(description = "Create Day Off Request")
    public DayOff take(@Valid @RequestBody DayOffTakeRequest request) {
        return service.take(request);
    }
}
