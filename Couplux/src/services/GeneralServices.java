package services;

import java.util.Collection;
import java.util.Date;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


import dao.DB.CouponDAODB;
import elements.Coupon;
import elements.CouponType;
import exceptions.ApplicationException;
import facades.AdminFacade;
import system.mainSystem.CouponSystem;
 /**
  * A Services Class for user's who are not logged in.
  * @author asafs
  *
  */
@Path("General")
public class GeneralServices {

	private CouponSystem couponSystem;
	
	public GeneralServices() throws ApplicationException {
		couponSystem= CouponSystem.getInstance();
	}
	
	/**
	 * A method to return all coupons in the system.
	 * @return Collection of Coupons
	 * @throws ApplicationException
	 */
	@GET
	@Path("Coupons")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Coupon> getAllCoupons() throws ApplicationException {
		return couponSystem.getAllCouponsInSystem();
	}
	
	/**
	 * A method to help get the company name that the coupons belongs to, by passing the company's id.
	 * @param ID
	 * @return
	 * @throws ApplicationException
	 */
	@GET
	@Path("CompanyNameByID/{id}")
	public String getCouponsCompanyNameByID(@PathParam("id") String ID) throws ApplicationException {
		long id = Long.parseLong(ID);
		String compName= new AdminFacade().readCompany(id).getCompName();
		return compName;
	}
	
	/**
	 * A Method to get all coupons of a certain type.
	 * @param typeAsString
	 * @return
	 * @throws ApplicationException
	 */
	/*
	 * Currently the logic behind this method is not correct.
	 * the phase 1 instructions do not include a method that could be reached by all.
	 * the coupons should not be reached through the DAO but through the CouponSystem Methods. 
	 */
	@GET
	@Path("Coupons/Type/{type}")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Coupon> getCouponsOfType( @PathParam("type") String typeAsString) throws ApplicationException{
	//Get Coupon Type:
	CouponType type= CouponType.fromString(typeAsString);
	//Get a collection of all coupons of that type:
	Collection<Coupon> couponsByType= new CouponDAODB().readCouponsByType(type);
	
	return couponsByType;
	}
	
	/**
	 * A Method to get all coupons up to a certain expiration date.
	 * @param typeAsString
	 * @return
	 * @throws ApplicationException
	 */
	/*
	 * Currently the logic behind this method is not correct.
	 * the phase 1 instructions do not include a method that could be reached by all.
	 * the coupons should not be reached through the DAO but through the CouponSystem Methods. 
	 */
	@GET
	@Path("Coupons/Date/{date}")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Coupon> getCouponsByDate(@PathParam("date") String dateAsString) throws ApplicationException{
		//Get Date as String in long form, parse to long:
		long miliDate= Long.parseLong(dateAsString);
		Date date = new Date(miliDate);
		Collection<Coupon> couponsByDate = new CouponDAODB().readCouponsByDate(date);
		return couponsByDate;
		
	}
	
	
}
