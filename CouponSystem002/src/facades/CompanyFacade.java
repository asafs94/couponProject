package facades;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import dao.DB.CompanyDAODB;
import dao.DB.CouponDAODB;
import elements.Coupon;
import elements.CouponType;
import exceptions.ApplicationException;
import exceptions.ErrorType;
/**
 * A facade which is received when a company logs in.
 * @author asafs94
 *
 */
public class CompanyFacade implements CouponClientFacade {

	private CheckerUtil checkerUtil;
	private CompanyDAODB companyDAODB;
	private CouponDAODB couponDAODB;
	/**
	 * The id of the company that uses this facade.
	 */
	private long company_id;
	
	public CompanyFacade(long company_id) throws ApplicationException {
		this.checkerUtil= new CheckerUtil();
		this.company_id=company_id;
		this.companyDAODB= new CompanyDAODB();
		this.couponDAODB= new CouponDAODB();
	}
	
	/**
	 * Creates a new Coupon in the database for the company.
	 * A coupon cannot be created if a coupon by that name already exists.
	 * @param coupon
	 * @throws ApplicationException
	 */
	public void createCoupon(Coupon coupon) throws ApplicationException {
		// Check if a coupon exists by the same title(name) already:
		boolean exists= checkerUtil.checkIfExistsByName(coupon);
		if(exists) {
			throw new ApplicationException(ErrorType.COUPON_EXISTS_BY_NAME);
		}else {
		couponDAODB.createCoupon(coupon);
		}
	}
	
	/**
	 * Deletes A Coupon from the database. Both from all clients records and from the coupon list.
	 * @param coupon_id
	 * @throws ApplicationException
	 */
	public void deleteCoupon(long coupon_id) throws ApplicationException {
		//When deleting a coupon, it also needs to be deleted from the customers:
		couponDAODB.deleteCouponFromCustCoup(coupon_id);
		//deleting coupon from the database:
		couponDAODB.deleteCoupon(coupon_id);
	}
	
	/**
	 * Updates a coupon. Changes the data of the coupon on the database, to the coupon received in the method.
	 * A coupon cannot be updated if its name has been changed.
	 * @param coupon
	 * @throws ApplicationException
	 */
	public void updateCoupon(Coupon coupon) throws ApplicationException{
		//Checking name validity before update:
		boolean nameValid=checkerUtil.PreUpdateNameValidityCheck(coupon);
		//Perform update if the name is valid, else: throw an exception:
		if(nameValid) {
			couponDAODB.updateCoupon(coupon);
		}else {
			throw new ApplicationException(ErrorType.COUPON_NAME_DOESNT_MATCH);
		}
	}
	
	/**
	 * Reads a coupon object from the database by using an id number (<b>coupon_id</b>).
	 * @param coupon_id
	 * @return Coupon
	 * @throws ApplicationException
	 */
	public Coupon readCoupon(long coupon_id) throws ApplicationException {
		return couponDAODB.readCoupon(coupon_id);
	}
	
	/**
	 * Reads all coupons of this company from the Database.
	 * @return Collection of Coupon objects.
	 * @throws ApplicationException
	 */
	public Collection<Coupon> readAllCoupons() throws ApplicationException{
		return companyDAODB.readCoupons(company_id);
	}
	
	/**
	 * Reads all coupons of this company of a certain <b>type</b> from the database.
	 * @param type
	 * @return Collection of Coupon objects.
	 * @throws ApplicationException
	 */
	public Collection<Coupon> readCouponsByType(CouponType type) throws ApplicationException{
		Collection<Coupon> AllCouponsOfType= couponDAODB.readCouponsByType(type);
		//sorting to only this company's coupons:
		Collection<Coupon> couponsByType= new ArrayList<Coupon>();
		for (Coupon coupon : AllCouponsOfType) {
			boolean couponOfThisCompany = coupon.getCompany_id()==this.company_id;
			if(couponOfThisCompany) {
				couponsByType.add(coupon);
			}else continue;
		}
		
		return couponsByType;
	}
	/**
	 * Returns a collection of coupons of this company below a ceratin price.
	 * @param price
	 * @return Collection of coupons
	 * @throws ApplicationException
	 */
	public Collection<Coupon> readCouponsByPrice(double price) throws ApplicationException{
		Collection<Coupon> companyCoupons= companyDAODB.readCoupons(this.company_id);
		Collection<Coupon> companyCouponsFiltered= new ArrayList<Coupon>();
		for (Coupon coupon : companyCoupons) {
			if(coupon.getPrice()<=price) {
				companyCouponsFiltered.add(coupon);
			}else continue;
		}return companyCouponsFiltered;
	}
	
	/**
	 * Returns a Collection of this company's coupons that their expiration date is not past the specified date.
	 * @param date
	 * @return Collection of Company
	 * @throws ApplicationException
	 */
	public Collection<Coupon> readCouponsByDate(Date date) throws ApplicationException{
		Collection<Coupon> companyCoupons= companyDAODB.readCoupons(this.company_id);
		Collection<Coupon> companyCouponsFiltered= new ArrayList<Coupon>();
		for (Coupon coupon : companyCoupons) {
			if(coupon.getEndDate().before(date)) {
				companyCouponsFiltered.add(coupon);
			}else continue;
		}return companyCouponsFiltered;
	}
	
	public long getCompany_id() {
		return company_id;
	}
	
	/**
	 * Read coupon by name Method.
	 * @param name
	 * @return
	 * @throws ApplicationException 
	 */
	public Coupon readCouponByTitle(String name) throws ApplicationException {
		Coupon coupon=couponDAODB.readByName(name);
		return coupon;
	}

	
}
