package dao.interfaces;

import java.util.Collection;
import java.util.Date;

import elements.Coupon;
import elements.CouponType;
import exceptions.ApplicationException;

/**
 * A Coupon DAO for pulling out Data about coupons and inserting new one into the database.
 * @author asafs94
 *
 */
public interface CouponDAO {
	/**
	 * Creates a new row in the <b>COUPON</b> table, containing all <i style="color: blue">coupon</i>'s data.
	 * @param coupon
	 * @throws ApplicationException
	 */
	public void createCoupon(Coupon coupon) throws ApplicationException;
	
	/**
	 * Delete's a row in the <b>COUPON</b> table, where <b>COUPON</b>.id = <i style="color: blue">coupon_id</i>. 
	 * @param coupon_id
	 * @throws ApplicationException
	 */
	public void deleteCoupon(long coupon_id) throws ApplicationException;
	
	/**
	 * Updates all data in the <b>COUPON</b> table where <b>COUPON</b>.id=<i style="color: blue">coupon</i>.id,
	 * to <i style="color: blue">coupon</i>'s data.
	 * @param coupon
	 * @throws ApplicationException
	 */
	public void updateCoupon(Coupon coupon) throws ApplicationException;
	
	/**
	 * Returns a Coupon object containing all data in the <b>COUPON</b> table, where <b>COUPON</b>.id=<i style="color: blue">coupon_id</i>.
	 * @param coupon_id
	 * @return Coupon
	 * @throws ApplicationException
	 */
	public Coupon readCoupon(long coupon_id) throws ApplicationException;
	
	/**
	 * Returns a Collection of Coupon objects from the <b>COUPON</b> table containing all coupons.
	 * @return Collection of Coupons
	 * @throws ApplicationException
	 */
	public Collection<Coupon> readAllCoupons() throws ApplicationException;
	
	/**
	 * Returns a Collection of Coupon objects from the <b>COUPON</b> table containing all coupons where <b>COUPON</b>.type=<i style="color: blue">type</i>.
	 * @param type
	 * @return Collection of Coupons
	 * @throws ApplicationException
	 */
	public Collection<Coupon> readCouponsByType(CouponType type) throws ApplicationException;
	
	//<Additions> (For JoinTable Customer_Coupon):
	/**
	 * Deletes one row in the <b>CUSTOMER_COUPON</b> join table where <b>CUSTOMER_COUPON</b>.coupon_id=<i style="color: blue">coupon_id</i>.
	 * Should be used when a coupon is deleted from the system, to delete it from all customers' accounts.
	 * @param coupon_id
	 * @throws ApplicationException
	 */
	public void deleteCouponFromCustCoup(long coupon_id) throws ApplicationException;
	
	/**
	 * A method which reads a Coupon object from the database by searching for its name, i.e title.
	 * @param title
	 * @return
	 * @throws ApplicationException
	 */
	public Coupon readByName(String title) throws ApplicationException;
	
	/**
	 * A method to Read all coupons that their End_Date is equal to or below a certain <b>date</b>.
	 * @param date
	 * @return Collection of Coupons
	 * @throws ApplicationException
	 */
	public Collection<Coupon> readCouponsByDate(Date date) throws ApplicationException;
	
	//</Additions>


}
