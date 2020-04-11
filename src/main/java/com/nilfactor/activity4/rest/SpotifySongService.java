package com.nilfactor.activity4.rest;

import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import com.nilfactor.activity4.model.SpotifyAlbum;
import com.nilfactor.activity4.model.SpotifySong;
import com.nilfactor.activity4.data.AlbumService;
import com.nilfactor.activity4.data.SongService;

@RequestScoped
@Path("/songs")
@Produces("application/json")
@Consumes("application/json")
public class SpotifySongService {
	
	@GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
	public List<SpotifySong> getSongs() {
		return SongService.getAllSongs();
	}
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public SpotifySong getSong(@PathParam("id") String id) {
		return SongService.getSong(id);
	}
	
	@POST
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public SpotifySong addedSong(SpotifySong song) {
		SongService.addSong(song);
		
		// Add the album if it exists
		SpotifyAlbum album = song.getSpotifyAlbum();
		if (album != null) {
			AlbumService.addAlbum(album);
		}
		
		return SongService.getSong(song.getId());
	}
	
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public String removeSong(@PathParam("id") String id) {
		SpotifySong song = SongService.getById(id);
		if (song != null) {
			SongService.removeSong(song);
		} else {
			return "{ \"message\": \"song " + id + " does not exist\"}";
		}
		
		return "{ \"message\": \"song removed\"}";
	}
}
