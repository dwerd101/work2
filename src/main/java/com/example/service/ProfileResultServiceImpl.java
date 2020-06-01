package com.example.service;

import com.example.dao.ProfileResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@Service
public class ProfileResultServiceImpl implements ProfileResultService {

    JdbcTemplate jdbcTemplate;

    @Override
    public void saveProfileResultDomain(String domain, Long id) {
        //language=sql
        final String SQL_SAVE_DOMAIN = "update profile_result as test set domain = ?" +
                " where field_id in " +
                " ( select profile_result.field_id from profile_result" +
                " inner join field f on profile_result.field_id = f.id" +
                " join tables t on f.tables_id = t.id " +
                "join owners o on t.owner_id = o.id " +
                "join sources s on o.source_id = s.id" +
                " where s.id=?)\n";
        jdbcTemplate.update(SQL_SAVE_DOMAIN, preparedStatement
                -> {
            preparedStatement.setString(1,domain);
            preparedStatement.setLong(2,id);
        });
    }

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
