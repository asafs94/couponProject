package dao.DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import dao.interfaces.CompanyDAO;
import databaseConnections.ConnectionPool;
import elements.Company;
import elements.Coupon;
import elements.CouponType;
import exceptions.ApplicationException;
import exceptions.ErrorType;

/**A class for withdrawing information on companies from the database,
 * specifically with derby sql database.
 * 
 * @author asafs94
 *
 */
public class CompanyDAODB implements CompanyDAO{

	private ConnectionPool pool;
	
	
	public CompanyDAODB() throws ApplicationException {
		try {
			pool= ConnectionPool.getInstance();
		} catch (SQLException e) {
			throw new ApplicationException(e, ErrorType.GENERIC_SYSTEM_ERROR);
		}
	
	}
	
	@Override
	public void createCompany(Company company) throws ApplicationException {
		String sql= "INSERT INTO company (comp_name,password,email) VALUES(?,?,?)";
		
		Connection con = null;
		try {
			con = this.pool.getConnection();
			PreparedStatement preparedStatement = con.prepareStatement(sql);

			preparedStatement.setString(1, company.getCompName());
			preparedStatement.setString(2, company.getPassword());
			preparedStatement.setString(3, company.getEmail());

			preparedStatement.executeUpdate();

		} catch (Exception e) {
			throw new ApplicationException(e, ErrorType.GENERIC_SYSTEM_ERROR);
		} finally {
			if (con != null) {
				this.pool.returnConnection(con);
			}
		}
		
	}

	@Override
	public void deleteCompany(long company_id) throws ApplicationException {
		String sql = "DELETE FROM company WHERE id=?";
		Connection con = null;
		try {
			con = this.pool.getConnection();
			PreparedStatement preparedStatement = con.prepareStatement(sql);

			preparedStatement.setLong(1, company_id);

			preparedStatement.executeUpdate();

		} catch (Exception e) {
			throw new ApplicationException(e, ErrorType.GENERIC_SYSTEM_ERROR);
		} finally {
			if (con != null) {
				this.pool.returnConnection(con);
			}
		}
	}

	@Override
	public void updateCompany(Company company) throws ApplicationException {
		String sql = "UPDATE company SET comp_name = ?, password = ?, email = ? WHERE id = ?";

		Connection con = null;
		try {
			con = this.pool.getConnection();
			PreparedStatement preparedStatement = con.prepareStatement(sql);

			preparedStatement.setString(1, company.getCompName());
			preparedStatement.setString(2, company.getPassword());
			preparedStatement.setString(3, company.getEmail());
			preparedStatement.setLong(4, company.getId());

			preparedStatement.executeUpdate();

		} catch (Exception e) {
			throw new ApplicationException(e, ErrorType.GENERIC_SYSTEM_ERROR);
		} finally {
			if (con != null) {
				this.pool.returnConnection(con);
			}
		}
	}

	@Override
	public Company readCompany(long company_id) throws ApplicationException {
		Company company = null;
		String sql = "SELECT * FROM company WHERE id=?";

		Connection con = null;
		try {
			con = this.pool.getConnection();
			PreparedStatement preparedStatement = con.prepareStatement(sql);

			preparedStatement.setLong(1, company_id);

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				String name = rs.getString(2);
				String password = rs.getString(3);
				String email= rs.getString(4);
				company = new Company(company_id, name, password, email, null);
			}
			
			
		} catch (Exception e) {
			throw new ApplicationException(e, ErrorType.GENERIC_SYSTEM_ERROR);
		} finally {
			if (con != null) {
				this.pool.returnConnection(con);
			}
		}		
			return company;
	}

	@Override
	public Collection<Company> readAllCompanies() throws ApplicationException {
		Collection<Company> companies = new ArrayList<Company>();
		String sql = "SELECT * FROM company";

		Connection con = null;

		try {
			con = this.pool.getConnection();
			Statement statement = con.createStatement();
			ResultSet rs = statement.executeQuery(sql);

			while (rs.next()) {
				long company_id = rs.getLong(1);
				String compName = rs.getString(2);
				String password = rs.getString(3);
				String email= rs.getString(4);
				Company company = new Company(company_id, compName, password, email,null);
				companies.add(company);
			}
		} catch (Exception e) {
			throw new ApplicationException(e, ErrorType.GENERIC_SYSTEM_ERROR);
		} finally {
			if (con != null) {
				this.pool.returnConnection(con);
			}
		}
		
		return companies;
	}

	@Override
	public Collection<Coupon> readCoupons(long company_id) throws ApplicationException {
		Collection<Coupon> coupons = new ArrayList<Coupon>();
		String sql = "SELECT * FROM coupon WHERE company_id=?";

		Connection con = null;

		try {
			con = this.pool.getConnection();
			PreparedStatement preparedStatement = con.prepareStatement(sql);
			preparedStatement.setLong(1, company_id);

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				// Getting Coupon Data:

				// Coupon ID:
				long id = rs.getLong(1);
				// Coupon Title:
				String title = rs.getString(2);
				// Coupon Start Date (switching from java.sql.Date to java.util.Date):
				java.sql.Date startDateInSQL = rs.getDate(3);
				Date startDate = new Date(startDateInSQL.getTime());
				// Coupon End Date (switching from java.sql.Date to java.util.Date):
				java.sql.Date endDateInSQL = rs.getDate(4);
				Date endDate = new Date(endDateInSQL.getTime());
				// Amount of Coupons:
				int amount = rs.getInt(5);
				// Coupon Type (using the String in SQL to search for the couponType):
				String couponTypeStringValue = rs.getString(6);
				CouponType type = CouponType.fromString(couponTypeStringValue);
				// Coupon Message:
				String message = rs.getString(7);
				// Coupon Price:
				double price = rs.getDouble(8);
				// Coupon Image String:
				String image = rs.getString(9);
				// Company that the Coupon belongs to: is company_id (10)
				
				// Creating Coupon:
				Coupon coupon = new Coupon(id, title, startDate, endDate, amount, type, message, price, image,
						company_id);
				// Adding the Coupon to the Collection:
				coupons.add(coupon);
			}
		} catch (Exception e) {
			throw new ApplicationException(e, ErrorType.GENERIC_SYSTEM_ERROR);
		} finally {
			if (con != null) {
				this.pool.returnConnection(con);
			}
		}
		
		return coupons;
	}

	@Override
	public boolean login(String name, String password) throws ApplicationException {
		String sql = "SELECT comp_name, password FROM company WHERE comp_name=? AND password=?";
		boolean login= false;
		Connection con = null;

		try {
			con = this.pool.getConnection();
			PreparedStatement preparedStatement = con.prepareStatement(sql);
			preparedStatement.setString(1, name);
			preparedStatement.setString(2, password);
			ResultSet rs = preparedStatement.executeQuery();
			//If there is a value for these name and password, true will be returned / else - false;
			login = rs.next();
		} catch (Exception e) {
			throw new ApplicationException(e, ErrorType.GENERIC_SYSTEM_ERROR);
		} finally {
			if (con != null) {
				this.pool.returnConnection(con);
			}
		}
		
		return login;
	}

	@Override
	public Company readByName(String name) throws ApplicationException {
		Company company = null;
		String sql = "SELECT * FROM company WHERE comp_name=?";

		Connection con = null;
		try {
			con = this.pool.getConnection();
			PreparedStatement preparedStatement = con.prepareStatement(sql);

			preparedStatement.setString(1, name);

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				long id = rs.getLong(1);
				String password = rs.getString(3);
				String email= rs.getString(4);
				company = new Company(id, name, password, email, null);
			}
			
			
		} catch (Exception e) {
			throw new ApplicationException(e, ErrorType.GENERIC_SYSTEM_ERROR);
		} finally {
			if (con != null) {
				this.pool.returnConnection(con);
			}
		}		
			return company;
	}
	
	
	

}
