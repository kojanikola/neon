package com.neon.releasetracker.repositories;

import com.neon.releasetracker.exceptions.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
public class StatusRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public String checkStatus(String status) throws CustomException {
        try {
            String query = "Select * from `statuses` where value=?";
            return jdbcTemplate.queryForObject(query, (rs, rowNum) -> {
                return rs.getString("value");
            }, status);
        } catch (EmptyResultDataAccessException e) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Status not valid.");
        }
    }
}
