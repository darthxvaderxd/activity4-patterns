package com.nilfactor.activity4.controller;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.interceptor.Interceptors;

import com.nilfactor.activity4.data.AlbumService;
import com.nilfactor.activity4.model.SpotifyAlbum;
import com.nilfactor.activity4.util.LogInterceptor;
import com.nilfactor.activity4.util.ServiceService;

@ManagedBean(name="album_lookup")
@SessionScoped
@Interceptors(LogInterceptor.class)
public class AlbumLookupController {
	private String search;
	private List<SpotifyAlbum> results;
	
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
	public List<SpotifyAlbum> getResults() {
		return results;
	}
	
	@Interceptors(LogInterceptor.class)
	public void setResults(List<SpotifyAlbum> results) {
		this.results = results;
	}
	
	@Interceptors(LogInterceptor.class)
	public String doSearch() {
		if (search != null) {
			results = albumService.findAlbumsInService(search);
		}
		
		return "search";
	}
}
