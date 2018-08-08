package filters;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import facades.AdminFacade;
import facades.CouponClientFacade;

/**
 * A Filter Class that checks if the client is an Admin before allowing Admin
 * Services to be reached.
 * 	<p> A filter for Services/Private/Admin/*</p>
 * @author asafs
 *
 */
public class AdminFilter implements Filter {

	public AdminFilter() {
	}

	public void destroy() {
	}

	/**
	 * <h1>The doFilter Steps:</h1>
	 * <p>
	 * <ol>
	 * <li><h4>Gets The Session</h4></li>
	 * <li><h4>Checks Which Type Of Facade is on the session</h4>
	 * <ul>
	 * <li><b>{@link facades.AdminFacade AdminFacade}</b> - Next Step</li>
	 * <li><b>Not AdminFacade</b> -  redirects to {@link services.FilterResponseSender#sendForbiddenError() sendForbiddenError()}</li>
	 * </ul>
	 * </li>
	 * <li>
	 * chain.doFilter()
	 * </li>
	 * </ol>
	 * </p>
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		//For Debug purposes:
		System.out.println("************ ADMIN FILTER ************");
		
		//Getting request and response as HttpServlet Objects:
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		
		// This filter can only be reached after the request has passed the loggin and session facade.
		// Therefore, there is already a session and the logged in status of the user is verified.
		// Getting session:
		HttpSession session = req.getSession(false);
		//Getting facade from the session:
		CouponClientFacade facade = (CouponClientFacade) session.getAttribute("facade");
		//Checking if its an admin:
		if (facade instanceof AdminFacade) {
			//For debug purposes:
			System.out.println("Admin Filter: Request went through Admin Filter Succesfully");
			//Passes the request along if its an admin:
			chain.doFilter(request, response);
		} else {
			System.out.println("Admin Filter: Request did not pass through Admin Filter");  //DEBUG
			System.out.println("Admin Filter: Redirecting..."); //DEBUG
			// if not an Admin => redirect to a Service which returns an errorResponse accordingly:
			/*
			This was made as a part of a temporary fix for a course project.
			the correct thing to do would have been to use a Jersey Filter and this problem would have not existed.
			*/
			String url = "/Couplux/Services/FilterResponse/Forbidden";
			resp.sendRedirect(resp.encodeRedirectURL(url));
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		
	}

}
