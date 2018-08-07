package threads;

import java.util.Collection;
import java.util.Date;

import dao.DB.CouponDAODB;
import elements.Coupon;
import exceptions.ApplicationException;
/**
 * A thread that runs each 24 hours, and deletes expired or out of stock coupons.
 * @author asafs94
 *
 */

public class DailyCouponExpirationTask implements Runnable {

	private CouponDAODB couponDAODB;
	private boolean quit;

	public DailyCouponExpirationTask() throws ApplicationException {
		this.couponDAODB = new CouponDAODB();
		this.quit = false;

	}
	@Override
	public void run() {

		this.quit= false;
		while (!quit) {
			try {
				Collection<Coupon> expiredCoupons = readExpiredCoupons();
				deleteExpiredCoupons(expiredCoupons);
				Thread.sleep(86400000);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void stopTask() {
		this.quit = true;

	}
	/**
	 * Deleted all coupons in the received Collection from the database.
	 * @param expiredCoupons
	 * @throws ApplicationException
	 */
	private void deleteExpiredCoupons(Collection<Coupon> expiredCoupons) throws ApplicationException {
		for (Coupon coupon : expiredCoupons) {
			couponDAODB.deleteCoupon(coupon.getId());
			//for Test purposes:
			/*System.out.println("\n----------------------Message From dailyCouponExpirationTask:  ");
			System.out.println(new Date()+"-Coupon Expired: deleted coupon - "+coupon.getTitle());
			System.out.println("---------------------------------------------------------------");*/
			//--------
			couponDAODB.deleteCouponFromCustCoup(coupon.getId());
		}
	}

	/**
	 * Reads and returns all expired coupons from the database.
	 * @return Collection of Coupon objects.
	 * @throws ApplicationException
	 */
	private Collection<Coupon> readExpiredCoupons() throws ApplicationException {
		Collection<Coupon> expiredCoupons = couponDAODB.readCouponsByDate(new Date());
		return expiredCoupons;

	}

}
