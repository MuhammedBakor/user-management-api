package com.mohammad_bakur.user;

import com.mohammad_bakur.user.models.Usert;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UsertRowMapperTest {

    @Test
    void mapRow() throws SQLException {
        // Given
        UserRowMapper userRowMapper = new UserRowMapper();

        ResultSet resultSet = mock(ResultSet.class);

        when(resultSet.getInt("id")).thenReturn(1);
        when(resultSet.getInt("age")).thenReturn(19);
        when(resultSet.getString("name")).thenReturn("Jamila");
        when(resultSet.getString("email")).thenReturn("jamila@gmail.com");

        // When
        Usert actual = userRowMapper.mapRow(resultSet, 1);

        // Then
        Usert expected = new Usert(
                1, "Jamila", "jamila@gmail.com", 19
        );
        assertThat(actual).isEqualTo(expected);
    }
}