package carsharing.service;

import carsharing.DAO.CompanyDAO;
import carsharing.DAO.CompanyDAOImpl;
import carsharing.entity.Company;
import carsharing.util.Log;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CompanyServiceImpl implements CompanyService{
    CompanyDAO companyDAO;

    public CompanyServiceImpl() {
        companyDAO = new CompanyDAOImpl();
    }


    @Override
    public List<Company> getAllCompanies() {
        List<Company> companyList = new ArrayList<>();

        try {
            companyList = companyDAO.getAll();
        } catch (SQLException e) {
            new Log("Error: failed to getAllCompanies()", e);
        }

        return companyList;
    }

    @Override
    public Company getCompanyByName(String companyName) {
        Company company = null;
        try {
            company = companyDAO.getByName(companyName);
        } catch (SQLException e) {
            new Log("Error: failed to getCompanyByName()", e);
        }

        return company;
    }


    @Override
    public boolean createNewCompany(String name) throws SQLException {
        Company company = new Company(name);

        int result = companyDAO.insert(company);

        return result > 0 ? true : false;
    }


}
