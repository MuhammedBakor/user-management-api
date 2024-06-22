package com.mohammad_bakur.client.repository;

import com.mohammad_bakur.client.ClientDAO;
import com.mohammad_bakur.client.ClientRowMapper;
import com.mohammad_bakur.client.models.Client;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("jdbc")
public class ClientJDBCDataAccessService implements ClientDAO {
    private final JdbcTemplate jdbcTemplate;
    private final ClientRowMapper clientRowMapper;


    public ClientJDBCDataAccessService(JdbcTemplate jdbcTemplate, ClientRowMapper clientRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.clientRowMapper = clientRowMapper;
    }

    @Override
    public List<Client> selectAllClients() {
        var sql = """
                SELECT id, name, email, age
                FROM client
                """;

        return jdbcTemplate.query(sql, clientRowMapper);
    }

    @Override
    public Optional<Client> selectClientById(Integer id) {
        var sql = """
                SELECT id, name, email, age
                FROM client
                WHERE id = ?""";

        return jdbcTemplate.query(sql, clientRowMapper, id)
                .stream().findFirst();
    }

    @Override
    public void insertClient(Client client) {
        String sql = """
                INSERT INTO client(name, email, age)
                VALUES (?, ?, ?);
                """;

        int result = jdbcTemplate.update(
                sql,
                client.getName(), client.getEmail(), client.getAge());

        System.out.println("jdbcTemplate = " + result);
    }

    @Override
    public boolean existsClientWithEmail(String email) {
        String sql = """
                SELECT COUNT(id)
                FROM Client
                WHERE email = ?
                """;
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, email);
        return count != null && count > 0;
    }

    @Override
    public boolean existsClientWithId(Integer id) {
        String sql = """
                SELECT COUNT(id)
                FROM Client
                WHERE id = ?
                """;
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id);
        return count != null && count > 0;
    }

    @Override
    public void deleteClientById(Integer id) {
        String sql = """
                DELETE
                FROM Client
                WHERE id = ?
                """;
        int result = jdbcTemplate.update(sql, id);
        System.out.println("deleteUserById result = " + result);
    }

    @Override
    public void updateClient(Client entity) {
        if(entity.getName() != null){
            String sql = """
                UPDATE client SET name = ? WHERE id = ?
                """;

            int result = jdbcTemplate.update(sql, entity.getName(), entity.getId());

            System.out.println("update client name result = " + result);
        }

        if(entity.getAge() != null){
            String sql = """
                UPDATE client SET age = ? WHERE id = ?
                """;

            int result = jdbcTemplate.update(sql, entity.getAge(), entity.getId());

            System.out.println("update client age result = " + result);
        }

        if(entity.getEmail() != null){
            String sql = """
                UPDATE client SET email = ? WHERE id = ?
                """;

            int result = jdbcTemplate.update(sql, entity.getEmail(), entity.getId());

            System.out.println("update client email result = " + result);
        }
    }
}
