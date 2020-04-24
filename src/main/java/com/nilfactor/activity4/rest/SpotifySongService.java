package com.nilfactor.activity4.rest;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.nilfactor.activity4.model.SpotifyAlbum;
import com.nilfactor.activity4.model.SpotifySong;
import com.nilfactor.activity4.util.LogInterceptor;
import com.nilfactor.activity4.data.AlbumService;
import com.nilfactor.activity4.data.SongService;

@RequestScoped
@Path("/songs")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Interceptors(LogInterceptor.class)
public class SpotifySongService extends RestControllerBase {
	@Inject private AlbumService albumService;
	@Inject private SongService songService;
	
	@Interceptors(LogInterceptor.class)
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
		        .entity(songService.getAllSongs())
		        .build();
	}
	
	@Interceptors(LogInterceptor.class)
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
		        .entity(songService.getSong(id))
		        .build();
	}
	
	@Interceptors(LogInterceptor.class)
	@POST
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public Response addedSong(@HeaderParam("authorization") String authString, SpotifySong song) {
		if (this.isUserAuthenticated(authString) == false) {
			return Response.status(Response.Status.UNAUTHORIZED)
		        .entity("{\"error\":\"User not authenticated\"}")
		        .build();
		}
		
		songService.addSong(song);
		
		// Add the album if it exists
		SpotifyAlbum album = song.getSpotifyAlbum();
		if (album != null) {
			albumService.addAlbum(album);
		}
		
		return Response.status(Response.Status.OK)
		        .entity(songService.getSong(song.getId()))
		        .build();
	}
	
	@Interceptors(LogInterceptor.class)
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response removeSong(@HeaderParam("authorization") String authString, @PathParam("id") String id) {
		if (this.isUserAuthenticated(authString) == false) {
			return Response.status(Response.Status.UNAUTHORIZED)
		        .entity("{\"error\":\"User not authenticated\"}")
		        .build();
		}
		
		SpotifySong song = songService.getById(id);
		if (song != null) {
			songService.removeSong(song);
		} else {
			return Response.status(Response.Status.OK)
			        .entity("{ \"message\": \"song " + id + " does not exist\"}")
			        .build();
		}
		
		return Response.status(Response.Status.OK)
		        .entity("{ \"message\": \"song removed\"}")
		        .build();
	}
}
