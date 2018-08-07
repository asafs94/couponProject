package system.mainSystem;

import java.util.Collection;

import dao.DB.CompanyDAODB;
import dao.DB.CouponDAODB;
import dao.DB.CustomerDAODB;
import databaseConnections.ConnectionPool;
import elements.Coupon;
import exceptions.ApplicationException;
import exceptions.ErrorType;
import facades.AdminFacade;
import facades.CompanyFacade;
import facades.CouponClientFacade;
import facades.CustomerFacade;
import threads.DailyCouponExpirationTask;

/**
 * The main system. A client logs in through this singleton.
 * 
 * @author asafs94
 *
 */

public class CouponSystem {

	private static CouponSystem couponSystem = null;
	private CustomerDAODB customerDAODB;
	private CompanyDAODB companyDAODB;
	private CouponDAODB couponDAODB;
	private DailyCouponExpirationTask dailyCouponExpirationTask;

	private CouponSystem() throws ApplicationException {
		couponDAODB= new CouponDAODB();
		customerDAODB = new CustomerDAODB();
		companyDAODB = new CompanyDAODB();
		dailyCouponExpirationTask = new DailyCouponExpirationTask();

		Thread dailyTask = new Thread(dailyCouponExpirationTask);
		dailyTask.start();
	}

	/**
	 * A getInstance method for this singletone.
	 * 
	 * @return CouponSystem
	 * @throws ApplicationException
	 */
	public static CouponSystem getInstance() throws ApplicationException {
		if (couponSystem == null) {
			couponSystem = new CouponSystem();
		}
		return couponSystem;
	}

	/**
	 * A log in by client type. A method which receives <b>clientType</b> and
	 * matches a <b>name</b> and <b>password</b>, using that <b>clientType</b> with
	 * the corresponding database table. If a row containing that <b>name</b> and
	 * <b>password</b> was found, a CouponClientFacade of that certain type is
	 * returned.
	 * 
	 * @param name
	 * @param password
	 * @param clientType
	 * @return CouponClientFacade
	 * @throws ApplicationException
	 */
	public CouponClientFacade login(String name, String password, ClientType clientType) throws ApplicationException {
		switch (clientType) {
		case ADMIN: {
			if (name.equals("ADMIN") && password.equals("1234")) {
				return new AdminFacade();
			} else {
				throw new ApplicationException(ErrorType.LOGIN_UNSUCCESSFULL);
			}
		}
		case COMPANY: {
			boolean companyLoginSuccessfull = companyDAODB.login(name, password);
			if (companyLoginSuccessfull) {
				// get Company Id:
				long company_id = companyDAODB.readByName(name).getId();
				return new CompanyFacade(company_id);
			} else {
				throw new ApplicationException(ErrorType.LOGIN_UNSUCCESSFULL);
			}
		}
		case CUSTOMER: {
			boolean customerLoginSuccessfull = customerDAODB.login(name, password);
			if (customerLoginSuccessfull) {
				// get Customer Id:
				long customer_id = customerDAODB.readByName(name).getId();
				return new CustomerFacade(customer_id);
			} else {
				throw new ApplicationException(ErrorType.LOGIN_UNSUCCESSFULL);
			}
		}
		default:
			throw new ApplicationException(ErrorType.LOGIN_UNSUCCESSFULL);
		}
	}

	/**
	 * An Addition, to show all visitors all the coupons in the system.
	 * @return Collection[Coupon]
	 * @throws ApplicationException
	 */
	public Collection<Coupon> getAllCouponsInSystem() throws ApplicationException {
		return couponDAODB.readAllCoupons();
	}
	
	
	
	/**
	 * A Shutdown method for the coupon system, will running tasks, and close all
	 * connections once they are all returned, and return CouponSystem to null
	 * value, until called again.
	 * 
	 * @throws ApplicationException
	 */
	public void shutDown() throws ApplicationException {
		dailyCouponExpirationTask.stopTask();

		try {
			ConnectionPool pool = ConnectionPool.getInstance();
			pool.closeAllConnections();
		} catch (Exception e) {
			throw new ApplicationException(ErrorType.GENERIC_SYSTEM_ERROR);
		}
		CouponSystem.couponSystem=null;
		
	}
	
	

}
