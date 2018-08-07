package facades;

import java.util.Collection;
import java.util.Date;

import dao.DB.CompanyDAODB;
import dao.DB.CouponDAODB;
import dao.DB.CustomerDAODB;
import elements.Company;
import elements.Coupon;
import elements.Customer;
import exceptions.ApplicationException;
import exceptions.ErrorType;

/**
 * A utility Entity to help with validation checks.
 * 
 * @author asafs94
 *
 */
public class CheckerUtil {

	private CompanyDAODB companyDAODB;
	private CustomerDAODB customerDAODB;
	private CouponDAODB couponDAODB;

	/**
	 * A CTR for this class.
	 * 
	 * @throws ApplicationException
	 */
	public CheckerUtil() throws ApplicationException {
		this.companyDAODB = new CompanyDAODB();
		this.customerDAODB = new CustomerDAODB();
		this.couponDAODB = new CouponDAODB();

	}

	/**
	 * Checks If this object's name exist in the database.
	 * 
	 * @param object
	 * @return <b style="color:purple">boolean</b> <b>true</b> if name exists,
	 *         <b>false</b> if not.
	 * @throws ApplicationException
	 */
	public boolean checkIfExistsByName(Object object) throws ApplicationException {
		boolean exists = false;
		// Checks which class this object belongs to:

		if (object instanceof Company) {
			Company company = (Company) object;
			// Reads the company by the name given from the database:
			Company companyFromDatabase = companyDAODB.readByName(company.getCompName());
			/*
			 * If the company was read by name successfully and it exists by this name -
			 * then companyFromDatabase's value will not be null, therefore it exists by
			 * name. Else: value will be null and a company by this name does not exist.
			 */
			exists = companyFromDatabase != null ? true : false;
		} else if (object instanceof Customer) {
			// Same logic applies for this as the company's check:
			Customer customer = (Customer) object;
			Customer customerFromDatabase = customerDAODB.readByName(customer.getCustName());
			exists = customerFromDatabase != null ? true : false;
		} else if (object instanceof Coupon) {
			// Same logic applies for this as the company's check:
			Coupon coupon = (Coupon) object;
			Coupon couponFromDatabase = couponDAODB.readByName(coupon.getTitle());
			exists = couponFromDatabase != null ? true : false;
		} else {
			throw new ApplicationException(ErrorType.WRONG_TYPE_ENTERED_INTO_CHECKER);
		}

		return exists;
	}

	/**
	 * A method to check the validity of a name of an
	 * <a style="color:blue">object</a> before an update is conducted with that
	 * object. If the name has been changed and is not the same, then it is not
	 * valid.
	 * 
	 * @param object
	 * @return <b style="color:purple">true</b> if name is valid,
	 *         <b style="color:purple">false</b> if not.
	 * @throws ApplicationException
	 */
	public boolean PreUpdateNameValidityCheck(Object object) throws ApplicationException {

		String objectName = null;
		String objectNameInDatabase = null;
		boolean nameValidity = false;

		// Checks which type of object was received:
		if (object instanceof Company) {
			Company company = (Company) object;
			// The name of the company received:
			objectName = company.getCompName();
			// The name of the company with the same id in the database:
			objectNameInDatabase = companyDAODB.readCompany(company.getId()).getCompName();
		} else if (object instanceof Customer) {
			// Same logic applies here as in the company's check
			Customer customer = (Customer) object;
			objectName = customer.getCustName();
			objectNameInDatabase = customerDAODB.readCustomer(customer.getId()).getCustName();
		} else if (object instanceof Coupon) {
			// Same logic applies here as in the company's check
			Coupon coupon = (Coupon) object;
			objectName = coupon.getTitle();
			objectNameInDatabase = couponDAODB.readCoupon(coupon.getId()).getTitle();
		} else {
			throw new ApplicationException(ErrorType.WRONG_TYPE_ENTERED_INTO_CHECKER);
		}

		// If the name is the same name (equal), then nameValidity will be true.
		// meaning, check was successful.
		nameValidity = objectName.equals(objectNameInDatabase);
		return nameValidity;
	}

	/**
	 * A method to check if the customer already has that coupon before purchase. A
	 * customer cannot have more than one of the same coupon.
	 * 
	 * @param customer_id
	 * @param coupon_id
	 * @return <b style="color:purple">true</b> if customer has coupon,
	 *         <b style="color:purple">false</b> if not.
	 * @throws ApplicationException
	 */
	public boolean checkIfCustomerHasCoupon(long customer_id, long coupon_id) throws ApplicationException {
		Collection<Coupon> customerCoupons = customerDAODB.readCoupons(customer_id);
		// creating a temporary coupon object with the coupon id for comparison:
		// [Coupon objects are compared by id by definition (via equals method)]
		Coupon temp = new Coupon(coupon_id, null, null, null, 0, null, null, 0, null, 0);
		return customerCoupons.contains(temp); // If it contains the coupon with coupon_id, then the customer already
												// has the coupon, and will return true.
	}

	/**
	 * A method that checks if a coupon is purchasable, meaning:<br>
	 * 1. If it is up to date and hasn't expired yet.<br>
	 * 2. If it is in stock.
	 * 
	 * @param coupon_id
	 * @return If Coupon is Purchasable, returns <b style="color:purple">true</b>,
	 *         else <b style="color:purple">false</b>.
	 * @throws ApplicationException
	 */
	public boolean checkIfCouponPurchasable(long coupon_id) throws ApplicationException {
		Coupon coupon = couponDAODB.readCoupon(coupon_id);
		// check Coupon Expiration:
		Date currentDate = new Date();
		Date couponExpirationDate = coupon.getEndDate();
		// if current date is before couponEndDate, then the coupon is still valid.
		boolean couponValid = currentDate.before(couponExpirationDate);

		// check if coupon exists in stock:
		boolean couponInStock = coupon.getAmount() >= 1;

		// Purchase-ability:
		boolean couponPurchaseable = couponValid && couponInStock;

		return couponPurchaseable;

	}



}
