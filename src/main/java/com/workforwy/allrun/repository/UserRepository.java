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
        user.setMd5password(rs.getString("md5password"));
        user.setNickname(rs.getString("nickname"));
        user.setGender(rs.getString("gender"));
        user.setIconUrl(rs.getString("iconUrl"));
        user.setLatitude(rs.getDouble("latitude"));
        user.setLongitude(rs.getDouble("longitude"));
        user.setIntro(rs.getString("intro"));
        user.setRegTime(rs.getDouble("regTime"));
        return user;
    };

    private final JdbcTemplate jdbcTemplate;

    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public boolean exists(String username, String md5password) {
        Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM `user` WHERE username = ? AND md5password = ?",
                Integer.class,
                username,
                md5password
        );
        return count != null && count > 0;
    }

    public Optional<User> findByCredentials(String username, String md5password) {
        List<User> users = jdbcTemplate.query(
                "SELECT * FROM `user` WHERE username = ? AND md5password = ?",
                ROW_MAPPER,
                username,
                md5password
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
                "INSERT INTO `user` (username, md5password, nickname, gender, iconUrl, latitude, longitude, intro, regTime) "
                        + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)",
                user.getUsername(),
                user.getMd5password(),
                user.getNickname(),
                user.getGender(),
                user.getIconUrl(),
                user.getLatitude(),
                user.getLongitude(),
                user.getIntro(),
                user.getRegTime()
        );
    }

    public List<User> findPage(int pageIndex, int rowNum) {
        int offset = (pageIndex - 1) * rowNum;
        return jdbcTemplate.query(
                "SELECT id, username, md5password, nickname, gender, iconUrl, latitude, longitude, intro, regTime "
                        + "FROM `user` ORDER BY id DESC LIMIT ? OFFSET ?",
                ROW_MAPPER,
                rowNum,
                offset
        );
    }
}
