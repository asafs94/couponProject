package dao.interfaces;

import java.util.Collection;

import elements.Coupon;
import elements.Customer;
import exceptions.ApplicationException;

/**
 * A Customer DAO for reading data about customers from the database and inserting new data.
 * @author asafs
 *
 */
public interface CustomerDAO {

	/**
	 * Creates a row in the <b>CUSTOMER</b> table, containing the data of <i style="color: blue">customer</i>.
	 * @param customer
	 * @throws ApplicationException
	 */
	public void createCustomer(Customer customer) throws ApplicationException;
	/**
	 * Deletes a row in the <b>CUSTOMER</b> table, where <b>CUSTOMER</b>.id=<i style="color: blue">customer_id</i>.
	 * @param customer_id
	 * @throws ApplicationException
	 */
	public void deleteCustomer(long customer_id) throws ApplicationException;
	/**
	 * Updates a row in the <b>CUSTOMER</b> table, where <b>CUSTOMER</b>.id= <i style="color: blue">customer</i>.id, to <i style="color: blue">customer</i>'s data.
	 * @param customer
	 * @throws ApplicationException
	 */
	public void updateCustomer(Customer customer) throws ApplicationException;
	
	/**
	 * Returns a Customer object containing all data from <b>CUSTOMER</b> table where <b>CUSTOMER</b>.id=<i style="color: blue">customer_id</i>.
	 * @param customer_id
	 * @return Customer
	 * @throws ApplicationException
	 */
	public Customer readCustomer(long customer_id) throws ApplicationException;
	/**
	 * Returns a Collection of Customer objects, containing all data from <b>CUSTOMER</b> table.
	 * @return
	 * @throws ApplicationException
	 */
	public Collection<Customer> readAllCustomers() throws ApplicationException;
	
	/**
	 * Returns a Collection of Coupon objects, containing all data from <b>COUPON</b> table, joined by <b>CUSTOMER_COUPON</b> table,
	 * where <b>CUSTOMER_COUPON</b>.cust_id=<i style="color: blue">customer_id</i>.
	 * Meaning, returns all of one customer's coupons.
	 * @param customer_id
	 * @return Collection of Coupons
	 * @throws ApplicationException
	 */
	public Collection<Coupon> readCoupons(long customer_id) throws ApplicationException;
	
	/**
	 A method that checks the <i style="color: blue">name</i> and <i style="color: blue">password</i> received.
	 * If they match a Customer's name in the <b>CUSTOMER</b>.cust_name column and its corresponding <b>CUSTOMER</b>.password, returns true, else returns false.
	 * @param name
	 * @param password
	 * @return <b>true</b> if login successful and <b>false</b> if not.
	 * @throws ApplicationException
	 */
	public boolean login(String name, String password) throws ApplicationException;
	
	//<join table methods>
	/**
	 * Creates a new row in the <b>CUSTOMER_COUPON</b> join table, containing <i style="color: blue">customer_id</i> and <i style="color: blue">coupon_id</i>.
	 * Should be used when a Customer purchases a Coupon.
	 * @param customer_id
	 * @param coupon_id
	 * @throws ApplicationException
	 */
	public void createCustomerCoupon(long customer_id, long coupon_id) throws ApplicationException;
	
	/**
	 * Deletes all <b>CUSTOMER_COUPON</b> rows where <b>CUSTOMER_COUPON</b>.cust_id= <i style="color: blue">customer_id</i>.
	 * Should be used when a client is deleted from the system and all of his coupons need to be deleted as well. 
	 * @param customer_id
	 * @throws ApplicationException
	 */
	public void deleteCustomerCoupons(long customer_id) throws ApplicationException;
	//</join table methods>
	
	/**
	 * A method which reads a Coupon by its name from the database.
	 * @param name
	 * @return Coupon
	 * @throws ApplicationException
	 */
	public Customer readByName(String name) throws ApplicationException;
	
}
