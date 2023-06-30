package carsharing.DAO;

import carsharing.entity.Customer;
import carsharing.util.ConnectionFactory;
import carsharing.util.Log;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAOImpl implements CustomerDAO{
    @Override
    public Customer get(int id) throws SQLException {
        return null;
    }

    @Override
    public List<Customer> getAll() throws SQLException {
        String sql = "SELECT * FROM CUSTOMER";
        List<Customer> customers = new ArrayList<>();
        Customer customer = null;

        Connection con = null;
        PreparedStatement pst = null;
        ResultSet resultSet = null;

        try {
            con = ConnectionFactory.getConnection();
            pst = con.prepareStatement(sql);
            resultSet = pst.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("ID");
                Integer rentedCarId = resultSet.getInt("RENTED_CAR_ID");
                String name = resultSet.getString("NAME");

                customer = new Customer(id, rentedCarId, name);
                customers.add(customer);
            }
        } catch (SQLException e) {
            new Log("Error: failed to getAll customers", e);
        } finally {
            pst.close();
            con.close();
        }

        return customers;
    }

    @Override
    public int save(Customer customer) throws SQLException {
        return 0;
    }

    @Override
    public int insert(Customer customer) throws SQLException {
        String sql = "INSERT INTO CUSTOMER (NAME) VALUES (?);";
        Connection con = null;
        PreparedStatement pst = null;
        int result = 0;

        try {
            con = ConnectionFactory.getConnection();
            pst = con.prepareStatement(sql);

            pst.setString(1, customer.getName());
            result = pst.executeUpdate();
        } catch (SQLException e) {
            new Log("Error: failed to insert customer", e);
        } finally {
            pst.close();
            con.close();
        }

        return result;
    }

    @Override
    public int update(Customer customer) throws SQLException {
        String sql = "UPDATE CUSTOMER " +
                "SET " +
                "NAME = ?, " +
                "RENTED_CAR_ID = ?" +
                "WHERE ID = ?;";
        Connection con = null;
        PreparedStatement pst = null;
        int result = 0;

        try {
            con = ConnectionFactory.getConnection();
            pst = con.prepareStatement(sql);

            pst.setString(1, customer.getName());
            pst.setObject(2, customer.getRentedCarId());
            pst.setInt(3, customer.getId());
            result = pst.executeUpdate();
        } catch (SQLException e) {
            new Log("Error: failed to insert customer", e);
        } finally {
            pst.close();
            con.close();
        }

        return result;
    }

    @Override
    public int delete(Customer customer) throws SQLException {
        return 0;
    }

    @Override
    public Customer getByName(String customerName) throws SQLException {
        String sql = "SELECT * FROM CUSTOMER " +
                "WHERE Name = ?" +
                ";";
        List<Customer> customers = new ArrayList<>();
        Customer customer = null;

        Connection con = null;
        PreparedStatement pst = null;
        ResultSet resultSet = null;

        try {
            con = ConnectionFactory.getConnection();
            pst = con.prepareStatement(sql);
            pst.setString(1, customerName);
            resultSet = pst.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("ID");
                Integer rentedCarId = resultSet.getObject("RENTED_CAR_ID", Integer.class);
                String name = resultSet.getString("NAME");

                customer = new Customer(id, rentedCarId, name);
                customers.add(customer);
            }
        } catch (SQLException e) {
            new Log("Error: failed to getByName customers", e);
        } finally {
            pst.close();
            con.close();
        }

        return customers.get(0);
    }
}
