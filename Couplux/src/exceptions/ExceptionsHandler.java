package exceptions;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import tools.Message;
import tools.MessageType;



@Provider
public class ExceptionsHandler implements ExceptionMapper<ApplicationException> {

	@Override
	public Response toResponse(ApplicationException e) {
		System.out.println("Error:------------> "+ e.getErrorType().toString()); // A note to the server console, will be deleted in production.
		//Getting Enum ErrorType:
		ErrorType error = e.getErrorType();
		//Editing Generic Message:
		Message message=new Message("System Error", "Please try again later.",MessageType.ERROR);
		//Editing Generic Status:
		Status status=Status.SERVICE_UNAVAILABLE;
		
		//Checking ErrorType:
		switch (error) {
		
		case GENERIC_SYSTEM_ERROR: {
			message = new Message("System Error", "Oh oh.. Something went wrong, please try again later...",MessageType.ERROR);
			e.printStackTrace();
			status = Status.SERVICE_UNAVAILABLE;
			break;
		}

		case COMPANY_DOESNT_EXIST: {
			message = new Message("Error", "The company your are trying to reach does not exist.",MessageType.FALIURE);
			status = Status.NOT_FOUND;
			break;
		}

		case COMPANY_EXISTS_BY_NAME: {
			message = new Message("Company Registration failed", "A company already exists by that name.",MessageType.FALIURE);
			status = Status.CONFLICT;break;
		}

		case COMPANY_NAME_DOESNT_MATCH: {
			message = new Message("Update Could not be completed",
					"An attemt to change the company name has been made.",MessageType.ERROR);
			status = Status.CONFLICT;break;
		}

		case COUPON_EXISTS_BY_NAME: {
			message = new Message("Coupon could not be created", "A coupon by that name already exists.",MessageType.FALIURE);
			status = Status.CONFLICT;break;
		}

		case COUPON_EXPIRED_OR_OUT_OF_STOCK: {
			message = new Message("Coupon could not be purchased", "Coupon is expired or out of stock.",MessageType.FALIURE);
			status = Status.CONFLICT;break;
		}

		case COUPON_NAME_DOESNT_MATCH: {
			message = new Message("Coupon could not be updated", "An attempt to change coupon name has been made.",MessageType.FALIURE);
			status = Status.CONFLICT;break;
		}

		case CUSTOMER_ALREADY_HAS_COUPON: {
			message = new Message("Coupon could not be purchased", "Coupon has already been purchased once.",MessageType.FALIURE);
			status = Status.CONFLICT;break;
		}

		case CUSTOMER_DOESNT_EXIST: {
			message = new Message("Customer could not be found",
					"The customer you are trying to reach does not exist.",MessageType.FALIURE);
			status = Status.NOT_FOUND;break;
		}

		case CUSTOMER_EXISTS_BY_NAME: {
			message = new Message("Registration failed", "A Customer by that name already exists",MessageType.FALIURE);
			status = Status.CONFLICT;break;
		}

		case CUSTOMER_NAME_DOESNT_MATCH: {
			message = new Message("Customer data could not be updated",
					"An attempt to change the customer name has been made.",MessageType.FALIURE);
			status = Status.CONFLICT;break;
		}

		case LOGIN_UNSUCCESSFULL: {
			message = new Message("Login Unsuccessfull", "Username or Password not correct",MessageType.FALIURE);
			status = Status.BAD_REQUEST;break;
		}

		case NAME_TOO_LONG: {
			message = new Message("Regsitration failed", "Name contains too many characters",MessageType.FALIURE);
			status = Status.NOT_ACCEPTABLE;break;
		}

		case PASSWORD_TOO_LONG: {
			message = new Message("Regsitration failed", "Password contains too many characters",MessageType.FALIURE);
			status = Status.NOT_ACCEPTABLE;
			break;
		}

		case WRONG_TYPE_ENTERED_INTO_CHECKER: {
			message = new Message("System Error", "Something went wrong, please refresh and try again.",MessageType.ERROR);
			status = Status.SERVICE_UNAVAILABLE;break;
		}
		}
		
		return Response.status(status)
				.entity(message)
				.type(MediaType.APPLICATION_JSON)
				.build();

	}
	

}
