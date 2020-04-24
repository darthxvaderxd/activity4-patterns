package com.nilfactor.activity4.rest;
import java.util.Base64;

import javax.inject.Inject;
import javax.interceptor.Interceptors;

import com.nilfactor.activity4.data.UserService;
import com.nilfactor.activity4.model.User;
import com.nilfactor.activity4.util.LogInterceptor;

class RestControllerBase {
	@Inject private UserService userService;
	
	/*
	 * This is for those who need to create the base64 encoded header value for Authorization
	 */
	@Interceptors(LogInterceptor.class)
	protected String generateB64AuthString(String username, String password) {
		String normalString = username + ":" + password;
		String encodedString = Base64.getEncoder().encodeToString(normalString.getBytes());
		return "Basic " + encodedString;
	}
	
	@Interceptors(LogInterceptor.class)
	protected boolean isUserAuthenticated(String authString) {
		try {
			String decodedAuth = "";
	        System.out.println(" ");
	        String[] parts = authString.split(" ");
	        String authInfo = parts[1];
	        byte[] bytes = null;
	        
		    bytes = Base64.getDecoder().decode(authInfo);
		    decodedAuth = new String(bytes);
	        
	        String[] decodedParts = decodedAuth.split(":");
	        
	        System.out.println("Decoded => " + decodedAuth);
	        
	        if (decodedParts.length < 2) {
	        	return false;
	        }
	       
	        String username = decodedParts[0];
	        String password = decodedParts[1];
	        User user = userService.getByUsername(username);
	        
	        System.out.println("Username => " + user.getUsername());
	        System.out.println("Password => " + user.getPassword());
	        
	        if (user.getPassword().equals(password)) {
	        	return true;
	        }
		} catch (Exception e) {
        	System.out.println(e.getMessage());
        	return false;
        }
         
        return false;
    }
}