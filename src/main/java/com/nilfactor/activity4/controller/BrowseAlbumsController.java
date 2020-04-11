package com.nilfactor.activity4.controller;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.nilfactor.activity4.data.AlbumService;
import com.nilfactor.activity4.model.SpotifyAlbum;

@ManagedBean(name="browse_albums")
@SessionScoped
public class BrowseAlbumsController {
	List<SpotifyAlbum> albums;
	
	public BrowseAlbumsController() {
		albums = AlbumService.getAllAlbums();
	}
	
	public List<SpotifyAlbum> getAlbums() {
		return albums;
	}
	
	public String refresh() {
		albums = AlbumService.getAllAlbums();
		return "browse";
	}
}
