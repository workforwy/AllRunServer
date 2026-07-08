package com.workforwy.allrun.dto;

import com.workforwy.allrun.entity.Sport;
import com.workforwy.allrun.entity.Trace;

import java.util.List;

public record SportResponse(
        int id,
        String username,
        String sportType,
        List<TraceResponse> traces
) {

    public static SportResponse from(Sport sport, List<Trace> traces) {
        return new SportResponse(
                sport.getId(),
                sport.getUsername(),
                sport.getSportType(),
                traces.stream().map(TraceResponse::from).toList()
        );
    }
}
