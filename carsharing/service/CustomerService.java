package carsharing.service;

import carsharing.entity.Customer;

import java.sql.SQLException;
import java.util.List;

public interface CustomerService {
    List<Customer> getAllCustomers();

    Customer getCustomerByName(String customerName);

    boolean createNewCustomer(String name) throws SQLException;

}
