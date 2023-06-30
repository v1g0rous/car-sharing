package carsharing.service;

import carsharing.entity.Car;
import carsharing.entity.Company;

import java.sql.SQLException;
import java.util.List;

public interface CarService {
    List<Car> getAllCarsByCompanyName(String companyName);

    boolean createNewCar(String carName, Company company) throws SQLException;

    List<Car> getAvailableCarsByCompanyName(String companyName);

    Car getCarByName(String carName);

}
