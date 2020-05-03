package com.nilfactor.activity4.util;

import javax.inject.Inject;
import javax.interceptor.Interceptors;

import org.apache.log4j.Logger;

import com.nilfactor.activity4.data.AlbumService;
import com.nilfactor.activity4.data.SongService;

@Interceptors(LogInterceptor.class)
public class ServiceService {
	@Inject private static SpotifyClient spotifyClient;
	@Inject private static AlbumService albumService;
	@Inject private static SongService songService;
	@Inject private static HibernateUtil hibernateUtil;
	@Inject private static LoggingService loggingService;
	
	@Interceptors(LogInterceptor.class)
	public static LoggingService getLoggingService() {
		if (loggingService == null) {
			loggingService = LoggingService.getInstance();
		}
		return loggingService;
	}
	
	@Interceptors(LogInterceptor.class)
	public static void setLoggingService(LoggingService ls) {
		loggingService = ls;
	}
	
	@Interceptors(LogInterceptor.class)
	public static Logger getLogger(String className) {
		return getLoggingService().getLogger(className);
	}
	
	@Interceptors(LogInterceptor.class)
	public static SpotifyClient getSpotifyClient() {
		if (spotifyClient == null) {
			spotifyClient = new SpotifyClient();
		}
		return spotifyClient;
	}
	
	@Interceptors(LogInterceptor.class)
	public static void setSpotifyClient(SpotifyClient sc) {
		spotifyClient = sc;
	}
	
	@Interceptors(LogInterceptor.class)
	public static AlbumService getAlbumService() {
		if (albumService == null) {
			albumService = new AlbumService();
			albumService.init();
		}
		return albumService;
	}
	
	@Interceptors(LogInterceptor.class)
	public static void setAlbumService(AlbumService as) {
		albumService = as;
	}
	
	@Interceptors(LogInterceptor.class)
	public static SongService getSongService() {
		if (songService == null) {
			songService = new SongService();
			songService.init();
		}
		return songService;
	}
	
	@Interceptors(LogInterceptor.class)
	public static void setSongService(SongService ss) {
		songService = ss;
	}

	@Interceptors(LogInterceptor.class)
	public static HibernateUtil getHibernateUtil() {
		if (hibernateUtil == null) {
			hibernateUtil = new HibernateUtil();
		}
		return hibernateUtil;
	}
	
	@Interceptors(LogInterceptor.class)
	public static void setHibernateUtil(HibernateUtil hu) {
		hibernateUtil = hu;
	}
}
