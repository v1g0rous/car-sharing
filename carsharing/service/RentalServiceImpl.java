package carsharing.service;

import carsharing.DAO.*;
import carsharing.entity.Car;
import carsharing.entity.Company;
import carsharing.entity.Customer;
import carsharing.util.Log;

import java.sql.SQLException;

public class RentalServiceImpl implements RentalService {
    CarDAO carDAO;
    CustomerDAO customerDAO;
    CompanyDAO companyDAO;

    public RentalServiceImpl() {
        this.carDAO = new CarDAOImpl();
        this.customerDAO = new CustomerDAOImpl();
        this.companyDAO = new CompanyDAOImpl();
    }

    @Override
    public boolean assignCarToCustomer(Car rentedCar, Customer customer) {
        int changes = 0;
        try {
            customer.setRentedCarId(rentedCar.getId());
            changes = customerDAO.update(customer);
        } catch (SQLException e) {
            new Log("Error: failed to assignCarToCustomer()", e);
        }

        return changes > 0;
    }

    @Override
    public boolean isCustomerRentedCar(Customer customer) {
        return customer.getRentedCarId() != null;
    }

    @Override
    public Car getRentedCarByCustomer(Customer customer) {
        Car car = null;
        try {
            car = carDAO.getByCustomer(customer);
        } catch (SQLException e) {
            new Log("Error: failed to getCarByName()", e);
        }

        return car;
    }

    @Override
    public Company getCompanyByCar(Car rentedCar) {
        Company company = null;
        try {
            company = companyDAO.getByCar(rentedCar);
        } catch (SQLException e) {
            new Log("Error: failed to getCompanyByCar", e);
        }

        return company;
    }

    @Override
    public Boolean removeRentedCar(Customer customer) {
        int changes = 0;
        try {
            customer.setRentedCarId(null);
            changes = customerDAO.update(customer);
        } catch (SQLException e) {
            new Log("Error: failed to removeRentedCar()", e);
        }

        return changes > 0;
    }


}
