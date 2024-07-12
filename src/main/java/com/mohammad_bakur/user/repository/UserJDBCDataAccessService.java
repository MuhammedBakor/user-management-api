package com.mohammad_bakur.user.repository;

import com.mohammad_bakur.user.UserDAO;
import com.mohammad_bakur.user.UserRowMapper;
import com.mohammad_bakur.user.models.Usert;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("jdbc")
public class UserJDBCDataAccessService implements UserDAO {
    private final JdbcTemplate jdbcTemplate;
    private final UserRowMapper userRowMapper;


    public UserJDBCDataAccessService(JdbcTemplate jdbcTemplate, UserRowMapper userRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.userRowMapper = userRowMapper;
    }

    @Override
    public List<Usert> selectAllClients() {
        var sql = """
                SELECT id, name, email, age
                FROM usert
                """;

        return jdbcTemplate.query(sql, userRowMapper);
    }

    @Override
    public Optional<Usert> selectClientById(Integer id) {
        var sql = """
                SELECT id, name, email, age
                FROM usert
                WHERE id = ?""";

        return jdbcTemplate.query(sql, userRowMapper, id)
                .stream().findFirst();
    }

    @Override
    public void insertClient(Usert usert) {
        String sql = """
                INSERT INTO usert(name, email, age)
                VALUES (?, ?, ?);
                """;

        int result = jdbcTemplate.update(
                sql,
                usert.getName(), usert.getEmail(), usert.getAge());

        System.out.println("jdbcTemplate = " + result);
    }

    @Override
    public boolean existsClientWithEmail(String email) {
        String sql = """
                SELECT COUNT(id)
                FROM Usert
                WHERE email = ?
                """;
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, email);
        return count != null && count > 0;
    }

    @Override
    public boolean existsClientWithId(Integer id) {
        String sql = """
                SELECT COUNT(id)
                FROM Usert
                WHERE id = ?
                """;
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id);
        return count != null && count > 0;
    }

    @Override
    public void deleteClientById(Integer id) {
        String sql = """
                DELETE
                FROM Usert
                WHERE id = ?
                """;
        int result = jdbcTemplate.update(sql, id);
        System.out.println("deleteUserById result = " + result);
    }

    @Override
    public void updateClient(Usert entity) {
        if(entity.getName() != null){
            String sql = """
                UPDATE usert SET name = ? WHERE id = ?
                """;

            int result = jdbcTemplate.update(sql, entity.getName(), entity.getId());

            System.out.println("update usert name result = " + result);
        }

        if(entity.getAge() != null){
            String sql = """
                UPDATE usert SET age = ? WHERE id = ?
                """;

            int result = jdbcTemplate.update(sql, entity.getAge(), entity.getId());

            System.out.println("update usert age result = " + result);
        }

        if(entity.getEmail() != null){
            String sql = """
                UPDATE usert SET email = ? WHERE id = ?
                """;

            int result = jdbcTemplate.update(sql, entity.getEmail(), entity.getId());

            System.out.println("update usert email result = " + result);
        }
    }
}
