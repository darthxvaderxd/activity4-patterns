package com.nilfactor.activity4.logic;

import javax.interceptor.Interceptors;

import com.nilfactor.activity4.model.User;
import com.nilfactor.activity4.util.LogInterceptor;

@Interceptors(LogInterceptor.class)
public class LoginService {
	@Interceptors(LogInterceptor.class)
	public static boolean isValidLogin(User user, String username, String password) {
		System.out.print("Debug: username => " + username);
		System.out.print("Debug: password => " + password);
		System.out.print("Debug: user.username => " + user.getUsername());
		System.out.print("Debug: user.password => " + user.getPassword());
		// user must be active, username must match, password must match
		return username.equals(user.getUsername())
			&& password.equals(user.getPassword());
	}
}
