package dao.interfaces;

import java.util.Collection;

import elements.Company;
import elements.Coupon;
import exceptions.ApplicationException;

/**
 * A Company DAO for pulling Data about companies from the Database and inserting new data.
 * 
 * @author asafs94
 *
 */
public interface CompanyDAO {
	
	/**Creates a new row in the <b>COMPANY</b> table,
	 * containing all <span style="color:blue;">company</span> details.
	 * 
	 * @param company
	 * @throws ApplicationException
	 */
	public void createCompany(Company company) throws ApplicationException;
	/**Deletes a row in the <b>COMPANY</b> table,
	 * where <span style="color:blue;">company_id</span> is found. 
	 * 
	 * @param company_id
	 * @throws ApplicationException
	 */
	public void deleteCompany(long company_id) throws ApplicationException;
	/**Updates a a row in the <b>COMPANY</b> table,
	 * where <i><span style="color:blue;">company</span>.id</i> is found, to the details of the given <i><span style="color:blue;">company</span></i> object.
	 * @param company
	 * @throws ApplicationException
	 */
	public void updateCompany(Company company) throws ApplicationException;
	/**
	 * Returns a Company object by reading its details from the <b>COMPANY</b> table in the database,
	 * where <i style="color:blue">company_id</i> is found in the <b>COMPANY</b>.id column.
	 * @param company_id
	 * @return Company
	 * @throws ApplicationException
	 */
	public Company readCompany(long company_id) throws ApplicationException;
	/**
	 * Returns a Collection of Company objects containing all companies in the <b>COMPANY</b> table,	 
	 * @return Collection of Company objects
	 * @throws ApplicationException
	 */
	public Collection<Company> readAllCompanies() throws ApplicationException;
	
	/**
	 * Returns a Collection of Coupon objects containing all coupons where <b>COUPON</b>.company_id = <i style="color:blue">company_id</i>.
	 * Meaning, returns all of a certain company's coupons from the <b>COUPON</b> table.
	 * @param company_id
	 * @return Collection of Coupon objects
	 * @throws ApplicationException
	 */
	public Collection<Coupon> readCoupons(long company_id) throws ApplicationException;
	
	/**
	 * A method that checks the <i style="color: blue">name</i> and <i style="color: blue">password</i> received.
	 * If they match a Company's name in the <b>COMPANY</b>.comp_name column and its corresponding <b>COMPANY</b>.password, returns true, else returns false.
	 * @param name
	 * @param password
	 * @return <b>true</b> if login successful or <b>false</b> if not.
	 * @throws ApplicationException
	 */
	public boolean login(String name, String password) throws ApplicationException;
	
	/**
	 * A method that reads a Company from the database by searching its name.
	 * @param name
	 * @return Company
	 * @throws ApplicationException
	 */
	public Company readByName(String name) throws ApplicationException;
	
}