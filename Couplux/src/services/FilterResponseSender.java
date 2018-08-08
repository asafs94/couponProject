package services;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import tools.Message;
import tools.MessageType;

/**
 * A compatibility between normal filters, and REST WEBservices class, the user's who do not pass one of the various filters, are redirected to this class.
 * This Class generates an Error response that the AJAX request can then use to determine what type of action to perform. 
 * 
 * @author asafs
 *
 */
@Path("FilterResponse")
public class FilterResponseSender {

	/**
	 * When the user doesnt pass through the {@link filters.LoginSessionFilter LoginSessionFilter},
	 * He/She will be redirected to this method, which will return a response accordingly. 
	 * @return {@link javax.ws.rs.core.Response Response} with entity {@link tools.Message Message}
	 * 	 
	 * */
	@Path("Login")
	@GET
	public Response sendNeedToLoginError() {
		Message message= new Message("Not Logged In", "Need to be logged In to perform that action.", MessageType.LOGIN_NEEDED);
		
		return Response.status(Status.FORBIDDEN).entity(message).build();
	}
	/**
	 * When the user Doesnt pass through the {@link filters.AdminFilter AdminFilter}, {@link filters.CompanyFilter CompanyFilter}
	 * or {@link filters.CustomerFilter CustomerFilter}, He or she will be redirected to this service which produces a response accordingly.
	 * @return {@link javax.ws.rs.core.Response Response} with entity {@link tools.Message Message}
	 */
	@Path("Forbidden")
	@GET
	public Response sendForbiddenError() {
		Message message= new Message("Wrong Action", "This Action is not permitted to your user type.", MessageType.FORBIDDEN_CONTENT);
		
		return Response.status(Status.FORBIDDEN).entity(message).build();
	}
}
