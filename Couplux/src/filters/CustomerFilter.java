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

import facades.CouponClientFacade;
import facades.CustomerFacade;
/**
 * Servlet Filter implementation class CustomerFilter
 */
public class CustomerFilter implements Filter {

    public CustomerFilter() {
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
	}

	/**
	 * <h1>The doFilter Steps:</h1>
	 * <p>
	 * <ol>
	 * <li><h4>Gets The Session</h4></li>
	 * <li><h4>Checks Which Type Of Facade is on the session</h4>
	 * <ul>
	 * <li><b>{@link facades.CustomerFacade CustomerFacade}</b> - Next Step</li>
	 * <li><b>Not CustomerFacade</b> -  redirects to {@link services.FilterResponseSender#sendForbiddenError() sendForbiddenError()}</li>
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
		
		CouponClientFacade facade= (CouponClientFacade) req.getSession(false).getAttribute("facade");
		
		if(facade instanceof CustomerFacade) {
			System.out.println("Customer Filter: Request went through Customer Filter");
			chain.doFilter(request, response);
		}else {
			System.out.println("Customer Filter: Request did not go through Customer Filter");
			//if not a Customer => return to home page.
			System.out.println("Customer Filter: Redirecting...");
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
