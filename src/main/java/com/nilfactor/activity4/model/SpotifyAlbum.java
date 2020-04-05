package com.nilfactor.activity4.model;

import java.util.List;

import com.nilfactor.activity4.data.SongService;

public class SpotifyAlbum {
	private String imageLink;
	private long imageWidth;
	private long imageHeight;
	private long totalTracks;
	private List<String> artists;
	private String releaseDate;
	private String name;
	private String link;
	private String id;
	List<SpotifySong> songs;
	
	public String getImageLink() {
		return imageLink;
	}
	
	public void setImageLink(String imageLink) {
		this.imageLink = imageLink;
	}
	
	public long getImageWidth() {
		return imageWidth;
	}
	
	public void setImageWidth(long imageWidth) {
		this.imageWidth = imageWidth;
	}
	
	public long getImageHeight() {
		return imageHeight;
	}
	
	public void setImageHeight(long imageHeight) {
		this.imageHeight = imageHeight;
	}
	
	public long getTotalTracks() {
		return totalTracks;
	}
	
	public void setTotalTracks(long totalTracks) {
		this.totalTracks = totalTracks;
	}
	
	public List<String> getArtists() {
		return artists;
	}

	public void setArtists(List<String> artists) {
		this.artists = artists;
	}
	
	public String getReleaseDate() {
		return releaseDate;
	}
	
	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getLink() {
		return link;
	}
	
	public void setLink(String link) {
		this.link = link;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getAllArtistsAsString() {
		return String.join(", ", artists);
	}
	
	public void setSongs(List<SpotifySong> songs) {
		this.songs = songs;
	}
	
	public List<SpotifySong> getSongs() {
		if (songs == null) {
			songs = SongService.findSongForAlbum(id);
		}
		
		return songs;
	}
}
