package org.levelup.universities.jdbc;

import java.sql.*;

public class UniversitiesJdbcStorage {
    private JdbcService jdbcService;

    public UniversitiesJdbcStorage(JdbcService jdbcService) {
        this.jdbcService = jdbcService;
    }

    public void createUniversity(String name, String shortName, int foundationYear) {
        if (foundationYear < 0) {
            throw new IllegalArgumentException("Foundation year must be positive");
        }
        //Create new record in table university
        try (Connection connection = jdbcService.openConnection()) {
            //Statement,PreparedStatement,CallableStatement
            PreparedStatement statement = connection.
                    prepareStatement("insert into university(name,short_name,foundation_year) values (?,?,?)");
            statement.setString(1, name);
            statement.setString(2, shortName);
            statement.setInt(3, foundationYear);


            int rowsAffected = statement.executeUpdate();
            System.out.println("Row affected" + rowsAffected);
        } catch (SQLException e) {
            System.out.println(e.getNextException());
            System.out.println(("Couldn't open new connection:" + e.getMessage()));
            throw new RuntimeException();
        }
    }

    public void displayUniversities() {
        try (Connection connection = jdbcService.openConnection()) {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("select * from university");
            while (rs.next()){
                //rs<-pointer ti current line in cicle
                int id =rs.getInt(1);
                String name =rs.getString(2);
                String shortName=rs.getString("short_name");
                int foundationYear =rs.getInt("foundation_year");

              //  String.join()
                System.out.println( name +" " +  shortName + " "+foundationYear );
            }


        } catch (SQLException e) {
            System.out.println(e.getNextException());
            System.out.println(("Couldn't open new connection:" + e.getMessage()));
            throw new RuntimeException();
        }
    }

    public void findBySql(String sql,Object ...args){ //

        try (Connection connection=jdbcService.openConnection()){
          PreparedStatement statement=connection.prepareStatement(sql);//select * from...where..
          int parameterIndex =1;
          for (Object argument:args){
              statement.setObject(parameterIndex++,argument);
          }
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                //rs<-pointer ti current line in cicle
                int id =rs.getInt(1);
                String name =rs.getString(2);
                String shortName=rs.getString("short_name");
                int foundationYear =rs.getInt("foundation_year");

                //  String.join()
                System.out.println( name +" " +  shortName + " "+foundationYear );
            }
        }
        catch (SQLException e) {
            System.out.println(e.getNextException());
            System.out.println(("Couldn't open new connection:" + e.getMessage()));
            throw new RuntimeException();
        }

    }
}
