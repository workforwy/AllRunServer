package com.workforwy.allrun.dto;

import jakarta.validation.constraints.NotNull;

public record TracePoint(
        @NotNull Double sportTime,
        @NotNull Double latitude,
        @NotNull Double longitude
) {
}
