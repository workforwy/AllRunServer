package com.workforwy.allrun.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record CreateSportRequest(
        @NotBlank String sportType,
        @NotEmpty List<TracePoint> traces
) {
}
