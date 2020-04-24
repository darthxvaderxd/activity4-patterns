package com.nilfactor.activity4.controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.interceptor.Interceptors;

import com.nilfactor.activity4.data.AlbumService;
import com.nilfactor.activity4.data.SongService;
import com.nilfactor.activity4.util.LogInterceptor;
import com.nilfactor.activity4.util.ServiceService;

@ManagedBean(name="main")
@SessionScoped
@Interceptors(LogInterceptor.class)
public class MainController {
	private int albumCount;
	private int songCount;
	
	private AlbumService albumService = ServiceService.getAlbumService();
	private SongService songService = ServiceService.getSongService();
	
	@Interceptors(LogInterceptor.class)
	public int getAlbumCount() {
		albumCount = albumService.getAlbumCount();
		return albumCount;
	}

	@Interceptors(LogInterceptor.class)
	public int getSongCount() {
		songCount = songService.getSongCount();
		return songCount;
	}
}
