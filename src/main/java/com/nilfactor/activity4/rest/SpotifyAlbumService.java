package com.nilfactor.activity4.rest;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.nilfactor.activity4.data.AlbumService;
import com.nilfactor.activity4.data.SongService;
import com.nilfactor.activity4.model.SpotifyAlbum;
import com.nilfactor.activity4.model.SpotifySong;

@RequestScoped
@Path("/albums")
@Produces("application/json")
@Consumes("application/json")
public class SpotifyAlbumService extends RestControllerBase {
	@GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
	public Response getSongs(@HeaderParam("authorization") String authString) {
		if (this.isUserAuthenticated(authString) == false) {
			return Response.status(Response.Status.UNAUTHORIZED)
		        .entity("{\"error\":\"User not authenticated\"}")
		        .build();
		}
		
		return Response.status(Response.Status.OK)
		        .entity(AlbumService.getAllAlbums())
		        .build();
	}
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getSong(@HeaderParam("authorization") String authString, @PathParam("id") String id) {
		if (this.isUserAuthenticated(authString) == false) {
			return Response.status(Response.Status.UNAUTHORIZED)
		        .entity("{\"error\":\"User not authenticated\"}")
		        .build();
		}
		
		return Response.status(Response.Status.OK)
		        .entity(AlbumService.getById(id))
		        .build();
	}
	
	@POST
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public Response addedAlbum(@HeaderParam("authorization") String authString, SpotifyAlbum album) {
		if (this.isUserAuthenticated(authString) == false) {
			return Response.status(Response.Status.UNAUTHORIZED)
		        .entity("{\"error\":\"User not authenticated\"}")
		        .build();
		}
		
		AlbumService.addAlbum(album);
		
		// Add the album if it exists
		List<SpotifySong> songs = album.getSongs();
		if (songs != null && songs.size() > 0) {
			for(int i = 0; i < songs.size(); i += 1) {
				SongService.saveSong(songs.get(i));
			}
		}
		
		return Response.status(Response.Status.OK)
		        .entity(AlbumService.getById(album.getAlbumId()))
		        .build();
	}
	
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response removeAlbum(@HeaderParam("authorization") String authString, @PathParam("id") String id) {
		if (this.isUserAuthenticated(authString) == false) {
			return Response.status(Response.Status.UNAUTHORIZED)
		        .entity("{\"error\":\"User not authenticated\"}")
		        .build();
		}
		
		SpotifyAlbum album = AlbumService.getById(id);
		if (album != null) {
			List<SpotifySong> songs = SongService.getSongsByAlbumId(id);
			if (songs != null && songs.size() > 0) {
				for(int i = 0; i < songs.size(); i += 1) {
					SongService.removeSong(songs.get(i));
				}
			}
			AlbumService.removeAlbum(album);
		} else {
			return Response.status(Response.Status.NOT_FOUND)
			        .entity("{ \"message\": \"album " + id + " does not exist\"}")
			        .build();
		}
		
		return Response.status(Response.Status.OK)
		        .entity("{ \"message\": \"album removed\"}")
		        .build();
	}
}
