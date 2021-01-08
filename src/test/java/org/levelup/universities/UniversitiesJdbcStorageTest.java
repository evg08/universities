package org.levelup.universities;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.levelup.universities.jdbc.JdbcService;
import org.levelup.universities.jdbc.UniversitiesJdbcStorage;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UniversitiesJdbcStorageTest {
    private UniversitiesJdbcStorage jdbcStorage;
    @Mock
    private JdbcService jdbcService;
    @Mock
    private Connection connection;
    @Mock
    private PreparedStatement statement;


    @BeforeEach
    public void setupJdbcStorage() throws SQLException {
        MockitoAnnotations.openMocks(this);
        //1 variant
        //   jdbcService = Mockito.mock(JdbcService.class);
        jdbcStorage = new UniversitiesJdbcStorage(jdbcService);
        Mockito.when(jdbcService.openConnection()).thenReturn(connection);
        Mockito.when(connection.prepareStatement(ArgumentMatchers.anyString())).thenReturn(statement);
    }

    @Test
    public void testCreateUniversity_whenDataIsValid_thenCreateUniversity() throws SQLException {
        //given
        String name = "name";
        String shortName = " short name";
        int foundationYear = 1994;
        Mockito.when(statement.executeUpdate()).thenReturn(10);
        //when
        jdbcStorage.createUniversity(name, shortName, foundationYear);

        Mockito.verify(statement, Mockito.times(2))
                .setString(ArgumentMatchers.anyInt(), ArgumentMatchers.anyString());
        Mockito.verify(statement).setInt(3, foundationYear);
        Mockito.verify(statement).executeUpdate();

    }

    @Test
    public void testCreateUniversity_whenFoundationYearIsNegative_thenException() throws SQLException {
        //given
        String name = "name";
        String shortName = " short name";
        int foundationYear = -1988;
        Mockito.when(statement.executeUpdate()).thenReturn(10);
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            jdbcStorage.createUniversity(name, shortName, foundationYear);
        });
    }

    @Test
    public void testCreateUniversity_whenOpenConnectionGenerateException_thenException() throws SQLException {
        //given
        String name = "name";
        String shortName = " short name";
        int foundationYear = 1988;
        Mockito.when(jdbcService.openConnection()).thenThrow(SQLException.class); //сюда нельзя передать метод где тип возвращаемого значения null
       //Mockito.doThrow(SQLException.class).when(jdbcService).openConnection(); //
        Mockito.when(statement.executeUpdate()).thenReturn(10);
        Assertions.assertThrows(RuntimeException.class, () -> {
            jdbcStorage.createUniversity(name, shortName, foundationYear);
        });

    }

}
