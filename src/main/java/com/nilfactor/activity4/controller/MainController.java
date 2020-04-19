package com.nilfactor.activity4.controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;

import com.nilfactor.activity4.data.AlbumService;
import com.nilfactor.activity4.data.SongService;

@ManagedBean(name="main")
@SessionScoped
public class MainController {
	private int albumCount;
	private int songCount;
	
	@Inject private AlbumService albumService;
	@Inject private SongService songService;
	
	public int getAlbumCount() {
		albumCount = albumService.getAlbumCount();
		return albumCount;
	}

	public int getSongCount() {
		songCount = songService.getSongCount();
		return songCount;
	}
}
