package com.nilfactor.activity4.controller;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;

import com.nilfactor.activity4.data.AlbumService;
import com.nilfactor.activity4.model.SpotifyAlbum;

@ManagedBean(name="album_lookup")
@SessionScoped
public class AlbumLookupController {
	private String search;
	private List<SpotifyAlbum> results;
	
	@Inject private AlbumService albumService;
	
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
			results = albumService.findAlbumsInService(search);
		}
		
		return "search";
	}
}
