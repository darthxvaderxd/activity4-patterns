package com.nilfactor.activity4.controller;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;

import com.nilfactor.activity4.data.AlbumService;
import com.nilfactor.activity4.model.SpotifyAlbum;

@ManagedBean(name="browse_albums")
@SessionScoped
public class BrowseAlbumsController {
	private List<SpotifyAlbum> albums;
	@Inject private AlbumService albumService;
	
	public BrowseAlbumsController() { }
	
	@PostConstruct
	void init() {
		albums = albumService.getAllAlbums();
	}
	
	public List<SpotifyAlbum> getAlbums() {
		return albums;
	}
	
	public String refresh() {
		albums = albumService.getAllAlbums();
		return "browse";
	}
}
