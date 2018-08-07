package facades;

import java.util.ArrayList;
import java.util.Collection;
import dao.DB.CouponDAODB;
import dao.DB.CustomerDAODB;
import elements.Coupon;
import elements.CouponType;
import exceptions.ApplicationException;
import exceptions.ErrorType;
/**
 * A facade which is returned when a customer logs in.
 * @author asafs94
 *
 */
public class CustomerFacade implements CouponClientFacade {

	private CheckerUtil checkerUtil;
	private CustomerDAODB customerDAODB;
	private CouponDAODB couponDAODB;
	/** The id of the customer who logged in and received this facade.*/
	private long customer_id;
	
	public CustomerFacade(long customer_id) throws ApplicationException {
	this.checkerUtil= new CheckerUtil();
	this.customer_id= customer_id;
	this.customerDAODB= new CustomerDAODB();
	this.couponDAODB= new CouponDAODB();
	}
	/**
	 * A method to be run when a customer makes a purchase.
	 * Checks if the customer doesn't already have the coupon, and if the coupon is purchasable.
	 * <br>If both are true, adds the coupon to the client's coupons in the database. 
	 * @param coupon_id
	 * @throws ApplicationException
	 */
	public void purchaseCoupon(long coupon_id) throws ApplicationException {
		//Running a pre-Purchase check:
		boolean hasCoupon= checkerUtil.checkIfCustomerHasCoupon(customer_id, coupon_id);
		boolean couponPurchaseable= checkerUtil.checkIfCouponPurchasable(coupon_id);
		if(hasCoupon) {
			throw new ApplicationException(ErrorType.CUSTOMER_ALREADY_HAS_COUPON);
		}else if(!couponPurchaseable){
			throw new ApplicationException(ErrorType.COUPON_EXPIRED_OR_OUT_OF_STOCK);
		}else {
			//IF ALL CHECKS PASSED- CREATES A NEW COUPON OF THAT ID FOR THE CUSTOMER:
			customerDAODB.createCustomerCoupon(customer_id, coupon_id);
			
			//reducing amount of coupons by one:
			Coupon coupon= couponDAODB.readCoupon(coupon_id);
			coupon.setAmount(coupon.getAmount()-1);
			couponDAODB.updateCoupon(coupon);
		}
	}
	/**
	 * Returns all of the customer's coupons.
	 * @return
	 * @throws ApplicationException
	 */
	public Collection<Coupon> readAllPurchasedCoupons() throws ApplicationException{
		return customerDAODB.readCoupons(customer_id);
	}
	
	/**
	 * Reads all coupons of a certain <b>type</b> that are owned by this customer.
	 * @param type
	 * @return Collection of Coupon objects.
	 * @throws ApplicationException
	 */
	public Collection<Coupon> readCouponsByType(CouponType type) throws ApplicationException{
		Collection<Coupon> allCustomerCoupons= customerDAODB.readCoupons(customer_id);
		Collection<Coupon> couponsByType= new ArrayList<Coupon>();
		
		for (Coupon coupon : allCustomerCoupons) {
			if(coupon.getType().equals(type)) {
				couponsByType.add(coupon);
			}else continue;
		}
		
		return couponsByType;
		
	}
	
	/**
	 * Reads all coupons below a certain <b>price</b> that this customer owns. 
	 * @param price
	 * @return Collection of Coupon objects.
	 * @throws ApplicationException
	 */
	public Collection<Coupon> readCouponsByPrice(double price) throws ApplicationException{
		Collection<Coupon> allCustomerCoupons= customerDAODB.readCoupons(customer_id);
		Collection<Coupon> couponsByPrice= new ArrayList<Coupon>();
		
		for (Coupon coupon : allCustomerCoupons) {
			if(coupon.getPrice()<=price) {
				couponsByPrice.add(coupon);
			}else continue;
		}
		return couponsByPrice;
	}
	public long getCustomer_id() {
		return customer_id;
	}
	
	
	
	
}
