package com.nilfactor.activity4.controller;

import java.io.IOException;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.interceptor.Interceptors;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.nilfactor.activity4.data.AlbumService;
import com.nilfactor.activity4.data.SongService;
import com.nilfactor.activity4.model.SpotifySong;
import com.nilfactor.activity4.util.LogInterceptor;
import com.nilfactor.activity4.util.ServiceService;

@ManagedBean(name="album_add")
@SessionScoped
@Interceptors(LogInterceptor.class)
public class AddAlbumController {
	private String id;
	private String album;
	private String artist;
	private String picture;
	private String releaseDate;
	private List<SpotifySong> results;
	
	private SongService songService = ServiceService.getSongService();
	private AlbumService albumService = ServiceService.getAlbumService();
	
	public AddAlbumController() {
		id = loadData("id");
		album = loadData("album");
		artist = loadData("band");
		picture = loadData("picture");
		releaseDate = loadData("date");
		results = songService.findSongForAlbum(id);
	}
	
	@Interceptors(LogInterceptor.class)
	private void loadGetParams() {
		String newId = loadData("id");
		
		if (newId == null || id.equals(newId)) {
			// do nothing
		} else {
			results = null;
			id = newId;
			album = loadData("album");
			artist = loadData("band");
			picture = loadData("picture");
			releaseDate = loadData("date");
			results = songService.findSongForAlbum(id);
			HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
			HttpServletResponse res = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
			try {
				res.sendRedirect(req.getContextPath() + "/faces/add_album_songs.xhtml");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				ServiceService.getLogger(this.getClass().getName()).error(e.getMessage());
				ServiceService.getLogger(this.getClass().getName()).error(e.getStackTrace());
			}
		}
	}
	
	@Interceptors(LogInterceptor.class)
	private String loadData(String name) {
		HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		return req.getParameter(name);
	}
	
	@Interceptors(LogInterceptor.class)
	public String getId() {
		loadGetParams();
		return id;
	}
	
	@Interceptors(LogInterceptor.class)
	public String getAlbum() {
		loadGetParams();
		return album;
	}

	@Interceptors(LogInterceptor.class)
	public String getArtist() {
		loadGetParams();
		return artist;
	}

	@Interceptors(LogInterceptor.class)
	public String getPicture() {
		loadGetParams();
		return picture;
	}

	@Interceptors(LogInterceptor.class)
	public String getReleaseDate() {
		loadGetParams();
		return releaseDate;
	}

	@Interceptors(LogInterceptor.class)
	public List<SpotifySong> getResults() {
		return results;
	}
	
	@Interceptors(LogInterceptor.class)
	public String saveSongs() {
		if (results != null) {
			albumService.addAlbum(id);
			
			for (int i = 0; i < results.size(); i += 1) {
				songService.addSong(results.get(i));
			}
			
			HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
			HttpServletResponse res = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
			try {
				res.sendRedirect(req.getContextPath() + "/faces/user.xhtml");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				ServiceService.getLogger(this.getClass().getName()).error(e.getMessage());
				ServiceService.getLogger(this.getClass().getName()).error(e.getStackTrace());
			}
		}
		return "saved";
	}
}
