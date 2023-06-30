package carsharing.service;

import carsharing.DAO.CarDAO;
import carsharing.DAO.CarDAOImpl;
import carsharing.entity.Car;
import carsharing.entity.Company;
import carsharing.util.Log;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CarServiceImpl implements CarService {
    CarDAO carDAO;

    public CarServiceImpl() {
        carDAO = new CarDAOImpl();
    }

    @Override
    public List<Car> getAllCarsByCompanyName(String companyName) {
        List<Car> carList = new ArrayList<>();

        try {
            carList = carDAO.getAllByCompanyName(companyName);
        } catch (SQLException e) {
            new Log("Error: failed to getAllByCompanyName()", e);
        }

        return carList;
    }

    @Override
    public boolean createNewCar(String name, Company company) throws SQLException {

        int companyId = company.getId();
        Car car = new Car(name, companyId);

        int result = carDAO.insert(car);

        return result > 0 ? true : false;
    }

    @Override
    public List<Car> getAvailableCarsByCompanyName(String companyName) {
        List<Car> carList = new ArrayList<>();

        try {
            carList = carDAO.getAvailableCarsByCompanyName(companyName);
        } catch (SQLException e) {
            new Log("Error: failed to getAvailableCarsByCompanyName()", e);
        }

        return carList;
    }

    @Override
    public Car getCarByName(String carName) {
        Car car = null;
        try {
            car = carDAO.getByName(carName);
        } catch (SQLException e) {
            new Log("Error: failed to getCarByName()", e);
        }

        return car;
    }

}
