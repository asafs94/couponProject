package services;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import exceptions.ApplicationException;
import exceptions.ErrorType;
import facades.AdminFacade;
import facades.CompanyFacade;
import facades.CouponClientFacade;
import facades.CustomerFacade;
import system.mainSystem.ClientType;
import system.mainSystem.CouponSystem;
import tools.Message;
import tools.MessageType;
import tools.User;

/**
 * The Service for logging in.
 * 
 * @author asafs
 *
 */
@Path("Login")
public class LoginService {

	CouponSystem couponSystem;
	@Context
	HttpServletRequest request;
	@Context
	HttpServletResponse response;

	public LoginService() throws ApplicationException {
		couponSystem = CouponSystem.getInstance();
	}

	/**
	 * A method that returns a user object, with details of :
	 * <ul>
	 * <li>username</li>
	 * <li>password</li>
	 * <li>user type - Admin, Company, Customer or
	 * <b style="color:purple">null</b>(Guest)</li>
	 * 
	 * @return User
	 */
	@Path("GetUser")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public User getUser() {
		HttpSession session = request.getSession(false);
		if (session == null) {
			return new User(0, "Guest", "", null);
		} else {
			CouponClientFacade facade = (CouponClientFacade) session.getAttribute("facade");
			User user = (User) session.getAttribute("user");
			if (user == null || facade == null) {
				return new User(0, "Guest", "", null);
			} else {
				return user;
			}
		}
	}

	/**
	 * A login message for an Admin.
	 * 
	 * @param name
	 * @param password
	 * @return {@link tools.Message Message}
	 * @throws ApplicationException
	 */
	@Path("Admin")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Message adminLogin(@QueryParam("adminName") String name, @QueryParam("adminPass") String password)
			throws ApplicationException {
		logoutIfNeccessary();
		AdminFacade adminFacade = (AdminFacade) couponSystem.login(name, password, ClientType.ADMIN);

		HttpSession session = request.getSession();

		// Saving facade as Attribute on session:
		User user = new User(0, name, password, ClientType.ADMIN);
		session.setAttribute("user", user);
		session.setAttribute("facade", adminFacade);

		System.out.println("logged in as admin");
		return new Message("Login", "Logged In Succesfully as " + user.getName(), MessageType.SUCCESS);

	}

	/**
	 * A login message for a Company.
	 * 
	 * @param name
	 * @param password
	 * @return {@link tools.Message Message}
	 * @throws ApplicationException
	 */
	@Path("Company")
	@GET
	public Message companyLogin(@QueryParam("companyName") String name, @QueryParam("companyPass") String password)
			throws ApplicationException {
		logoutIfNeccessary();
		CompanyFacade companyFacade = (CompanyFacade) couponSystem.login(name, password, ClientType.COMPANY);

		HttpSession session = request.getSession();

		// Saving facade as Attribute on session:
		session.setAttribute("facade", companyFacade);
		User user = new User(companyFacade.getCompany_id(), name, password, ClientType.COMPANY);
		session.setAttribute("user", user);

		Message message = new Message("Company Login", "Login Successfull", MessageType.SUCCESS);

		return message;

	}

	/**
	 * A login message for a Customer.
	 * 
	 * @param name
	 * @param password
	 * @return {@link tools.Message Message}
	 * @throws ApplicationException
	 */
	@Path("Customer")
	@GET
	public Message customerLogin(@QueryParam("customerName") String name, @QueryParam("customerPass") String password)
			throws ApplicationException {
		CustomerFacade customerFacade = (CustomerFacade) couponSystem.login(name, password, ClientType.CUSTOMER);
		logoutIfNeccessary();
		HttpSession session = request.getSession();

		// Saving facade as Attribute on session:
		session.setAttribute("facade", customerFacade);

		User user = new User(customerFacade.getCustomer_id(), name, password, ClientType.CUSTOMER);
		session.setAttribute("user", user);
		Message message = new Message("Customer Login", "Login Successfull", MessageType.SUCCESS);

		return message;
	}

	/**
	 * A method used in login method's to make sure a user doesn't log in while
	 * already logged in as another user. invalidates the session. Then the user can
	 * login again.
	 */
	private void logoutIfNeccessary() {
		// Gets Session if exists
		if (request.getSession(false) != null) {
			HttpSession session = request.getSession(false);
			session.invalidate();
		}

	}

	/**
	 * A Logout Method.
	 * @return {@link tools.Message Message}
	 * @throws ApplicationException
	 */
	@Path("Logout")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Message logOut() throws ApplicationException {
		if (request.getSession(false) == null) {
			throw new ApplicationException(ErrorType.GENERIC_SYSTEM_ERROR);
		}
		request.getSession(false).invalidate();
		System.out.println("LogOut: Session terminated");
		return new Message("Logout", "Logged Out Succesfully", MessageType.SUCCESS);
	}

}
