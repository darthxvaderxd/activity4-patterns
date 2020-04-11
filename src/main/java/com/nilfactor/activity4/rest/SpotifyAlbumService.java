package com.nilfactor.activity4.rest;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.nilfactor.activity4.data.AlbumService;
import com.nilfactor.activity4.data.SongService;
import com.nilfactor.activity4.model.SpotifyAlbum;
import com.nilfactor.activity4.model.SpotifySong;

@RequestScoped
@Path("/albums")
@Produces("application/json")
@Consumes("application/json")
public class SpotifyAlbumService {
	@GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
	public List<SpotifyAlbum> getSongs() {
		return AlbumService.getAllAlbums();
	}
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public SpotifyAlbum getSong(@PathParam("id") String id) {
		return AlbumService.getById(id);
	}
	
	@POST
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public SpotifyAlbum addedAlbum(SpotifyAlbum album) {
		AlbumService.addAlbum(album);
		
		// Add the album if it exists
		List<SpotifySong> songs = album.getSongs();
		if (songs != null && songs.size() > 0) {
			for(int i = 0; i < songs.size(); i += 1) {
				SongService.saveSong(songs.get(i));
			}
		}
		
		return AlbumService.getById(album.getAlbumId());
	}
	
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public String removeAlbum(@PathParam("id") String id) {
		SpotifyAlbum album = AlbumService.getById(id);
		if (album != null) {
			List<SpotifySong> songs = SongService.getSongsByAlbumId(id);
			if (songs.size() > 0) {
				for(int i = 0; i < songs.size(); i += 1) {
					SongService.removeSong(songs.get(i));
				}
			}
			
		} else {
			return "{ \"message\": \"album " + id + " does not exist\"}";
		}
		
		return "{ \"message\": \"album removed\"}";
	}
}
