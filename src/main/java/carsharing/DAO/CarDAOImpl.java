package carsharing.DAO;

import carsharing.entity.Car;
import carsharing.entity.Customer;
import carsharing.util.ConnectionFactory;
import carsharing.util.Log;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CarDAOImpl implements CarDAO{
    @Override
    public Car get(int id) throws SQLException {
        return null;
    }

    @Override
    public List<Car> getAll() throws SQLException {
        return null;
    }

    public List<Car> getAllByCompanyName(String companyName) throws SQLException {
        String sql = "SELECT CAR.* FROM CAR " +
                "JOIN COMPANY " +
                "ON CAR.COMPANY_ID = COMPANY.ID " +
                "WHERE COMPANY.NAME = ?" +
                ";";

        List<Car> cars = new ArrayList<>();
        Car car = null;

        Connection con = null;
        PreparedStatement pst = null;
        ResultSet resultSet = null;

        try {
            con = ConnectionFactory.getConnection();
            pst = con.prepareStatement(sql);
            pst.setString(1, companyName);

            resultSet = pst.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("ID");
                String name = resultSet.getString("NAME");

                car = new Car(id, name);
                cars.add(car);
            }
        } catch (SQLException e) {
            new Log("Error: failed to getAll companies", e);
        } finally {
            pst.close();
            con.close();
        }

        return cars;
    }

    @Override
    public List<Car> getAvailableCarsByCompanyName(String companyName) throws SQLException {
        String sql = "SELECT CAR.* FROM CAR " +
                "JOIN COMPANY " +
                "ON CAR.COMPANY_ID = COMPANY.ID " +
                "LEFT JOIN CUSTOMER " +
                "ON CAR.ID = CUSTOMER.RENTED_CAR_ID " +
                "WHERE COMPANY.NAME = ? AND " +
                "CUSTOMER.RENTED_CAR_ID IS NULL " +
                "ORDER BY CAR.ID" +
                ";";

        List<Car> cars = new ArrayList<>();
        Car car = null;

        Connection con = null;
        PreparedStatement pst = null;
        ResultSet resultSet = null;

        try {
            con = ConnectionFactory.getConnection();
            pst = con.prepareStatement(sql);
            pst.setString(1, companyName);

            resultSet = pst.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("ID");
                String name = resultSet.getString("NAME");

                car = new Car(id, name);
                cars.add(car);
            }
        } catch (SQLException e) {
            new Log("Error: failed to getAll companies", e);
        } finally {
            pst.close();
            con.close();
        }

        return cars;
    }

    @Override
    public Car getByName(String carName) throws SQLException {
        String sql = "SELECT * FROM CAR " +
                "WHERE Name = ?" +
                ";";
        List<Car> cars = new ArrayList<>();
        Car car = null;

        Connection con = null;
        PreparedStatement pst = null;
        ResultSet resultSet = null;

        try {
            con = ConnectionFactory.getConnection();
            pst = con.prepareStatement(sql);
            pst.setString(1, carName);
            resultSet = pst.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("ID");
                String name = resultSet.getString("NAME");
                int companyId = resultSet.getInt("COMPANY_ID");

                car = new Car(id, name, companyId);
                cars.add(car);
            }
        } catch (SQLException e) {
            new Log("Error: failed to getByName", e);
        } finally {
            pst.close();
            con.close();
        }

        return cars.get(0);
    }

    @Override
    public Car getByCustomer(Customer customer) throws SQLException {
        String sql = "SELECT * FROM CAR " +
                "WHERE Id = ?" +
                ";";
        List<Car> cars = new ArrayList<>();
        Car car = null;

        Connection con = null;
        PreparedStatement pst = null;
        ResultSet resultSet = null;

        try {
            con = ConnectionFactory.getConnection();
            pst = con.prepareStatement(sql);
            pst.setInt(1, customer.getRentedCarId());
            resultSet = pst.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("ID");
                String name = resultSet.getString("NAME");
                int companyId = resultSet.getInt("COMPANY_ID");

                car = new Car(id, name, companyId);
                cars.add(car);
            }
        } catch (SQLException e) {
            new Log("Error: failed to getByName", e);
        } finally {
            pst.close();
            con.close();
        }

        return cars.get(0);
    }

    @Override
    public int save(Car car) throws SQLException {
        return 0;
    }

    @Override
    public int insert(Car car) throws SQLException {
        String sql = "INSERT INTO CAR (NAME, COMPANY_ID) VALUES (?, ?);";
        Connection con = null;
        PreparedStatement pst = null;
        int result = 0;

        try {
            con = ConnectionFactory.getConnection();
            pst = con.prepareStatement(sql);

            pst.setString(1, car.getName());
            pst.setInt(2, car.getCompanyId());
            result = pst.executeUpdate();
        } catch (SQLException e) {
            new Log("Error: failed to insert car", e);
        } finally {
            pst.close();
            con.close();
        }

        return result;
    }

    @Override
    public int update(Car car) throws SQLException {
        return 0;
    }

    @Override
    public int delete(Car car) throws SQLException {
        return 0;
    }
}
