package com.nilfactor.activity4.controller;
import java.io.IOException;
import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.nilfactor.activity4.data.UserService;
import com.nilfactor.activity4.logic.LoginService;
import com.nilfactor.activity4.model.User;
import com.nilfactor.activity4.util.LogInterceptor;

@ManagedBean(name="login")
@SessionScoped
@Interceptors(LogInterceptor.class)
public class LoginController implements Serializable {
	private static final long serialVersionUID = 1L;
	
	// Properties to access in JSF
	private String username;
	private String password;
	private String message;
	
	// to be used later
	private User user;
	
	@Inject private UserService userService;
	
	@Interceptors(LogInterceptor.class)
	public String getUsername() {
		return username;
	}
	
	@Interceptors(LogInterceptor.class)
	public void setUsername(String username) {
		this.username = username;
	}
	
	@Interceptors(LogInterceptor.class)
	public String getPassword() {
		return password;
	}
	
	@Interceptors(LogInterceptor.class)
	public void setPassword(String password) {
		this.password = password;
	}
	
	@Interceptors(LogInterceptor.class)
	public String getMessage() {
		// Load message from session if it exists
		if (message == null) {
			HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
			HttpSession session = req.getSession(false);
			if (session != null) {
				message = (String) session.getAttribute("login_message");
			}
		}
		
		return message;
	}
	
	@Interceptors(LogInterceptor.class)
	public void setMessage(String message) {
		this.message = message;
		
		// store message in session
		HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		HttpSession session = req.getSession(false);
		if (session != null) {
			session.setAttribute("login_message", message);
		}
	}
	
	@Interceptors(LogInterceptor.class)
	public User getUser() {
		// load user from session if it exists
		if (user == null) {
			HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
			HttpSession session = req.getSession(false);
			if (session != null) {
				user = userService.getByUsername((String) session.getAttribute("username"));
			}
		}
		
		return user;
	}
	
	@Interceptors(LogInterceptor.class)
	public void setUser(User user) {
		this.user = user;
	}
	
	@Interceptors(LogInterceptor.class)
	public String validateLogin() {
		// lookup user from database
		user =  userService.getByUsername(username);
		HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		HttpSession session = req.getSession(false);
		
		// validate login through business logic
		if (LoginService.isValidLogin(user, username, password) == true) {
			// store username in session
			if (session != null) {
				session.setAttribute("username", username);
			}
			
			// set welcome message
			setMessage("Welcome " + user.getFirstName() + "!");
			return "user";
		}
		
		// set login failure message
		setMessage("Invalid username/password");
		
		return "login";
	}
	
	@Interceptors(LogInterceptor.class)
	public void logout() throws IOException {
		HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		HttpServletResponse res = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
		HttpSession session = req.getSession(false);
		session.invalidate();
		res.sendRedirect(req.getContextPath() + "/faces/login.xhtml");
	}
}
