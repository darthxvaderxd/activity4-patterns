package com.nilfactor.activity3.filter;
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
import javax.servlet.http.HttpSession;

@WebFilter(filterName = "AuthFilter", urlPatterns = { "*.xhtml" })
public class LoginFilter implements Filter {
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain c) throws IOException, ServletException {
		try {
			// cast response/request to HttpServelet versions
			HttpServletRequest req = (HttpServletRequest) request;
			HttpServletResponse res = (HttpServletResponse) response;
			HttpSession ses = req.getSession(false);
			
			// get the request string
			String reqURI = req.getRequestURI();
			
			// logic of what to do if the path is x and user is or is not logged in
			if (ses != null && ses.getAttribute("username") != null && reqURI.indexOf("/login.xhtml") >= 0 ) { // we are already logged in
				res.sendRedirect(req.getContextPath() + "/faces/user.xhtml");
			} else if (reqURI.indexOf("/login.xhtml") >= 0
					|| (ses != null && ses.getAttribute("username") != null)
					|| reqURI.indexOf("/static/") >= 0 // to come public pages
					|| reqURI.contains("javax.faces.resource")) { // we either do not need to login or are logged in
				c.doFilter(request, response);
			} else { // user needs to login to access this page
				res.sendRedirect(req.getContextPath() + "/faces/login.xhtml");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
	}
}
