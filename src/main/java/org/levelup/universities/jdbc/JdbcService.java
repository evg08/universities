package org.levelup.universities.jdbc;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcService {
    public Connection openConnection() throws SQLException {
        //JDBC url:
        //jdbc:<vendor_name>://<url>:<port>/<database_name>
        //return each time new connection
        return DriverManager.getConnection("jdbc:postgresql://localhost:5432/universities_2","postgres","admin");
    }
}
