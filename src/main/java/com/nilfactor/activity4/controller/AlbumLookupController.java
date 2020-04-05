package com.nilfactor.activity4.controller;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.nilfactor.activity4.data.AlbumService;
import com.nilfactor.activity4.model.SpotifyAlbum;

@ManagedBean(name="album_lookup")
@SessionScoped
public class AlbumLookupController {
	String search;
	List<SpotifyAlbum> results;
	
	public String getSearch() {
		return search;
	}
	
	public void setSearch(String search) {
		this.search = search;
	}
	
	public List<SpotifyAlbum> getResults() {
		return results;
	}
	
	public void setResults(List<SpotifyAlbum> results) {
		this.results = results;
	}
	
	public String doSearch() {
		if (search != null) {
			results = AlbumService.findAlbumsInService(search);
		}
		
		return "search";
	}
}
