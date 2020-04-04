package com.nilfactor.activity3.logic;

import com.nilfactor.activity3.model.User;

public class LoginService {
	public static boolean isValidLogin(User user, String username, String password) {
		// user must be active, username must match, password must match
		return user.getActive() == true
			&& username.equals(user.getUsername())
			&& password.equals(user.getPassword());
	}
}
