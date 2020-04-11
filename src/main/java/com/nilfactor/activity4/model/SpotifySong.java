package com.nilfactor.activity4.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.validator.constraints.NotEmpty;

import com.nilfactor.activity4.data.AlbumService;

@Entity(name = "com.nilfactor.activity4.model.SpotifySong")
@XmlRootElement
@Table(name = "spotify_song", uniqueConstraints = @UniqueConstraint(columnNames = "song_id"))
public class SpotifySong implements Serializable {
	/**
     * Default value included to remove warning. Remove or modify at will.
     **/
    private static final long serialVersionUID = 1L;
    
	@Id
	@Column(name = "song_id", unique=true, nullable = false)
	private String id;
	
	@NotNull
    @Column(name="disc")
	private Long disc;
	
	@NotNull
    @Column(name="track_number")
	private Long trackNumber;
	
	@NotNull
    @Column(name="duration")
	private Long duration;
	
	@NotNull
	@NotEmpty
    @Column(name="preview_url")
	private String previewUrl;
	
	@NotNull
	@NotEmpty
    @Column(name="name")
	private String name;
	
	@NotNull
	@NotEmpty
    @Column(name="album_id")
	private String albumId;
	
	@OneToOne
	@JoinColumn(name="id",referencedColumnName="album_id", insertable=false, updatable=false)
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
	
	@XmlElement
	public void setDisc(Long disc) {
		this.disc = disc;
	}
	
	public Long getTrackNumber() {
		return trackNumber;
	}
	
	@XmlElement
	public void setTrackNumber(Long trackNumber) {
		this.trackNumber = trackNumber;
	}
	
	public Long getDuration() {
		return duration;
	}
	
	@XmlElement
	public void setDuration(Long duration) {
		this.duration = duration;
	}
	
	public String getPreviewUrl() {
		return previewUrl;
	}
	
	@XmlElement
	public void setPreviewUrl(String previewUrl) {
		this.previewUrl = previewUrl;
	}
	
	public String getName() {
		return name;
	}
	
	@XmlElement
	public void setName(String name) {
		this.name = name;
	}
	
	public String getAlbumId() {
		return albumId;
	}
	
	@XmlElement
	public void setAlbumId(String album) {
		this.albumId = album;
		
		if (spotifyAlbum == null) {
			spotifyAlbum = AlbumService.getAlbum(album);
		}
	}

	public SpotifyAlbum getSpotifyAlbum() {
		return spotifyAlbum;
	}

	@XmlElement
	public void setSpotifyAlbum(SpotifyAlbum spotifyAlbum) {
		this.spotifyAlbum = spotifyAlbum;
		this.albumId = spotifyAlbum.getAlbumId();
	}
	
}
