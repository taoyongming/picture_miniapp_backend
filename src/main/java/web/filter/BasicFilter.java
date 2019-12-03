package web.filter;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet Filter implementation class BasicFilter
 */
@Component
@WebFilter(
		filterName = "BasicFilter",
		urlPatterns = {"/*"}
)
public class BasicFilter implements Filter {

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse reb,
						 FilterChain arg2) throws IOException, ServletException {
		HttpServletResponse response = (HttpServletResponse)reb;
		HttpServletRequest request = (HttpServletRequest)arg0;
		response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));  
		response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");  
		response.setHeader("Access-Control-Max-Age", "0");  
		response.setHeader("Access-Control-Allow-Headers", "Origin, No-Cache, X-Requested-With, If-Modified-Since, Pragma, Last-Modified, Cache-Control, Expires, Content-Type, X-E4M-With,userId,token,Access-Control-Allow-Headers");  
		response.setHeader("Access-Control-Allow-Credentials", "true");  
		response.setHeader("XDomainRequestAllowed","1"); 

		
		arg2.doFilter(arg0, reb);
	}

	@Override
	public void destroy() {

	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	@Override
	public void init(FilterConfig fConfig) throws ServletException {
		
	}

}
