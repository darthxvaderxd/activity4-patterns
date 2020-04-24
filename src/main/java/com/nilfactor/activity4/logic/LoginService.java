package com.nilfactor.activity4.logic;

import javax.interceptor.Interceptors;

import com.nilfactor.activity4.model.User;
import com.nilfactor.activity4.util.LogInterceptor;
import com.nilfactor.activity4.util.ServiceService;

@Interceptors(LogInterceptor.class)
public class LoginService {
	@Interceptors(LogInterceptor.class)
	public static boolean isValidLogin(User user, String username, String password) {
		ServiceService.getLogger("LoginService").debug("Debug: username => " + username);
		ServiceService.getLogger("LoginService").debug("Debug: password => " + password);
		ServiceService.getLogger("LoginService").debug("Debug: user.username => " + user.getUsername());
		ServiceService.getLogger("LoginService").debug("Debug: user.password => " + user.getPassword());
		
		// user must be active, username must match, password must match
		return username.equals(user.getUsername())
			&& password.equals(user.getPassword());
	}
}
