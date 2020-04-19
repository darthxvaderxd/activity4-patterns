package com.nilfactor.activity4.controller;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;

import com.nilfactor.activity4.data.AlbumService;
import com.nilfactor.activity4.model.SpotifyAlbum;

@ManagedBean(name="search_albums")
@SessionScoped
public class SearchAlbumsController {
	private String search;
	private List<SpotifyAlbum> albums = new ArrayList<SpotifyAlbum>();
	@Inject private AlbumService albumService;
	
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
		this.albums = albumService.searchWildCardAlbum(search);
		return "library_search";
	}
}
