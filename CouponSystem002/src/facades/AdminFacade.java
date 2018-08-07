package facades;

import java.util.Collection;
import dao.DB.CompanyDAODB;
import dao.DB.CouponDAODB;
import dao.DB.CustomerDAODB;
import elements.Company;
import elements.Coupon;
import elements.Customer;
import exceptions.ApplicationException;
import exceptions.ErrorType;
/**
 * A Facade which is returned when an Admin logs in.
 * @author asafs94
 *
 */
public class AdminFacade implements CouponClientFacade {
	
	private CheckerUtil checkerUtil;
	private CompanyDAODB companyDAODB;
	private CustomerDAODB customerDAODB;
	private CouponDAODB couponDAODB;
	
	/**
	 *  A CTR for creation of a new Admin FACADE.
	 * @throws ApplicationException
	 */
	public AdminFacade() throws ApplicationException {
		this.checkerUtil= new CheckerUtil();
		this.companyDAODB= new CompanyDAODB();
		this.customerDAODB= new CustomerDAODB();
		this.couponDAODB= new CouponDAODB();
	}
	/**
	 * A method to create a new Company in the system.
	 * This method performs a safety check to make sure no company exists by the new company's name before creation.
	 * If a company exists by that name, throws an Application Excpetion.
	 * @param company
	 * @throws ApplicationException
	 */
	public void createCompany(Company company) throws ApplicationException{
		//Check if the company exists by name:
		boolean exists= checkerUtil.checkIfExistsByName(company);
		//If it exists, throws an Exception with ErrorType company_exists.
		if(exists) {
			throw new ApplicationException(ErrorType.COMPANY_EXISTS_BY_NAME);
		}else {
			//If a company by that name doesn't exist, creates a new company.
			companyDAODB.createCompany(company);
		}
	}
	
	/**
	 * A method for deleting a Company from the system and all of its coupons.
	 * @param company_id
	 * @throws ApplicationException
	 */
	public void deleteCompany(long company_id) throws ApplicationException{
		//Reading company coupons:
		Collection<Coupon> coupons= companyDAODB.readCoupons(company_id);
		
		//Deleting coupons from Coupon table and from customers:
		for (Coupon coupon : coupons) {
			couponDAODB.deleteCoupon(coupon.getId());
			couponDAODB.deleteCouponFromCustCoup(coupon.getId());
		}
		
		//Deleting company from database:
		companyDAODB.deleteCompany(company_id);
	}
	
	/**
	 * A method which receives a company object. and replaces its details with the company details of the company in the database with the same id.
	 * This method makes sure that the company name has not been changed before updating details. Company name cannot be changed.
	 * @param company
	 * @throws ApplicationException
	 */
	public void updateCompany(Company company) throws ApplicationException{
		//Performing a pre-update name validity check (checks if the name has not been changed.)
		boolean nameValid = checkerUtil.PreUpdateNameValidityCheck(company);
		//If it is the same name, conduct an update
		if(nameValid) {
			this.companyDAODB.updateCompany(company);
		}else {
			throw new ApplicationException(ErrorType.COMPANY_NAME_DOESNT_MATCH);
		}
	}
	
	/**
	 * Returns a Collection of all companies in the Database.
	 * @return Collection of Company
	 * @throws ApplicationException
	 */
	public Collection<Company> readAllCompanies() throws ApplicationException{
		Collection<Company> companies= companyDAODB.readAllCompanies();
		return companies;
	}
	
	/**
	 * Returns a Company object with the corresponding id (<a style="color:brown">company_id</a>).
	 * @param company_id
	 * @return
	 * @throws ApplicationException
	 */
	public Company readCompany(long company_id) throws ApplicationException {
		Company company= companyDAODB.readCompany(company_id);
		if(company!=null) {
		return company;
		}
		else throw new ApplicationException(ErrorType.COMPANY_DOESNT_EXIST);
	}
	
	/**
	 * A method to create a new Customer in the database.
	 * This method makes sure that no Customer exists by the same name before creation.
	 * @param customer
	 * @throws ApplicationException
	 */
	public void createCustomer(Customer customer) throws ApplicationException{
		//Checking if the client exists by name:
		boolean exists= checkerUtil.checkIfExistsByName(customer);
		if(exists) {
			throw new ApplicationException(ErrorType.CUSTOMER_EXISTS_BY_NAME);
		}else { //If a customer doesnt exist by that name already, create a new customer:
			customerDAODB.createCustomer(customer);
		}
	}
	
	/**
	 * Deletes a Customer from the Database and all of its Coupons.
	 * @param customer_id
	 * @throws ApplicationException
	 */
	public void deleteCustomer(long customer_id) throws ApplicationException{
		//Delete all Customer coupons:
		customerDAODB.deleteCustomerCoupons(customer_id);
		//Delete customer from the database:
		customerDAODB.deleteCustomer(customer_id);
	}
	
	/**
	 * Updates a Customer's details except name where customer id is found.
	 * This method makes sure that customer's name has not been changed before update.
	 * @param customer
	 * @throws ApplicationException
	 */
	public void updateCustomer(Customer customer) throws ApplicationException{
		//Checking validity of the name before update (if the name has not been changed):
		boolean nameValid=checkerUtil.PreUpdateNameValidityCheck(customer);
		//If its the same name- conduct update, else, throw an exception.
		if(nameValid) {
			customerDAODB.updateCustomer(customer);
		}else {
			throw new ApplicationException(ErrorType.CUSTOMER_NAME_DOESNT_MATCH);
		}
	}
	
	/**
	 * Returns a Collection of all customers in the database.
	 * @return Collection of Customers
	 * @throws ApplicationException
	 */
	public Collection<Customer> readAllCustomers() throws ApplicationException{
		return customerDAODB.readAllCustomers();
	}
	
	/**
	 * Returns a Customer object from the database with corresponding id.
	 * @param customer_id
	 * @return
	 * @throws ApplicationException
	 */
	public Customer readCustomer(long customer_id) throws ApplicationException{
		Customer customer= customerDAODB.readCustomer(customer_id);
		if(customer!= null) {
		return customer;
		}else {
			throw new ApplicationException(ErrorType.CUSTOMER_DOESNT_EXIST);
		}
	}
}
