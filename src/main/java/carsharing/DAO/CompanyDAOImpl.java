package carsharing.DAO;

import carsharing.entity.Car;
import carsharing.entity.Company;
import carsharing.util.ConnectionFactory;
import carsharing.util.Log;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CompanyDAOImpl implements CompanyDAO{

    @Override
    public Company get(int id) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        Company company = null;

        String sql = "SELECT id, name FROM companies WHERE id = ?";

        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, id);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            int oid = rs.getInt("id");
            String companyName = rs.getString("name");

            company = new Company(oid, companyName);
        }

        return company;
    }

    @Override
    public List<Company> getAll() throws SQLException {
        String sql = "SELECT * FROM COMPANY";
        List<Company> companies = new ArrayList<>();
        Company company = null;

        Connection con = null;
        PreparedStatement pst = null;
        ResultSet resultSet = null;

        try {
            con = ConnectionFactory.getConnection();
            pst = con.prepareStatement(sql);
            resultSet = pst.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("ID");
                String name = resultSet.getString("NAME");

                company = new Company(id, name);
                companies.add(company);
            }
        } catch (SQLException e) {
            new Log("Error: failed to getAll companies", e);
        } finally {
            pst.close();
            con.close();
        }

        return companies;
    }

    @Override
    public int save(Company company) throws SQLException {
        return 0;
    }

    @Override
    public int insert(Company company) throws SQLException {

        String sql = "INSERT INTO COMPANY (name) VALUES (?);";
        Connection con = null;
        PreparedStatement pst = null;
        int result = 0;

        try {
            con = ConnectionFactory.getConnection();
            pst = con.prepareStatement(sql);

            pst.setString(1, company.getName());
            result = pst.executeUpdate();
        } catch (SQLException e) {
            new Log("Error: failed to insert company", e);
        } finally {
            pst.close();
            con.close();
        }

        return result;
    }

    @Override
    public int update(Company company) throws SQLException {
        Connection connection = ConnectionFactory.getConnection();

        String sql = "UPDATE companies SET name = ? WHERE id = ?";

        PreparedStatement ps = connection.prepareStatement(sql);

        ps.setString(1, company.getName());
        ps.setInt(2, company.getId());

        int result = ps.executeUpdate();

        ps.close();
        connection.close();

        return result;
    }

    @Override
    public int delete(Company company) throws SQLException {
        Connection connection = ConnectionFactory.getConnection();

        String sql = "DELETE FROM companies WHERE id = ?";

        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, company.getId());

        int result = ps.executeUpdate();

        ps.close();
        connection.close();

        return result;
    }

    @Override
    public Company getByName(String companyName) throws SQLException {
        String sql = "SELECT * FROM COMPANY " +
                "WHERE Name = ?" +
                ";";
        List<Company> companies = new ArrayList<>();
        Company company = null;

        Connection con = null;
        PreparedStatement pst = null;
        ResultSet resultSet = null;

        try {
            con = ConnectionFactory.getConnection();
            pst = con.prepareStatement(sql);
            pst.setString(1, companyName);
            resultSet = pst.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("ID");
                String name = resultSet.getString("NAME");

                company = new Company(id, name);
                companies.add(company);
            }
        } catch (SQLException e) {
            new Log("Error: failed to getAll companies", e);
        } finally {
            pst.close();
            con.close();
        }

        return companies.get(0);
    }

    @Override
    public Company getByCar(Car rentedCar) throws SQLException {
        String sql = "SELECT * FROM COMPANY " +
                "LEFT JOIN CAR " +
                "ON CAR.COMPANY_ID = COMPANY.ID " +
                "WHERE CAR.COMPANY_ID = ?" +
                ";";
        List<Company> companies = new ArrayList<>();
        Company company = null;

        Connection con = null;
        PreparedStatement pst = null;
        ResultSet resultSet = null;

        try {
            con = ConnectionFactory.getConnection();
            pst = con.prepareStatement(sql);
            pst.setInt(1, rentedCar.getCompanyId());
            resultSet = pst.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("ID");
                String name = resultSet.getString("NAME");

                company = new Company(id, name);
                companies.add(company);
            }
        } catch (SQLException e) {
            new Log("Error: failed to getAll companies", e);
        } finally {
            pst.close();
            con.close();
        }

        return companies.get(0);
    }
}
