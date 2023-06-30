package carsharing.service;

import carsharing.DAO.CustomerDAO;
import carsharing.DAO.CustomerDAOImpl;
import carsharing.entity.Customer;
import carsharing.util.Log;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerServiceImpl implements CustomerService{

    CustomerDAO customerDAO;

    public CustomerServiceImpl() {
        customerDAO = new CustomerDAOImpl();
    }

    @Override
    public List<Customer> getAllCustomers() {
        List<Customer> customerList = new ArrayList<>();

        try {
            customerList = customerDAO.getAll();
        } catch (SQLException e) {
            new Log("Error: failed to getAllCompanies()", e);
        }

        return customerList;
    }

    @Override
    public Customer getCustomerByName(String customerName) {
        Customer customer = null;
        try {
            customer = customerDAO.getByName(customerName);
        } catch (SQLException e) {
            new Log("Error: failed to getCompanyByName()", e);
        }

        return customer;
    }

    @Override
    public boolean createNewCustomer(String name) throws SQLException {
        Customer customer = new Customer(name);

        int result = customerDAO.insert(customer);

        return result > 0 ? true : false;
    }


}
