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

import facades.CouponClientFacade;

/**
 * A Filter that a checks if the user is logged in before reaching any methods
 * which are private.
 * 
 * 	<p> A filter for Services/Private/*</p>
 * 
 * @author asafs94
 *
 */
public class LoginSessionFilter implements Filter {

	/**
	 * <h2>The doFilter Method - Steps:</h2>
	 * <ol>
	 * <li><b>Checks if a session exists.</b>
	 * <ul>
	 * <li><b>No Session</b> - redirecting to
	 * {@link services.FilterResponseSender#sendNeedToLoginError()
	 * sendNeedToLoginError()} Method</li>
	 * <li><b>If there is a session</b> -moving on to next check (2)</li>
	 * </ul>
	 * </li>
	 * 
	 * <li><b>Checks if the session contains a {@link facades.CouponClientFacade
	 * Facade}.</b>
	 * <ul>
	 * <li><b>No Facade</b> - redirecting to
	 * {@link services.FilterResponseSender#sendNeedToLoginError()
	 * sendNeedToLoginError()} Method</li>
	 * <li><b>If there is a facade</b> -next Step.</li>
	 * </ul>
	 * </li>
	 * <li><b>Sends the request through the chain.doFilter Method</b></li>
	 * </ol>
	 * 
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		System.out.println("************ LOGIN FILTER ************");
		// Get the request and response:
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;

		// Get a Session, and check if it is null:
		HttpSession session = req.getSession(false);
		if (session == null) {
			// Session Doesn't exist
			System.out.println("---Login Filter: Session doesnt exist, sending error");
			// Redirect to login page:
			resp.sendRedirect(resp.encodeRedirectURL("/Couplux/Services/FilterResponse/Login"));
		} else {
			// Session exists
			System.out.println("---Login Filter: Session Exists");
			CouponClientFacade facade = (CouponClientFacade) session.getAttribute("facade");
			if (facade == null) {
				System.out.println("---Login Filter: No Facade- sending error");
				// If there is no facade object, sendRedirect to Login screen.
				resp.sendRedirect(resp.encodeRedirectURL("/Couplux/Services/FilterResponse/Login"));
			} else {
				System.out.println("Request went through Login Filter Succsfully");
				chain.doFilter(req, resp);
			}
		}

	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
	}

	@Override
	public void destroy() {

	}

}
