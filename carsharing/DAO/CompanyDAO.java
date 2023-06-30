package carsharing.DAO;

import carsharing.entity.Car;
import carsharing.entity.Company;

import java.sql.SQLException;

public interface CompanyDAO extends DAO<Company> {
    Company getByName(String companyName) throws SQLException;

    Company getByCar(Car rentedCar) throws SQLException;
}
