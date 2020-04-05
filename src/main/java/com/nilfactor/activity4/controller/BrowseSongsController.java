package com.nilfactor.activity4.controller;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.nilfactor.activity4.data.SongService;
import com.nilfactor.activity4.model.SpotifySong;

@ManagedBean(name="browse_songs")
@SessionScoped
public class BrowseSongsController {
	List<SpotifySong> songs;
	
	public BrowseSongsController() {
		songs = SongService.getAllSongs();
	}
	
	public List<SpotifySong> getSongs() {
		return songs;
	}
	
	public String refresh() {
		songs = SongService.getAllSongs();
		
		return "browse";
	}
}
