package services;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;


import elements.Coupon;
import elements.CouponType;
import exceptions.ApplicationException;
import facades.CustomerFacade;
import tools.Message;
import tools.MessageType;

/**
 * A Services class for a customer type user.
 * @author asafs
 *
 */
@Path("/Private/Customer")
public class CustomerServices {

	private CustomerFacade customerFacade;
	
	
	public CustomerServices(@Context HttpServletRequest request) throws ApplicationException {
		HttpSession session = request.getSession(false);
		customerFacade= (CustomerFacade) session.getAttribute("facade");
	}
	
	/**
	 * A Method for purchasing a coupon.
	 * @param coup_idInString
	 * @return {@link tools.Message Message}
	 * @throws ApplicationException
	 */
	@Path("Coupons/{coupon_id}")
	@POST
	public Message purchaseCoupon(@PathParam("coupon_id") String coup_idInString) throws ApplicationException {
		long coupon_id= Long.parseLong(coup_idInString);	
		customerFacade.purchaseCoupon(coupon_id);
		
		return new Message("Coupon Purchased", "Succesfully purchased coupon #"+coupon_id, MessageType.SUCCESS);
	}
	
	/**
	 * A Method to get all customer's owned coupons.
	 * @return {@link java.util.Collection Collection} of {@link elements.Coupon Coupon}s 
	 * @throws ApplicationException
	 */
	@Path("Coupons")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Coupon> readCoupons() throws ApplicationException {
		
		return customerFacade.readAllPurchasedCoupons();
	}
	
	/**
	 * A Method to get customer's owned coupons, filtered by highest possible price.
	 * @return {@link java.util.Collection Collection} of {@link elements.Coupon Coupon}s 
	 * @throws ApplicationException
	 */
	@Path("Coupons/Price")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Coupon> readCouponsByPrice( @QueryParam("price") String priceInStringForm) throws ApplicationException{
		double price= Long.parseLong(priceInStringForm);
		
		return customerFacade.readCouponsByPrice(price);
		
	}
	/**
	 * A Method to get customer's owned coupons, filtered by type ({@link elements.CouponType CouponType}).
	 * @return {@link java.util.Collection Collection} of {@link elements.Coupon Coupon}s 
	 * @throws ApplicationException
	 */
	@Path("Coupons/Type")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Coupon> readCouponsByType( @QueryParam("type") String typeInString) throws ApplicationException{
		CouponType type= CouponType.fromString(typeInString);
		
		return customerFacade.readCouponsByType(type);
	}
	
	
	
	
}
