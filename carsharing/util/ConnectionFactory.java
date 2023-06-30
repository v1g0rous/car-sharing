package carsharing.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    private static Connection connection;
    private static final String JDBC_DRIVER = "org.h2.Driver";
    private static final String DB_DIR = "jdbc:h2:.\\src\\carsharing\\db\\";
    private static String DB_NAME;

    public static void setDbName(String dbName) {
        DB_NAME = dbName;
    }

    private ConnectionFactory () {
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            new Log("Couldn't init JDBC driver", e);
        }
    }


    public static Connection getConnection() {
        return createConnection();
    }

    private static Connection createConnection() {

        try {
            if (connection != null && !connection.isClosed()) {
                return connection;
            } else {
                String DB_URL = DB_DIR + DB_NAME;

                connection = DriverManager.getConnection(DB_URL);
            }

        } catch (SQLException e) {
            new Log("ERROR: Unable to Connect to Database - " + DB_NAME, e);
        }
        return connection;
    }



}
