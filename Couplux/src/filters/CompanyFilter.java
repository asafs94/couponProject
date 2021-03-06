package filters;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import facades.CompanyFacade;
import facades.CouponClientFacade;

/**
 * Servlet Filter implementation class CompanyFilter
 */
@WebFilter("/CompanyFilter")
public class CompanyFilter implements Filter {

	
    /**
     * Default constructor. 
     */
    public CompanyFilter() {
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * <h1>The doFilter Steps:</h1>
	 * <p>
	 * <ol>
	 * <li><h4>Gets The Session</h4></li>
	 * <li><h4>Checks Which Type Of Facade is on the session</h4>
	 * <ul>
	 * <li><b>{@link facades.CompanyFacade CompanyFacade}</b> - Next Step</li>
	 * <li><b>Not CompanyFacade</b> -  redirects to {@link services.FilterResponseSender#sendForbiddenError() sendForbiddenError()}</li>
	 * </ul>
	 * </li>
	 * <li>
	 * chain.doFilter()
	 * </li>
	 * </ol>
	 * </p>
	 * 
	 * 	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req= (HttpServletRequest) request;
		HttpServletResponse resp= (HttpServletResponse) response;
		
		// This filter can only be reached after the request has passed the loggin and session facade.
		// Therefore, there is already a session and the logged in status of the user is verified.

		CouponClientFacade facade= (CouponClientFacade) req.getSession(false).getAttribute("facade");
		
		if(facade instanceof CompanyFacade) {
			System.out.println("Company Filter: Request went through Company Filter");
			chain.doFilter(request, response);
		}else {
			//---Debug----
			System.out.println("Request did not go through Company Filter");
			System.out.println("Company Filter: Redirecting...");
			//-------------
			// if not a Company => redirect to a Service which returns an errorResponse accordingly:
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
		// TODO Auto-generated method stub
	}

}
