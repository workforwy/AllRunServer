package com.workforwy.allrun.repository;

import com.workforwy.allrun.entity.Topic;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Repository
public class TopicRepository {

    private static final RowMapper<Topic> ROW_MAPPER = (rs, rowNum) -> {
        Topic topic = new Topic();
        topic.setId(rs.getInt("id"));
        topic.setUsername(rs.getString("username"));
        topic.setContent(rs.getString("content"));
        topic.setImageUrl(rs.getString("imageUrl"));
        topic.setAddress(rs.getString("address"));
        topic.setLatitude(rs.getDouble("latitude"));
        topic.setLongitude(rs.getDouble("longitude"));
        topic.setCreateTime(rs.getLong("createTime"));
        return topic;
    };

    private final JdbcTemplate jdbcTemplate;

    public TopicRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Topic> findAll() {
        return jdbcTemplate.query(
                "SELECT id, username, content, imageUrl, address, latitude, longitude, createTime "
                        + "FROM topic ORDER BY id DESC",
                ROW_MAPPER
        );
    }

    public Optional<Topic> findById(int id) {
        List<Topic> topics = jdbcTemplate.query(
                "SELECT id, username, content, imageUrl, address, latitude, longitude, createTime "
                        + "FROM topic WHERE id = ?",
                ROW_MAPPER,
                id
        );
        return topics.stream().findFirst();
    }

    public int save(Topic topic) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO topic (username, content, imageUrl, address, latitude, longitude, createTime) "
                            + "VALUES (?, ?, ?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, topic.getUsername());
            ps.setString(2, topic.getContent());
            ps.setString(3, topic.getImageUrl());
            ps.setString(4, topic.getAddress());
            ps.setDouble(5, topic.getLatitude());
            ps.setDouble(6, topic.getLongitude());
            ps.setLong(7, topic.getCreateTime());
            return ps;
        }, keyHolder);
        Number key = keyHolder.getKey();
        return key != null ? key.intValue() : -1;
    }
}
