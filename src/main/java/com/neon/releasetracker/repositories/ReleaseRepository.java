package com.neon.releasetracker.repositories;

import com.neon.releasetracker.exceptions.CustomException;
import com.neon.releasetracker.models.Release;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
@Slf4j
public class ReleaseRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    public Release newRelease(Release release) {

        String query = "INSERT INTO `releases` (`name`,`description`,`status`,`releaseDate`," +
                "`createdAt`,`lastUpdatedAt`) VALUES ( ?, ?, ?, ?, ?, ?);";

        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();

            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, release.getName());
                ps.setString(2, release.getDescription());
                ps.setString(3, release.getStatus());
                ps.setDate(4, release.getReleaseDate());
                ps.setTimestamp(5, release.getCreatedAt());
                ps.setTimestamp(6, release.getLastUpdatedAt());
                return ps;
            }, keyHolder);

            log.info("" + keyHolder.getKeys().get("GENERATED_KEY"));

            release.setId(keyHolder.getKey().intValue());

            return release;

        } catch (DataAccessException e) {
            log.error(e.getMessage());
            return null;
        }
    }

    public List<Release> getAllReleases() throws CustomException {
        try {
            String query = "SELECT * FROM `releases`;";

            return jdbcTemplate.query(query, (rs, rowNum) -> {
                Release release = new Release();
                release.setId(rs.getInt("id"));
                release.setName(rs.getString("name"));
                release.setDescription(rs.getString("description"));
                release.setCreatedAt(rs.getTimestamp("createdAt"));
                release.setReleaseDate(rs.getDate("releaseDate"));
                release.setStatus(rs.getString("status"));
                release.setLastUpdatedAt(rs.getTimestamp("lastUpdatedAt"));
                return release;
            });
        } catch (EmptyResultDataAccessException e) {
            throw new CustomException(HttpStatus.NOT_FOUND, "Currently no data in database.");
        }
    }

    public Release getRelease(Integer id) throws CustomException {
        try {
            String query = "SELECT * FROM `releases` where `id`=?";

            return jdbcTemplate.queryForObject(query, (rs, rowNum) -> {
                Release release = new Release();
                release.setId(rs.getInt("id"));
                release.setName(rs.getString("name"));
                release.setDescription(rs.getString("description"));
                release.setCreatedAt(rs.getTimestamp("createdAt"));
                release.setReleaseDate(rs.getDate("releaseDate"));
                release.setStatus(rs.getString("status"));
                release.setLastUpdatedAt(rs.getTimestamp("lastUpdatedAt"));
                return release;
            }, id);

        } catch (EmptyResultDataAccessException e) {
            throw new CustomException(HttpStatus.NOT_FOUND, "Release doesn't exists");
        }
    }

    public Release updateRelease(Integer id, Release release) throws CustomException {

        String query = "UPDATE releases\n" +
                "SET\n" +
                "name = ?,\n" +
                "description = ?,\n" +
                "status = ?,\n" +
                "releaseDate = ?,\n" +
                "lastUpdatedAt = ?\n" +
                "WHERE id = ?;";

        try {

            jdbcTemplate.update(query, release.getName(), release.getDescription(), release.getStatus(),
                    release.getReleaseDate(),  release.getLastUpdatedAt(), id);

            return this.getRelease(id);

        } catch (DataAccessException e) {
            log.error(e.getMessage());
            throw new CustomException(HttpStatus.FORBIDDEN, "Update failed.");
        }
    }

    public String deleteRelease(Integer id) {

        String query = "DELETE FROM `releases`\n" +
                "WHERE `id`=?";

        try {
            jdbcTemplate.update(query, id);
            return "successful";
        } catch (DataAccessException e) {
            log.error(e.getMessage());
            return "unsuccessful";
        }
    }
}
