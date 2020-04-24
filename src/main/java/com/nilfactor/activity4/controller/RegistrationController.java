package com.nilfactor.activity4.controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.nilfactor.activity4.data.UserService;
import com.nilfactor.activity4.util.LogInterceptor;

@ManagedBean(name="register")
@SessionScoped
@Interceptors(LogInterceptor.class)
public class RegistrationController {
	@NotNull(message = "Please enter a Username. This is a required field.")
	@Size(min = 3, max = 15)
	private String username;
	
	@NotNull(message = "Please enter a Password. This is a required field.")
	@Size(min = 6, max = 15)
	private String password;
	
	@NotNull(message = "Please enter your first name. This is a required field.")
	@Size(min = 1, max = 254)
	private String firstName;
	
	@NotNull(message = "Please enter your last name. This is a required field.")
	@Size(min = 1, max = 254)
	private String lastName;
	
	@NotNull(message = "Please enter your email. This is a required field.")
	@Size(min = 1, max = 254)
	private String email;
	
	private String message;
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
	public String getFirstName() {
		return firstName;
	}
	
	@Interceptors(LogInterceptor.class)
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	@Interceptors(LogInterceptor.class)
	public String getLastName() {
		return lastName;
	}
	
	@Interceptors(LogInterceptor.class)
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	@Interceptors(LogInterceptor.class)
	public String getEmail() {
		return email;
	}
	
	@Interceptors(LogInterceptor.class)
	public void setEmail(String email) {
		this.email = email;
	}
	
	@Interceptors(LogInterceptor.class)
	public String getMessage() {
		// Load message from session if it exists
		if (message == null) {
			HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
			HttpSession session = req.getSession(false);
			if (session != null) {
				message = (String) session.getAttribute("register_message");
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
			session.setAttribute("register_message", message);
		}
	}

	@Interceptors(LogInterceptor.class)
	public String register() {
		int result = userService.registerUser(username, password, firstName, lastName, email);
		System.out.println("Debug result of register => " + result);
		if (result == 1) {
			return "registered";
		} else if (result == 0) {
			setMessage("Please choose another username");
		} else {
			setMessage("An error has occured");
		}
		return "register";
	}
}
