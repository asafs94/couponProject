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
		System.out.println("************ ADMIN FILTER ************");

		HttpServletRequest req = (HttpServletRequest) request; // TODO: move to filter's attributes.
		HttpServletResponse resp = (HttpServletResponse) response;
		// ^^There was already a filter for checking there is a session and login data.
		HttpSession session = req.getSession(false);
		CouponClientFacade facade = (CouponClientFacade) session.getAttribute("facade");
		if (facade instanceof AdminFacade) {
			System.out.println("Admin Filter: Request went through Admin Filter Succesfully");
			chain.doFilter(request, response);
		} else {
			// if not an Admin => return to home page.
			System.out.println("Admin Filter: Request did not pass through Admin Filter");
			System.out.println("Admin Filter: Redirecting...");
			String url = "/Couplux/Services/FilterResponse/Forbidden";
			resp.sendRedirect(resp.encodeRedirectURL(url));
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
