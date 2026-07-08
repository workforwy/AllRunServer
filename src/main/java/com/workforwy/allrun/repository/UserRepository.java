package com.workforwy.allrun.repository;

import com.workforwy.allrun.entity.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository {

    private static final RowMapper<User> ROW_MAPPER = (rs, rowNum) -> {
        User user = new User();
        user.setId(rs.getInt("id"));
        user.setUsername(rs.getString("username"));
        user.setPasswordHash(rs.getString("password_hash"));
        user.setNickname(rs.getString("nickname"));
        user.setGender(rs.getString("gender"));
        user.setIconUrl(rs.getString("iconUrl"));
        user.setLatitude(rs.getDouble("latitude"));
        user.setLongitude(rs.getDouble("longitude"));
        user.setIntro(rs.getString("intro"));
        user.setRegTime(rs.getLong("regTime"));
        return user;
    };

    private final JdbcTemplate jdbcTemplate;

    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<User> findByUsername(String username) {
        List<User> users = jdbcTemplate.query(
                "SELECT * FROM `user` WHERE username = ?",
                ROW_MAPPER,
                username
        );
        return users.stream().findFirst();
    }

    public boolean existsByUsername(String username) {
        Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM `user` WHERE username = ?",
                Integer.class,
                username
        );
        return count != null && count > 0;
    }

    public void save(User user) {
        jdbcTemplate.update(
                "INSERT INTO `user` (username, password_hash, nickname, gender, iconUrl, latitude, longitude, intro, regTime) "
                        + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)",
                user.getUsername(),
                user.getPasswordHash(),
                user.getNickname(),
                user.getGender(),
                user.getIconUrl(),
                user.getLatitude(),
                user.getLongitude(),
                user.getIntro(),
                user.getRegTime()
        );
    }

    public List<User> findPage(int page, int size) {
        int offset = (page - 1) * size;
        return jdbcTemplate.query(
                "SELECT id, username, password_hash, nickname, gender, iconUrl, latitude, longitude, intro, regTime "
                        + "FROM `user` ORDER BY id DESC LIMIT ? OFFSET ?",
                ROW_MAPPER,
                size,
                offset
        );
    }
}
