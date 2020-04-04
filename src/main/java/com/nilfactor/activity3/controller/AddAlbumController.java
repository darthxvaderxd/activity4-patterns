package com.nilfactor.activity3.controller;

import java.io.IOException;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.nilfactor.activity3.data.AlbumService;
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
		id = loadData("id");
		album = loadData("album");
		artist = loadData("band");
		picture = loadData("picture");
		releaseDate = loadData("date");
		results = SongService.findSongForAlbum(id);
	}
	
	private void loadGetParams() {
		String newId = loadData("id");
		
		if (newId == null || id.equals(newId)) {
			// do nothing
		} else {
			id = newId;
			album = loadData("album");
			artist = loadData("band");
			picture = loadData("picture");
			releaseDate = loadData("date");
			results = SongService.findSongForAlbum(id);
		}
	}
	
	private String loadData(String name) {
		HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		return req.getParameter(name);
	}
	
	public String getId() {
		loadGetParams();
		return id;
	}
	
	public String getAlbum() {
		loadGetParams();
		return album;
	}

	public String getArtist() {
		loadGetParams();
		return artist;
	}

	public String getPicture() {
		loadGetParams();
		return picture;
	}

	public String getReleaseDate() {
		loadGetParams();
		return releaseDate;
	}

	public List<SpotifySong> getResults() {
		return results;
	}
	
	public String saveSongs() {
		if (results != null) {
			AlbumService.addAlbum(id);
			
			for (int i = 0; i < results.size(); i += 1) {
				SongService.addSong(results.get(i));
			}
			
			HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
			HttpServletResponse res = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
			try {
				res.sendRedirect(req.getContextPath() + "/faces/user.xhtml");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return "saved";
	}
}
