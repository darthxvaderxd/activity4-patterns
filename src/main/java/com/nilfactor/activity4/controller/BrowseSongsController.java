package com.nilfactor.activity4.controller;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.interceptor.Interceptors;

import com.nilfactor.activity4.data.SongService;
import com.nilfactor.activity4.model.SpotifySong;
import com.nilfactor.activity4.util.LogInterceptor;
import com.nilfactor.activity4.util.ServiceService;

@ManagedBean(name="browse_songs")
@SessionScoped
@Interceptors(LogInterceptor.class)
public class BrowseSongsController {
	private List<SpotifySong> songs;
	private SongService songService = ServiceService.getSongService();
	
	public BrowseSongsController() {
		songs = songService.getAllSongs();
	}
	
	@Interceptors(LogInterceptor.class)
	public List<SpotifySong> getSongs() {
		return songs;
	}
	
	@Interceptors(LogInterceptor.class)
	public String refresh() {
		songs = songService.getAllSongs();
		
		return "browse";
	}
}
