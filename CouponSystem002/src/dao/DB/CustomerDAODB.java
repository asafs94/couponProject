package dao.DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import dao.interfaces.CustomerDAO;
import databaseConnections.ConnectionPool;
import elements.Coupon;
import elements.CouponType;
import elements.Customer;
import exceptions.ApplicationException;
import exceptions.ErrorType;

public class CustomerDAODB implements CustomerDAO {

	ConnectionPool pool;

	public CustomerDAODB() throws ApplicationException {
		try {
			this.pool = ConnectionPool.getInstance();
		} catch (Exception e) {
			throw new ApplicationException(e, ErrorType.GENERIC_SYSTEM_ERROR);
		}

	}

	@Override
	public void createCustomer(Customer customer) throws ApplicationException {
		String sql = "INSERT INTO customer(cust_name,password) VALUES (?,?)";

		Connection con = null;
		try {
			con = this.pool.getConnection();
			PreparedStatement preparedStatement = con.prepareStatement(sql);

			preparedStatement.setString(1, customer.getCustName());
			preparedStatement.setString(2, customer.getPassword());

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
	public void deleteCustomer(long customer_id) throws ApplicationException {
		String sql = "DELETE FROM customer WHERE id=?";
		Connection con = null;
		try {
			con = this.pool.getConnection();
			PreparedStatement preparedStatement = con.prepareStatement(sql);

			preparedStatement.setLong(1, customer_id);

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
	public void updateCustomer(Customer customer) throws ApplicationException {
		String sql = "UPDATE customer SET cust_name = ?, password = ? WHERE id = ?";

		Connection con = null;
		try {
			con = this.pool.getConnection();
			PreparedStatement preparedStatement = con.prepareStatement(sql);

			preparedStatement.setString(1, customer.getCustName());
			preparedStatement.setString(2, customer.getPassword());
			preparedStatement.setLong(3, customer.getId());

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
	public Customer readCustomer(long customer_id) throws ApplicationException {
		Customer customer = null;
		String sql = "SELECT * FROM customer WHERE id=?";

		Connection con = null;
		try {
			con = this.pool.getConnection();
			PreparedStatement preparedStatement = con.prepareStatement(sql);

			preparedStatement.setLong(1, customer_id);

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				String name = rs.getString(2);
				String password = rs.getString(3);
				customer = new Customer(customer_id, name, password, null);
			}
		} catch (Exception e) {
			throw new ApplicationException(e, ErrorType.GENERIC_SYSTEM_ERROR);
		} finally {
			if (con != null) {
				this.pool.returnConnection(con);
			}
		}

			return customer;

	}

	@Override
	public Collection<Customer> readAllCustomers() throws ApplicationException {
		Collection<Customer> customers = new ArrayList<Customer>();
		String sql = "SELECT * FROM customer";

		Connection con = null;

		try {
			con = this.pool.getConnection();
			Statement statement = con.createStatement();
			ResultSet rs = statement.executeQuery(sql);

			while (rs.next()) {
				long customer_id = rs.getLong(1);
				String name = rs.getString(2);
				String password = rs.getString(3);
				Customer customer = new Customer(customer_id, name, password, null);
				customers.add(customer);
			}
		} catch (Exception e) {
			throw new ApplicationException(e, ErrorType.GENERIC_SYSTEM_ERROR);
		} finally {
			if (con != null) {
				this.pool.returnConnection(con);
			}
		}

		return customers;
	}

	@Override
	public Collection<Coupon> readCoupons(long customer_id) throws ApplicationException {
		Collection<Coupon> coupons = new ArrayList<Coupon>();
		String sql = "SELECT coupon.* FROM coupon " + "INNER JOIN customer_coupon "
				+ "ON coupon.id = customer_coupon.coupon_id " + "WHERE customer_coupon.cust_id= ?";

		Connection con = null;

		try {
			con = this.pool.getConnection();
			PreparedStatement preparedStatement = con.prepareStatement(sql);
			preparedStatement.setLong(1, customer_id);

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
				// Company that the Coupon belongs to:
				long company_id = rs.getLong(10);
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
		String sql = "SELECT cust_name, password FROM customer WHERE cust_name=? AND password=?";
		boolean login = false;
		Connection con = null;

		try {
			con = this.pool.getConnection();
			PreparedStatement preparedStatement = con.prepareStatement(sql);
			preparedStatement.setString(1, name);
			preparedStatement.setString(2, password);
			ResultSet rs = preparedStatement.executeQuery();
			// If there is a value for these name and password, true will be returned / else
			// - false;
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
	public void createCustomerCoupon(long customer_id, long coupon_id) throws ApplicationException {
		String sql = "INSERT INTO customer_coupon VALUES(?,?)";

		Connection con = null;
		try {
			con = this.pool.getConnection();
			PreparedStatement preparedStatement = con.prepareStatement(sql);

			preparedStatement.setLong(1, customer_id);
			preparedStatement.setLong(2, coupon_id);

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
	public void deleteCustomerCoupons(long customer_id) throws ApplicationException {
		String sql = "DELETE FROM customer_coupon WHERE cust_id = ?";

		Connection con = null;
		try {
			con = this.pool.getConnection();
			PreparedStatement preparedStatement = con.prepareStatement(sql);

			preparedStatement.setLong(1, customer_id);

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
	public Customer readByName(String name) throws ApplicationException {
		Customer customer = null;
		String sql = "SELECT * FROM customer WHERE cust_name=?";

		Connection con = null;
		try {
			con = this.pool.getConnection();
			PreparedStatement preparedStatement = con.prepareStatement(sql);

			preparedStatement.setString(1, name);

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				long id = rs.getLong(1);
				String password = rs.getString(3);
				customer = new Customer(id, name, password, null);
			}
		} catch (Exception e) {
			throw new ApplicationException(e, ErrorType.GENERIC_SYSTEM_ERROR);
		} finally {
			if (con != null) {
				this.pool.returnConnection(con);
			}
		}

			return customer;
	}
	

}
