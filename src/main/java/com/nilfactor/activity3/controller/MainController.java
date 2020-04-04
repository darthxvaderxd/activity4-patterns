package com.nilfactor.activity3.controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.nilfactor.activity3.data.AlbumService;
import com.nilfactor.activity3.data.SongService;

@ManagedBean(name="main")
@SessionScoped
public class MainController {
	int albumCount;
	int songCount;
	
	public int getAlbumCount() {
		albumCount = AlbumService.getAlbumCount();
		return albumCount;
	}

	public int getSongCount() {
		songCount = SongService.getSongCount();
		return songCount;
	}
}
