package carsharing.service;

import carsharing.entity.Car;
import carsharing.entity.Company;
import carsharing.entity.Customer;

public interface RentalService {
    boolean assignCarToCustomer(Car rentedCar, Customer customer);
    boolean isCustomerRentedCar(Customer customer);


    Car getRentedCarByCustomer(Customer customer);

    Company getCompanyByCar(Car rentedCar);

    Boolean removeRentedCar(Customer customer);
}
