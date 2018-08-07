package dao.DB;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;

import dao.interfaces.CouponDAO;
import databaseConnections.ConnectionPool;
import elements.Coupon;
import elements.CouponType;
import exceptions.ApplicationException;
import exceptions.ErrorType;

public class CouponDAODB implements CouponDAO {

	ConnectionPool pool;

	public CouponDAODB() throws ApplicationException {
		try {
			pool = ConnectionPool.getInstance();
		} catch (Exception e) {
			throw new ApplicationException(e, ErrorType.GENERIC_SYSTEM_ERROR);
		}
	}

	@Override
	public void createCoupon(Coupon coupon) throws ApplicationException {
		String sql = "INSERT INTO coupon(" + "title," + "start_date," + "end_date," + "amount," + "type," + "message,"
				+ "price," + "image," + "company_id) VALUES (?,?,?,?,?,?,?,?,?)";

		Connection con = null;
		try {
			con = this.pool.getConnection();
			PreparedStatement preparedStatement = con.prepareStatement(sql);
			Date startDateInSQL = new Date(coupon.getStartDate().getTime());
			Date endDateInSQL = new Date(coupon.getEndDate().getTime());
			String type = coupon.getType().getText();

			preparedStatement.setString(1, coupon.getTitle());
			preparedStatement.setDate(2, startDateInSQL);
			preparedStatement.setDate(3, endDateInSQL);
			preparedStatement.setInt(4, coupon.getAmount());
			preparedStatement.setString(5, type);
			preparedStatement.setString(6, coupon.getMessage());
			preparedStatement.setDouble(7, coupon.getPrice());
			preparedStatement.setString(8, coupon.getImage());
			preparedStatement.setLong(9, coupon.getCompany_id());

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
	public void deleteCoupon(long coupon_id) throws ApplicationException {
		String sql = "DELETE FROM coupon WHERE id=?";
		Connection con = null;
		try {
			con = this.pool.getConnection();
			PreparedStatement preparedStatement = con.prepareStatement(sql);

			preparedStatement.setLong(1, coupon_id);

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
	public void updateCoupon(Coupon coupon) throws ApplicationException {
		String sql = "UPDATE coupon SET title = ?, start_date = ?, end_date = ?, amount = ?,"
				+ "type = ?, message = ?, price = ?, image = ?, company_id = ? WHERE id = ?";

		Connection con = null;
		// Converting start_date from java.util to java.sql:
		java.sql.Date startDate = new Date(coupon.getStartDate().getTime());
		// Converting end_date from java.util to java.sql:
		java.sql.Date endDate = new Date(coupon.getEndDate().getTime());

		try {
			con = this.pool.getConnection();
			PreparedStatement preparedStatement = con.prepareStatement(sql);

			preparedStatement.setString(1, coupon.getTitle()); // TITLE
			preparedStatement.setDate(2, startDate); // START_DATE (IN SQL FORM)
			preparedStatement.setDate(3, endDate); // END_DATE (IN SQL FORM)
			preparedStatement.setInt(4, coupon.getAmount()); // AMOUNT
			preparedStatement.setString(5, coupon.getType().getText()); // TYPE (IN STRING)
			preparedStatement.setString(6, coupon.getMessage()); // MESSAGE
			preparedStatement.setDouble(7, coupon.getPrice()); // PRICE
			preparedStatement.setString(8, coupon.getImage()); // IMAGE STRING
			preparedStatement.setLong(9, coupon.getCompany_id()); // BELONGING COMPANY ID
			preparedStatement.setLong(10, coupon.getId()); // UPDATING BY COUPON ID

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
	public Coupon readCoupon(long coupon_id) throws ApplicationException {
		Coupon coupon = null;
		String sql = "SELECT * FROM coupon WHERE id=?";

		Connection con = null;
		try {
			con = this.pool.getConnection();
			PreparedStatement preparedStatement = con.prepareStatement(sql);

			preparedStatement.setLong(1, coupon_id);

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				// Getting coupon parameters:

				String title = rs.getString(2);
				// Getting and converting SQL date to util Date:
				java.sql.Date startDateInSQL = rs.getDate(3);
				java.util.Date startDate = new java.util.Date(startDateInSQL.getTime());
				// Getting and converting SQL date to util Date:
				java.sql.Date endDateInSQL = rs.getDate(4);
				java.util.Date endDate = new java.util.Date(endDateInSQL.getTime());

				int amount = rs.getInt(5);

				// Getting CouponType in String form and converting to enum:
				String couponTypeInString = rs.getString(6);
				CouponType type = CouponType.fromString(couponTypeInString);

				String message = rs.getString(7);
				double price = rs.getDouble(8);
				String image = rs.getString(9);
				long company_id = rs.getLong(10);

				// Constructing a new Coupon object:
				coupon = new Coupon(coupon_id, title, startDate, endDate, amount, type, message, price, image,
						company_id);
			}

		} catch (Exception e) {
			throw new ApplicationException(e, ErrorType.GENERIC_SYSTEM_ERROR);
		} finally {
			if (con != null) {
				this.pool.returnConnection(con);
			}
		}
		return coupon;
	}

	@Override
	public Collection<Coupon> readAllCoupons() throws ApplicationException {
		Collection<Coupon> coupons = new ArrayList<Coupon>();
		String sql = "SELECT * FROM coupon";

		Connection con = null;
		try {
			con = this.pool.getConnection();
			PreparedStatement preparedStatement = con.prepareStatement(sql);

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				// Getting coupon parameters:

				long couponId = rs.getLong(1);
				String title = rs.getString(2);
				// Getting and converting SQL date to util Date:
				java.sql.Date startDateInSQL = rs.getDate(3);
				java.util.Date startDate = new java.util.Date(startDateInSQL.getTime());

				// Getting and converting SQL date to util Date:
				java.sql.Date endDateInSQL = rs.getDate(4);
				java.util.Date endDate = new java.util.Date(endDateInSQL.getTime());

				int amount = rs.getInt(5);

				// Getting CouponType in String form and converting to enum:
				String couponTypeInString = rs.getString(6);
				CouponType type = CouponType.fromString(couponTypeInString);

				String message = rs.getString(7);
				double price = rs.getDouble(8);
				String image = rs.getString(9);
				long company_id = rs.getLong(10);

				// Constructing a new Coupon object:
				Coupon coupon = new Coupon(couponId, title, startDate, endDate, amount, type, message, price, image,
						company_id);
				// Adding it to the ArrayList:
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
	public Collection<Coupon> readCouponsByType(CouponType type) throws ApplicationException {
		Collection<Coupon> coupons = new ArrayList<Coupon>();
		String sql = "SELECT * FROM coupon WHERE type=?";

		Connection con = null;
		try {
			con = this.pool.getConnection();
			PreparedStatement preparedStatement = con.prepareStatement(sql);

			preparedStatement.setString(1, type.getText());

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				// Getting coupon parameters:

				long couponId = rs.getLong(1);
				String title = rs.getString(2);
				// Getting and converting SQL date to util Date:
				java.sql.Date startDateInSQL = rs.getDate(3);
				java.util.Date startDate = new java.util.Date(startDateInSQL.getTime());

				// Getting and converting SQL date to util Date:
				java.sql.Date endDateInSQL = rs.getDate(4);
				java.util.Date endDate = new java.util.Date(endDateInSQL.getTime());

				int amount = rs.getInt(5);

				// => Skipping CouponType =>

				String message = rs.getString(7);
				double price = rs.getDouble(8);
				String image = rs.getString(9);
				long company_id = rs.getLong(10);

				// Constructing a new Coupon object:
				Coupon coupon = new Coupon(couponId, title, startDate, endDate, amount, type, message, price, image,
						company_id);
				// Adding it to the ArrayList:
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
	public void deleteCouponFromCustCoup(long coupon_id) throws ApplicationException {
		String sql = "DELETE FROM customer_coupon WHERE coupon_id = ?";

		Connection con = null;
		try {
			con = this.pool.getConnection();
			PreparedStatement preparedStatement = con.prepareStatement(sql);

			preparedStatement.setLong(1, coupon_id);

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
	public Coupon readByName(String title) throws ApplicationException {
		Coupon coupon = null;
		String sql = "SELECT * FROM coupon WHERE title=?";

		Connection con = null;
		try {
			con = this.pool.getConnection();
			PreparedStatement preparedStatement = con.prepareStatement(sql);

			preparedStatement.setString(1, title);

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				// Getting coupon parameters:

				long id = rs.getLong(1);
				// Getting and converting SQL date to util Date:
				java.sql.Date startDateInSQL = rs.getDate(3);
				java.util.Date startDate = new java.util.Date(startDateInSQL.getTime());
				// Getting and converting SQL date to util Date:
				java.sql.Date endDateInSQL = rs.getDate(4);
				java.util.Date endDate = new java.util.Date(endDateInSQL.getTime());

				int amount = rs.getInt(5);

				// Getting CouponType in String form and converting to enum:
				String couponTypeInString = rs.getString(6);
				CouponType type = CouponType.fromString(couponTypeInString);

				String message = rs.getString(7);
				double price = rs.getDouble(8);
				String image = rs.getString(9);
				long company_id = rs.getLong(10);

				// Constructing a new Coupon object:
				coupon = new Coupon(id, title, startDate, endDate, amount, type, message, price, image, company_id);
			}

		} catch (Exception e) {
			throw new ApplicationException(e, ErrorType.GENERIC_SYSTEM_ERROR);
		} finally {
			if (con != null) {
				this.pool.returnConnection(con);
			}
		}
		return coupon;
	}

	@Override
	public Collection<Coupon> readCouponsByDate(java.util.Date endDate) throws ApplicationException {
		Collection<Coupon> couponsUnderDate= new ArrayList<Coupon>();
		String sql = "SELECT * FROM coupon WHERE end_date <= ?";
		java.sql.Date endDateInSQLforFilter = new java.sql.Date(endDate.getTime());
		
		Connection con = null;
		try {
			con = this.pool.getConnection();
			PreparedStatement preparedStatement = con.prepareStatement(sql);

			preparedStatement.setDate(1, endDateInSQLforFilter);

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				// Getting coupon parameters:

				long couponId = rs.getLong(1);
				String title = rs.getString(2);
				// Getting and converting SQL date to util Date:
				java.sql.Date startDateInSQL = rs.getDate(3);
				java.util.Date startDate = new java.util.Date(startDateInSQL.getTime());

				// Getting and converting SQL date to util Date:
				java.sql.Date endDateInSQL = rs.getDate(4);
				java.util.Date couponEndDate = new java.util.Date(endDateInSQL.getTime());

				int amount = rs.getInt(5);

				CouponType type= CouponType.fromString(rs.getString(6));

				String message = rs.getString(7);
				double price = rs.getDouble(8);
				String image = rs.getString(9);
				long company_id = rs.getLong(10);

				// Constructing a new Coupon object:
				Coupon coupon = new Coupon(couponId, title, startDate, couponEndDate, amount, type, message, price, image,
						company_id);
				// Adding it to the ArrayList:
				couponsUnderDate.add(coupon);
			}

		} catch (Exception e) {
			throw new ApplicationException(e, ErrorType.GENERIC_SYSTEM_ERROR);
		} finally {
			if (con != null) {
				this.pool.returnConnection(con);
			}
		}
		return couponsUnderDate;
	}

}
