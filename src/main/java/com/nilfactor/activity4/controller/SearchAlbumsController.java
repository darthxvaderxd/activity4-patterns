package com.nilfactor.activity4.controller;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.interceptor.Interceptors;

import com.nilfactor.activity4.data.AlbumService;
import com.nilfactor.activity4.model.SpotifyAlbum;
import com.nilfactor.activity4.util.LogInterceptor;
import com.nilfactor.activity4.util.ServiceService;

@ManagedBean(name="search_albums")
@SessionScoped
@Interceptors(LogInterceptor.class)
public class SearchAlbumsController {
	private String search;
	private List<SpotifyAlbum> albums = new ArrayList<SpotifyAlbum>();
	private AlbumService albumService = ServiceService.getAlbumService();
	
	@Interceptors(LogInterceptor.class)
	public String getSearch() {
		return search;
	}
	
	@Interceptors(LogInterceptor.class)
	public void setSearch(String search) {
		this.search = search;
	}
	
	@Interceptors(LogInterceptor.class)
	public List<SpotifyAlbum> getAlbums() {
		return albums;
	}
	
	@Interceptors(LogInterceptor.class)
	public void setAlbums(List<SpotifyAlbum> albums) {
		this.albums = albums;
	}
	
	@Interceptors(LogInterceptor.class)
	public String searchAlbums() {
		this.albums = albumService.searchWildCardAlbum(search);
		return "library_search";
	}
}
