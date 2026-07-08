package com.workforwy.allrun.repository;

import com.workforwy.allrun.entity.Trace;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TraceRepository {

    private static final RowMapper<Trace> ROW_MAPPER = (rs, rowNum) -> {
        Trace trace = new Trace();
        trace.setId(rs.getInt("id"));
        trace.setSportId(rs.getInt("sportId"));
        trace.setSportTime(rs.getDouble("sportTime"));
        trace.setLatitude(rs.getDouble("latitude"));
        trace.setLongitude(rs.getDouble("longitude"));
        return trace;
    };

    private final JdbcTemplate jdbcTemplate;

    public TraceRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Trace> findBySportId(int sportId) {
        return jdbcTemplate.query(
                "SELECT id, sportId, sportTime, latitude, longitude FROM trace WHERE sportId = ? ORDER BY id ASC",
                ROW_MAPPER,
                sportId
        );
    }

    public void save(int sportId, Trace trace) {
        jdbcTemplate.update(
                "INSERT INTO trace (sportId, sportTime, latitude, longitude) VALUES (?, ?, ?, ?)",
                sportId,
                trace.getSportTime(),
                trace.getLatitude(),
                trace.getLongitude()
        );
    }
}
