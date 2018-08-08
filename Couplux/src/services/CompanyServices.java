package services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;


import elements.Coupon;
import elements.CouponType;
import exceptions.ApplicationException;
import exceptions.ErrorType;
import facades.CompanyFacade;
import tools.Message;
import tools.MessageType;
/**
 * A Services class for a Company type user.
 * @author asafs
 *
 */
@Path("Private/Company")
public class CompanyServices {
	
	CompanyFacade companyFacade;
	
	public CompanyServices(@Context HttpServletRequest request) throws ApplicationException {
		HttpSession session= request.getSession(false);
		companyFacade= (CompanyFacade) session.getAttribute("facade");
	}
	
	/**
	 * A Method to create a new Coupon in the database.
	 * @param coupon
	 * @return {@link tools.Message Message}
	 * @throws ApplicationException
	 */
	@Path("Coupons")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Message createCoupon(Coupon coupon) throws ApplicationException {
		coupon.setId(0);
		coupon.setCompany_id(companyFacade.getCompany_id());
		companyFacade.createCoupon(coupon);
		long millisUntilExpired = coupon.getEndDate().getTime()-System.currentTimeMillis();
		long daysUntilExpired= (long) (0.0000000115741*millisUntilExpired);
		return new Message("Success", "Coupon Created succesfully and will expire in "+daysUntilExpired+"days",MessageType.SUCCESS);
	}

	/**
	 * A Method to update an existing coupon.
	 * @param coupon
	 * @return {@link tools.Message Message}
	 * @throws ApplicationException
	 */
	@Path("Coupons")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public Message updateCoupon(Coupon coupon) throws ApplicationException {

		companyFacade.updateCoupon(coupon);
		return new Message("Coupon Updated","Coupon " + coupon.getId() + " updated.", MessageType.SUCCESS);
	}

	/**
	 * A Method to delete an existing coupon.
	 * @param coupon
	 * @return {@link tools.Message Message}
	 * @throws ApplicationException
	 */
	@Path("Coupons/{coupon.id}")
	@DELETE
	public Message deleteCoupon(@PathParam("coupon.id") String ID) throws ApplicationException {
		long id = Long.parseLong(ID);
		companyFacade.deleteCoupon(id);

		return new Message("Coupon Deleted","Coupon NO." + id + " deleted from the system.",MessageType.SUCCESS);
	}

	/**
	 * A Method to read a single coupon from the database.
	 * @param coupon
	 * @return {@link elements.Coupon Coupon}
	 * @throws ApplicationException
	 */
	/*
	 * I did not find a use for the method on the client side.
	 * Therefore, I did not use it.
	 * I still wrote it to match the project's requirements.
	 * 	 */
	@Path("Coupons/{coupon.id}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Coupon readCoupon(@PathParam("coupon.id") String ID) throws ApplicationException {
		long id = Long.parseLong(ID);
		Coupon coupon = companyFacade.readCoupon(id);
		return coupon;
	}

	/**
	 * A Method to read all coupons from the database of this company.
	 * @return {@link java.util.Collection Collection} of {@link elements.Coupon Coupon}s
	 * @throws ApplicationException
	 */
	@Path("Coupons")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Coupon> readCoupons() throws ApplicationException {
		Collection<Coupon> coupons = companyFacade.readAllCoupons();
		return coupons;
	}
	
	/**
	 * A Method to read coupons from the database of this company, filtered by {@link elements.CouponType type}.
	 * @return {@link java.util.Collection Collection} of {@link elements.Coupon Coupon}s
	 * @throws ApplicationException
	 */
	@Path("Coupons/Type/{type}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Coupon> readCouponsByType(@PathParam("type") String typeInStringForm) throws ApplicationException {
		Collection<Coupon> coupons = companyFacade.readCouponsByType(CouponType.fromString(typeInStringForm));
		return coupons;
	}
	
	/**
	 * A Method to read coupons from the database of this company, filtered by Highest possible price.
	 * @return {@link java.util.Collection Collection} of {@link elements.Coupon Coupon}s
	 * @throws ApplicationException
	 */
	@Path("Coupons/Price")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Coupon> readCouponsByPrice(@QueryParam("price") String priceInStringForm) throws ApplicationException {
		double price= Double.parseDouble(priceInStringForm);
		Collection<Coupon> coupons= companyFacade.readCouponsByPrice(price);
		return coupons;
	}
	
	/**
	 * A Method to read coupons from the database of this company, filtered by last endDate.
	 * @return {@link java.util.Collection Collection} of {@link elements.Coupon Coupon}s
	 * @throws ApplicationException
	 */
	@Path("Coupons/Date")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Coupon> readCouponsByDate(@QueryParam("endDate") String dateInStringForm) throws ApplicationException {
		SimpleDateFormat sdf= new SimpleDateFormat("dd-M-yyyy");
		Date endDate;
		try {
			endDate = sdf.parse(dateInStringForm);
		} catch (ParseException e) {
			throw new ApplicationException(e, ErrorType.GENERIC_SYSTEM_ERROR);
		}
		Collection<Coupon> coupons= companyFacade.readCouponsByDate(endDate);
		return coupons;
	}
	

	
}
