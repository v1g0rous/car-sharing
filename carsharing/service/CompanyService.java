package carsharing.service;

import carsharing.entity.Company;

import java.sql.SQLException;
import java.util.List;

public interface CompanyService {
    List<Company> getAllCompanies();
    Company getCompanyByName(String companyName);

    boolean createNewCompany(String name) throws SQLException;
}
