package carsharing.DAO;

import carsharing.util.ConnectionFactory;
import carsharing.util.Log;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AppDAOImpl implements AppDAO {
    public static final String DEFAULT_DB_NAME = "database";

    public void initDatabase(String[] args) {
        String dbName = getDatabaseName(args);
        ConnectionFactory.setDbName(dbName);
        initCompanyTable();
        initCarTable();
        initCustomerTable();
    }

    private void initCustomerTable() {
        Connection con = ConnectionFactory.getConnection();
        String sql = "CREATE TABLE IF NOT EXISTS CUSTOMER(" +
                "ID INTEGER PRIMARY KEY AUTO_INCREMENT, " +
                "NAME VARCHAR(255) UNIQUE NOT NULL, " +
                "RENTED_CAR_ID INTEGER, " +
                "CONSTRAINT fk_rented_car_id FOREIGN KEY (RENTED_CAR_ID) " +
                "REFERENCES CAR(ID)" +
                ");";

        PreparedStatement pst = null;
        try {
            pst = con.prepareStatement(sql);
            pst.executeUpdate();
        } catch (Exception e) {
            new Log("Error executing sql statement: " + sql, e);
        } finally {
            try {
                pst.close();
                con.close();
            } catch (SQLException e) {
                new Log("Error closing resources to DB", e);
            }
        }
    }


    private void initCompanyTable() {

        Connection con = ConnectionFactory.getConnection();
        String sql = "CREATE TABLE IF NOT EXISTS COMPANY(ID INTEGER PRIMARY KEY AUTO_INCREMENT, NAME VARCHAR(255) UNIQUE NOT NULL);";
        PreparedStatement pst = null;
        try {
            pst = con.prepareStatement(sql);
            pst.executeUpdate();
        } catch (Exception e) {
            new Log("Error executing sql statement: " + sql, e);
        } finally {
            try {
                pst.close();
                con.close();
            } catch (SQLException e) {
                new Log("Error closing resources to DB", e);
            }
        }
    }

    private void initCarTable() {

        Connection con = ConnectionFactory.getConnection();
        String sql = "CREATE TABLE IF NOT EXISTS CAR(" +
                "ID INTEGER PRIMARY KEY AUTO_INCREMENT, " +
                "NAME VARCHAR(255) UNIQUE NOT NULL, " +
                "COMPANY_ID INTEGER NOT NULL, " +
                "CONSTRAINT fk_company_id FOREIGN KEY (COMPANY_ID) " +
                "REFERENCES COMPANY(ID)" +
                ");";

        PreparedStatement pst = null;
        try {
            pst = con.prepareStatement(sql);
            pst.executeUpdate();
        } catch (Exception e) {
            new Log("Error executing sql statement: " + sql, e);
        } finally {
            try {
                pst.close();
                con.close();
            } catch (SQLException e) {
                new Log("Error closing resources to DB", e);
            }
        }
    }

    private static String getDatabaseName(String[] args) {
        String name = DEFAULT_DB_NAME;
        try {
            for (int i = 0; i < args.length; i++) {
                if (args[i].equals("-databaseFileName")) {
                    name = args[i + 1];
                }
            }
        } catch (Exception e) {
            new Log("Error: can't find databaseName", e);
        }

        return name;
    }
}
