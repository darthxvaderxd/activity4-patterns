package com.nilfactor.activity4.controller;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;

import com.nilfactor.activity4.data.SongService;
import com.nilfactor.activity4.model.SpotifySong;

@ManagedBean(name="browse_songs")
@SessionScoped
public class BrowseSongsController {
	private List<SpotifySong> songs;
	@Inject private SongService songService;
	
	public BrowseSongsController() {
		
	}
	
	@PostConstruct
	void init() {
		songs = songService.getAllSongs();
	}
	
	public List<SpotifySong> getSongs() {
		return songs;
	}
	
	public String refresh() {
		songs = songService.getAllSongs();
		
		return "browse";
	}
}
