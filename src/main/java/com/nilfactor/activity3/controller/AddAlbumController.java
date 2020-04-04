package com.nilfactor.activity3.controller;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import com.nilfactor.activity3.data.SongService;
import com.nilfactor.activity3.model.SpotifySong;

@ManagedBean(name="album_add")
@SessionScoped
public class AddAlbumController {
	private String id;
	private String album;
	private String artist;
	private String picture;
	private String releaseDate;
	private List<SpotifySong> results;
	
	public AddAlbumController() {
		HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		if (id == null) {
			id = req.getParameter("id");
		}
		
		if (album == null) {
			album = req.getParameter("album");
		}
		
		if (artist == null) {
			artist = req.getParameter("band");
		}
		
		if (picture == null) {
			picture = req.getParameter("picture");
		}
		
		if (releaseDate == null) {
			releaseDate = req.getParameter("date");
		}
		
		results = SongService.findSongForAlbum(id);
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getAlbum() {
		return album;
	}

	public void setAlbum(String album) {
		this.album = album;
	}

	public String getArtist() {
		return artist;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}

	public List<SpotifySong> getResults() {
		return results;
	}
	
	public void setResults(List<SpotifySong> results) {
		this.results = results;
	}
	
	public String save() {
		return "saved";
	}
}
