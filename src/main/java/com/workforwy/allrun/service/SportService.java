package com.workforwy.allrun.service;

import com.workforwy.allrun.entity.Sport;
import com.workforwy.allrun.entity.Trace;
import com.workforwy.allrun.repository.SportRepository;
import com.workforwy.allrun.repository.TraceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class SportService {

    private final SportRepository sportRepository;
    private final TraceRepository traceRepository;

    public SportService(SportRepository sportRepository, TraceRepository traceRepository) {
        this.sportRepository = sportRepository;
        this.traceRepository = traceRepository;
    }

    public List<Sport> findAllSports() {
        return sportRepository.findAllOrderByIdDesc();
    }

    public List<Trace> findTracesBySportId(int sportId) {
        return traceRepository.findBySportId(sportId);
    }

    @Transactional
    public void addSportData(String username, String sportType, String data) {
        int sportId = sportRepository.insert(username, sportType);
        if (sportId < 0 || data == null || data.isBlank()) {
            return;
        }

        String[] points = data.split("@");
        for (String point : points) {
            if (point.isBlank()) {
                continue;
            }
            String[] parts = point.split("\\|");
            if (parts.length < 3) {
                continue;
            }
            Trace trace = new Trace();
            trace.setSportTime(Double.parseDouble(parts[0]));
            trace.setLatitude(Double.parseDouble(parts[1]));
            trace.setLongitude(Double.parseDouble(parts[2]));
            traceRepository.save(sportId, trace);
        }
    }

    public List<SportWithTraces> findSportsWithTraces() {
        List<SportWithTraces> result = new ArrayList<>();
        for (Sport sport : sportRepository.findAllOrderByIdDesc()) {
            result.add(new SportWithTraces(sport, traceRepository.findBySportId(sport.getId())));
        }
        return result;
    }

    public record SportWithTraces(Sport sport, List<Trace> traces) {
    }
}
