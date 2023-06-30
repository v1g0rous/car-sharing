package carsharing.DAO;

import carsharing.entity.Car;
import carsharing.entity.Customer;

import java.sql.SQLException;
import java.util.List;

public interface CarDAO extends DAO<Car>{
    List<Car> getAllByCompanyName(String companyName) throws SQLException;

    List<Car> getAvailableCarsByCompanyName(String companyName) throws SQLException;

    Car getByName(String carName) throws SQLException;

    Car getByCustomer(Customer customer) throws SQLException;
}
