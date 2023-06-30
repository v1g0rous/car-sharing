package carsharing.DAO;

import carsharing.entity.Customer;

import java.sql.SQLException;

public interface CustomerDAO extends DAO<Customer>{
    Customer getByName(String customerName) throws SQLException;
}
