package com.workforwy.allrun.repository;

import com.workforwy.allrun.entity.Topic;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    public void save(Topic topic) {
        jdbcTemplate.update(
                "INSERT INTO topic (username, content, imageUrl, address, latitude, longitude, createTime) "
                        + "VALUES (?, ?, ?, ?, ?, ?, ?)",
                topic.getUsername(),
                topic.getContent(),
                topic.getImageUrl(),
                topic.getAddress(),
                topic.getLatitude(),
                topic.getLongitude(),
                topic.getCreateTime()
        );
    }
}
