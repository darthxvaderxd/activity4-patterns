package com.nilfactor.activity4.controller;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.interceptor.Interceptors;

import com.nilfactor.activity4.data.AlbumService;
import com.nilfactor.activity4.model.SpotifyAlbum;
import com.nilfactor.activity4.util.LogInterceptor;
import com.nilfactor.activity4.util.ServiceService;

@ManagedBean(name="browse_albums")
@SessionScoped
@Interceptors(LogInterceptor.class)
public class BrowseAlbumsController {
	private List<SpotifyAlbum> albums;
	private AlbumService albumService = ServiceService.getAlbumService();
	
	public BrowseAlbumsController() {
		albums = albumService.getAllAlbums();
	}
	
	@Interceptors(LogInterceptor.class)
	public List<SpotifyAlbum> getAlbums() {
		return albums;
	}
	
	@Interceptors(LogInterceptor.class)
	public String refresh() {
		albums = albumService.getAllAlbums();
		return "browse";
	}
}
