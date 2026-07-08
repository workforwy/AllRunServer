package com.workforwy.allrun.service;

import com.workforwy.allrun.dto.CreateSportRequest;
import com.workforwy.allrun.dto.SportResponse;
import com.workforwy.allrun.dto.TracePoint;
import com.workforwy.allrun.entity.Sport;
import com.workforwy.allrun.entity.Trace;
import com.workforwy.allrun.repository.SportRepository;
import com.workforwy.allrun.repository.TraceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SportService {

    private final SportRepository sportRepository;
    private final TraceRepository traceRepository;

    public SportService(SportRepository sportRepository, TraceRepository traceRepository) {
        this.sportRepository = sportRepository;
        this.traceRepository = traceRepository;
    }

    @Transactional
    public SportResponse create(String username, CreateSportRequest request) {
        int sportId = sportRepository.insert(username, request.sportType());
        for (TracePoint point : request.traces()) {
            Trace trace = new Trace();
            trace.setSportTime(point.sportTime());
            trace.setLatitude(point.latitude());
            trace.setLongitude(point.longitude());
            traceRepository.save(sportId, trace);
        }

        Sport sport = new Sport();
        sport.setId(sportId);
        sport.setUsername(username);
        sport.setSportType(request.sportType());
        List<Trace> traces = traceRepository.findBySportId(sportId);
        return SportResponse.from(sport, traces);
    }

    public List<SportResponse> findAllWithTraces() {
        return sportRepository.findAllOrderByIdDesc().stream()
                .map(sport -> SportResponse.from(sport, traceRepository.findBySportId(sport.getId())))
                .toList();
    }
}
