package com.nilfactor.activity4.controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.interceptor.Interceptors;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.nilfactor.activity4.util.LogInterceptor;
import com.nilfactor.activity4.util.ServiceService;
import com.nilfactor.activity4.util.SpotifyClient;

@ManagedBean(name="spotify")
@SessionScoped
@Interceptors(LogInterceptor.class)
public class SpotifyController {
	private String clientSecret;
	private String spotifyUri;
	private SpotifyClient spotifyClient = ServiceService.getSpotifyClient();
	
	@Interceptors(LogInterceptor.class)
	public static void rejectSessionToken() {
		HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		HttpSession session = req.getSession(false);
		session.setAttribute("spotify_token", null);
	}
	
	@Interceptors(LogInterceptor.class)
	public static String getSessionToken() {
		HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		HttpSession session = req.getSession(false);
		return (String) session.getAttribute("spotify_token");
	}

	@Interceptors(LogInterceptor.class)
	public String getClientSecret() {
		HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		HttpSession session = req.getSession(false);
		
		// check if key is stored in session
		if (session != null) {
			clientSecret = (String) session.getAttribute("spotify_token");
		}
		
		// check if key has been supplied back to us by spotify
		if (clientSecret == null){
			String code = req.getParameter("access_token");
			
			// it has
			if (code != null) {
				session.setAttribute("spotify_token", code);
				clientSecret = code;
			}
		}
		
		return clientSecret;
	}

	@Interceptors(LogInterceptor.class)
	public String getSpotifyUri() {
		if (spotifyUri == null) {
			spotifyUri = spotifyClient.getSpotifyAuthUrl();
		}
		return spotifyUri;
	}
}
