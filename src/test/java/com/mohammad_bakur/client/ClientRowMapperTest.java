package com.mohammad_bakur.client;

import com.mohammad_bakur.client.models.Client;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ClientRowMapperTest {

    @Test
    void mapRow() throws SQLException {
        // Given
        ClientRowMapper clientRowMapper = new ClientRowMapper();

        ResultSet resultSet = mock(ResultSet.class);

        when(resultSet.getInt("id")).thenReturn(1);
        when(resultSet.getInt("age")).thenReturn(19);
        when(resultSet.getString("name")).thenReturn("Jamila");
        when(resultSet.getString("email")).thenReturn("jamila@gmail.com");

        // When
        Client actual = clientRowMapper.mapRow(resultSet, 1);

        // Then
        Client expected = new Client(
                1, "Jamila", "jamila@gmail.com", 19
        );
        assertThat(actual).isEqualTo(expected);
    }
}