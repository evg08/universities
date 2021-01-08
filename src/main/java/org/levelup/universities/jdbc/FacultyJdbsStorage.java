package org.levelup.universities.jdbc;

import java.sql.*;

public class FacultyJdbsStorage {
    private JdbcService jdbcService;

    public FacultyJdbsStorage() {
        this.jdbcService = new JdbcService();
    }

    public void createFaculty(String name, int university_id) {
        if (university_id < 0) {
            throw new IllegalArgumentException("university_id must be positive");
        }
        try (Connection connection = jdbcService.openConnection()) {
            PreparedStatement statement = connection.
                    prepareStatement("insert into faculty(name,university_id) values (?,?)");
            statement.setString(1, name);
            statement.setInt(2, university_id);
            int rowsAffected = statement.executeUpdate();
            System.out.println("Row affected" + rowsAffected);
        } catch (SQLException e) {
            System.out.println(e.getNextException());
            System.out.println(("Couldn't open new connection:" + e.getMessage()));
            throw new RuntimeException();
        }
    }

    public void deleteFaculty(int university_id) {
        if (university_id < 0) {
            throw new IllegalArgumentException("university_id must be positive");
        }
        try (Connection connection = jdbcService.openConnection()) {
            PreparedStatement statement = connection.
                    prepareStatement("delete from faculty where university_id=?");
            statement.setInt(1, university_id);

            int rowsAffected = statement.executeUpdate();
            System.out.println("Row affected" + rowsAffected);
        } catch (SQLException e) {
            System.out.println(e.getNextException());
            System.out.println(("Couldn't open new connection:" + e.getMessage()));
            throw new RuntimeException();
        }
    }

    public void findBySql(String sql, Object... args) {

        try (Connection connection = jdbcService.openConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);//select * from...where..
            int parameterIndex = 1;
            for (Object argument : args) {
                statement.setObject(parameterIndex++, argument);
            }
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(1);
                String name = rs.getString(2);
                int university_id = rs.getInt(3);
                System.out.println(name + " " + university_id);
            }
        } catch (SQLException e) {
            System.out.println(e.getNextException());
            System.out.println(("Couldn't open new connection:" + e.getMessage()));
            throw new RuntimeException();
        }

    }

    public void displayFacilties() {
        try (Connection connection = jdbcService.openConnection()) {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("select * from faculty");
            while (rs.next()){
                int id = rs.getInt(1);
                String name = rs.getString(2);
                int university_id = rs.getInt(3);
                System.out.println(name + " " + university_id);
            }
        } catch (SQLException e) {
            System.out.println(e.getNextException());
            System.out.println(("Couldn't open new connection:" + e.getMessage()));
            throw new RuntimeException();
        }
    }
}
