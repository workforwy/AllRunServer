package com.workforwy.allrun.dto;

import com.workforwy.allrun.entity.Trace;

public record TraceResponse(
        int id,
        int sportId,
        double sportTime,
        double latitude,
        double longitude
) {

    public static TraceResponse from(Trace trace) {
        return new TraceResponse(
                trace.getId(),
                trace.getSportId(),
                trace.getSportTime(),
                trace.getLatitude(),
                trace.getLongitude()
        );
    }
}
