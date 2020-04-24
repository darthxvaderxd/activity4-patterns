package com.nilfactor.activity4.util;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import org.json.simple.*;

import com.nilfactor.activity4.controller.SpotifyController;
import com.nilfactor.activity4.model.SpotifyAlbum;
import com.nilfactor.activity4.model.SpotifySong;

import java.net.URLEncoder;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.Dependent;
import javax.inject.Named;
import javax.interceptor.Interceptors;

@Dependent
@Named
@Interceptors(LogInterceptor.class)
public class SpotifyClient {
	private String clientId = "6024456da7984f6ea98ee546114fd468";
	private final CloseableHttpClient httpClient = HttpClients.createDefault();
	
	@SuppressWarnings("deprecation")
	@Interceptors(LogInterceptor.class)
	public String getSpotifyAuthUrl() {
		return "https://accounts.spotify.com/authorize?response_type=token"
				+ "&client_id=" + clientId
				+ "&redirect_uri="
				+ URLEncoder.encode("http://localhost:8080/Activity4-Patterns/faces/spotify.xhtml");
	}
	
	@SuppressWarnings({ "unchecked", "deprecation" })
	@Interceptors(LogInterceptor.class)
	public List<SpotifySong> lookupAlbumTracks(String id, Long offset) throws Exception {
		String authToken = SpotifyController.getSessionToken();
		List<SpotifySong> spotifySongs = new ArrayList<SpotifySong>();
		
		if (authToken != null) {
			String uri = "https://api.spotify.com/v1/albums/"
			+ URLEncoder.encode(id)
			+ "/tracks"
			+ "?offset=" + offset;
			
			HttpGet req = new HttpGet(uri);
			req.addHeader("Authorization", "Bearer " + authToken);
			
			try (CloseableHttpResponse response = httpClient.execute(req)) {
				String httpResponse = response.getStatusLine().toString();
				JSONObject data = (JSONObject) JSONValue.parse(EntityUtils.toString(response.getEntity()));;
				JSONObject error = (JSONObject) data.get("error");
				
				System.out.println("Debug httpResponse => " + httpResponse);
				System.out.println("Debug dataString => " + data.toJSONString());
				
				if (error != null && (Long) error.get("status") == 401) {
					System.out.println("Need to refresh token?");
					SpotifyController.rejectSessionToken();
				} else {
					List<JSONObject> songs = (List<JSONObject>) data.get("items");
					if (songs.size() > 0) {
						for (int i = 0; i < songs.size(); i += 1) {
							JSONObject song = songs.get(i);
							
							long duration = (long) song.get("duration_ms");
							long seconds = duration / 1000;  
									
							SpotifySong spotifySong = new SpotifySong();
							spotifySong.setId((String) song.get("id"));
							spotifySong.setName((String) song.get("name"));
							spotifySong.setAlbumId(id);
							spotifySong.setDisc((long) song.get("disc_number"));
							spotifySong.setTrackNumber((Long) song.get("track_number"));
							spotifySong.setDuration(seconds);
							spotifySong.setPreviewUrl((String) song.get("preview_url"));
							spotifySongs.add(spotifySong);
						}
						
						String nextTrackLink = (String) data.get("next");
						
						if (nextTrackLink != null) {
							offset = offset + spotifySongs.size();
							List<SpotifySong> moreSongs = lookupAlbumTracks(id, offset);
							for (int i = 0; i < moreSongs.size(); i += 1) {
								spotifySongs.add(moreSongs.get(i));
							}
						}
					}
				}
			}
		}
		
		return spotifySongs;
	}
	
	@SuppressWarnings({ "unchecked", "deprecation" })
	@Interceptors(LogInterceptor.class)
	public SpotifyAlbum lookupAlbum(String id) throws Exception {
		String authToken = SpotifyController.getSessionToken();
		if (authToken != null) {
			String uri = "https://api.spotify.com/v1/albums/" + URLEncoder.encode(id);
			
			HttpGet req = new HttpGet(uri);
			req.addHeader("Authorization", "Bearer " + authToken);
			
			try (CloseableHttpResponse response = httpClient.execute(req)) {
				String httpResponse = response.getStatusLine().toString();
				JSONObject data = (JSONObject) JSONValue.parse(EntityUtils.toString(response.getEntity()));;
				JSONObject error = (JSONObject) data.get("error");
				
				System.out.println("Debug httpResponse => " + httpResponse);
				System.out.println("Debug dataString => " + data.toJSONString());
				
				
				if (error != null && (Long) error.get("status") == 401) {
					System.out.println("Need to refresh token?");
					SpotifyController.rejectSessionToken();
				} else {
					SpotifyAlbum spotifyAlbum = new SpotifyAlbum();
					
					List<JSONObject> images = (List<JSONObject>) data.get("images");
					JSONObject image = images.get(0);
					
					List<JSONObject> jsonArtists = (List<JSONObject>) data.get("artists");
					List<String> artists = new ArrayList<String>();
					
					for (int m = 0; m < jsonArtists.size(); m += 1) {
						JSONObject artist = (JSONObject) jsonArtists.get(m);
						artists.add((String) artist.get("name"));
					}
					
					spotifyAlbum.setAlbumId(id);
					spotifyAlbum.setImageHeight((long) image.get("height"));
					spotifyAlbum.setImageWidth((long) image.get("width"));
					spotifyAlbum.setImageLink(URLDecoder.decode((String) image.get("url")));
					spotifyAlbum.setReleaseDate((String) data.get("release_date"));
					spotifyAlbum.setName((String) data.get("name"));
					spotifyAlbum.setTotalTracks((long) data.get("total_tracks"));
					spotifyAlbum.setLink(URLDecoder.decode((String) data.get("href")));
					spotifyAlbum.setArtists(artists);
					spotifyAlbum.setSongs(lookupAlbumTracks(id, (long) 0));
					
					return spotifyAlbum;
				}
			}
		}
		
		return null;
	}
	
	@SuppressWarnings({ "unchecked", "deprecation" })
	@Interceptors(LogInterceptor.class)
	public List<SpotifyAlbum> lookupAlbums(String album) throws Exception {
		String authToken = SpotifyController.getSessionToken();
		List<SpotifyAlbum> spotifyAlbums = new ArrayList<SpotifyAlbum>();
		
		if (authToken != null) {
			String uri = "https://api.spotify.com/v1/search?q=" + URLEncoder.encode(album)
					+ "&client_id=" + clientId
					+ "&type=album";
			
			HttpGet req = new HttpGet(uri);
			req.addHeader("Authorization", "Bearer " + authToken);
			
			try (CloseableHttpResponse response = httpClient.execute(req)) {
				String httpResponse = response.getStatusLine().toString();
				JSONObject data = (JSONObject) JSONValue.parse(EntityUtils.toString(response.getEntity()));;
				JSONObject error = (JSONObject) data.get("error");
				
				System.out.println("Debug httpResponse => " + httpResponse);
				System.out.println("Debug dataString => " + data.toJSONString());
				
				
				if (error != null && (Long) error.get("status") == 401) {
					System.out.println("Need to refresh token?");
					SpotifyController.rejectSessionToken();
				} else {
					JSONObject jsonAlbums = (JSONObject) data.get("albums"); 
					List<JSONObject> albums = (List<JSONObject>) jsonAlbums.get("items");
					if (albums.size() > 0) {
						for (int i = 0; i < albums.size(); i += 1) {
							JSONObject a = albums.get(i);
							
							List<JSONObject> images = (List<JSONObject>) a.get("images");
							JSONObject image = images.get(0);
							
							List<JSONObject> jsonArtists = (List<JSONObject>) a.get("artists");
							List<String> artists = new ArrayList<String>();
							
							for (int m = 0; m < jsonArtists.size(); m += 1) {
								JSONObject artist = (JSONObject) jsonArtists.get(m);
								artists.add((String) artist.get("name"));
							}
							
							SpotifyAlbum spotifyAlbum = new SpotifyAlbum();
							spotifyAlbum.setAlbumId((String) a.get("id"));
							spotifyAlbum.setImageHeight((long) image.get("height"));
							spotifyAlbum.setImageWidth((long) image.get("width"));
							spotifyAlbum.setImageLink(URLDecoder.decode((String) image.get("url")));
							spotifyAlbum.setReleaseDate((String) a.get("release_date"));
							spotifyAlbum.setName((String) a.get("name"));
							spotifyAlbum.setTotalTracks((long) a.get("total_tracks"));
							spotifyAlbum.setLink(URLDecoder.decode((String) a.get("href")));
							spotifyAlbum.setArtists(artists);
							spotifyAlbums.add(spotifyAlbum);
						}
					}
				}
			}
		}
		
		return spotifyAlbums;
	}
}
