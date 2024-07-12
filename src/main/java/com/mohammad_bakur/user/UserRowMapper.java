package com.mohammad_bakur.user;

import com.mohammad_bakur.user.models.Usert;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class UserRowMapper implements RowMapper<Usert> {
    @Override
    public Usert mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Usert(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("email"),
                rs.getInt("age")
        );
    }
}

/* In the selectallusers method
         RowMapper<Client> clientRowMapper = (rs, rowNum) -> {
            Client client = new Client(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getInt("age")
            );
            return client;
        };

        List<Client> clients = jdbcTemplate.query(sql, clientRowMapper);

        return clients;
         */
