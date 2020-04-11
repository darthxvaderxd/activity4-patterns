package com.nilfactor.activity4.controller;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.nilfactor.activity4.data.AlbumService;
import com.nilfactor.activity4.model.SpotifyAlbum;

@ManagedBean(name="search_albums")
@SessionScoped
public class SearchAlbumsController {
	String search;
	List<SpotifyAlbum> albums = new ArrayList<SpotifyAlbum>();
	
	public String getSearch() {
		return search;
	}
	
	public void setSearch(String search) {
		this.search = search;
	}
	
	public List<SpotifyAlbum> getAlbums() {
		return albums;
	}
	
	public void setAlbums(List<SpotifyAlbum> albums) {
		this.albums = albums;
	}
	
	public String searchAlbums() {
		this.albums = AlbumService.searchWildCardAlbum(search);
		return "library_search";
	}
}
