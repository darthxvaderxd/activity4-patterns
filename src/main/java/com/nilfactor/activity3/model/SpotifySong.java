package com.nilfactor.activity3.model;

public class SpotifySong {
	private String id;
	private Long disc;
	private Long trackNumber;
	private Long duration;
	private String previewUrl;
	private String name;
	private String album;
	private SpotifyAlbum spotifyAlbum; 
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public Long getDisc() {
		return disc;
	}
	
	public void setDisc(Long disc) {
		this.disc = disc;
	}
	
	public Long getTrackNumber() {
		return trackNumber;
	}
	
	public void setTrackNumber(Long trackNumber) {
		this.trackNumber = trackNumber;
	}
	
	public Long getDuration() {
		return duration;
	}
	
	public void setDuration(Long duration) {
		this.duration = duration;
	}
	
	public String getPreviewUrl() {
		return previewUrl;
	}
	
	public void setPreviewUrl(String previewUrl) {
		this.previewUrl = previewUrl;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getAlbum() {
		return album;
	}
	
	public void setAlbum(String album) {
		this.album = album;
	}

	public SpotifyAlbum getSpotifyAlbum() {
		return spotifyAlbum;
	}

	public void setSpotifyAlbum(SpotifyAlbum spotifyAlbum) {
		this.spotifyAlbum = spotifyAlbum;
	}
	
}
