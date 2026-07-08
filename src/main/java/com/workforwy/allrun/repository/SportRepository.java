package com.workforwy.allrun.repository;

import com.workforwy.allrun.entity.Sport;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class SportRepository {

    private static final RowMapper<Sport> ROW_MAPPER = (rs, rowNum) -> {
        Sport sport = new Sport();
        sport.setId(rs.getInt("id"));
        sport.setUsername(rs.getString("username"));
        sport.setSportType(rs.getString("sportType"));
        return sport;
    };

    private final JdbcTemplate jdbcTemplate;

    public SportRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Sport> findAllOrderByIdDesc() {
        return jdbcTemplate.query(
                "SELECT id, sportType, username FROM sport ORDER BY id DESC",
                ROW_MAPPER
        );
    }

    public int insert(String username, String sportType) {
        String uuid = UUID.randomUUID().toString();
        jdbcTemplate.update(
                "INSERT INTO sport (username, sportType, uuid) VALUES (?, ?, ?)",
                username,
                sportType,
                uuid
        );
        Integer id = jdbcTemplate.queryForObject(
                "SELECT id FROM sport WHERE username = ? AND uuid = ?",
                Integer.class,
                username,
                uuid
        );
        return id != null ? id : -1;
    }
}
