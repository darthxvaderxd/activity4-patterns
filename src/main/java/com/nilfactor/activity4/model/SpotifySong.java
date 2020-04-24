package com.nilfactor.activity4.model;

import java.io.Serializable;

import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;
import javax.interceptor.Interceptors;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.validator.constraints.NotEmpty;

import com.nilfactor.activity4.data.AlbumService;
import com.nilfactor.activity4.util.LogInterceptor;

@ManagedBean
@Dependent
@Named
@Entity(name = "com.nilfactor.activity4.model.SpotifySong")
@XmlRootElement
@Table(name = "spotify_song", uniqueConstraints = @UniqueConstraint(columnNames = "song_id"))
@Interceptors(LogInterceptor.class)
public class SpotifySong implements Serializable {
	/**
     * Default value included to remove warning. Remove or modify at will.
     **/
    private static final long serialVersionUID = 1L;
    
    @Inject private AlbumService albumService;
    
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
	

	@OneToOne(fetch = FetchType.EAGER, optional = false)
	@JoinTable(name="spotify_album",
		joinColumns= {@JoinColumn(name="album_id", referencedColumnName="albumId", insertable=false, updatable=false)},
		inverseJoinColumns={@JoinColumn(name="album_id", referencedColumnName="albumId")}
	)
	private SpotifyAlbum spotifyAlbum;
	
	@Interceptors(LogInterceptor.class)
	public String getId() {
		return id;
	}
	
	@Interceptors(LogInterceptor.class)
	public void setId(String id) {
		this.id = id;
	}
	
	@Interceptors(LogInterceptor.class)
	public Long getDisc() {
		return disc;
	}
	
	@XmlElement
	@Interceptors(LogInterceptor.class)
	public void setDisc(Long disc) {
		this.disc = disc;
	}
	
	@Interceptors(LogInterceptor.class)
	public Long getTrackNumber() {
		return trackNumber;
	}
	
	@XmlElement
	@Interceptors(LogInterceptor.class)
	public void setTrackNumber(Long trackNumber) {
		this.trackNumber = trackNumber;
	}
	
	@Interceptors(LogInterceptor.class)
	public Long getDuration() {
		return duration;
	}
	
	@XmlElement
	@Interceptors(LogInterceptor.class)
	public void setDuration(Long duration) {
		this.duration = duration;
	}
	
	@Interceptors(LogInterceptor.class)
	public String getPreviewUrl() {
		return previewUrl;
	}
	
	@XmlElement
	@Interceptors(LogInterceptor.class)
	public void setPreviewUrl(String previewUrl) {
		this.previewUrl = previewUrl;
	}
	
	@Interceptors(LogInterceptor.class)
	public String getName() {
		return name;
	}
	
	@XmlElement
	@Interceptors(LogInterceptor.class)
	public void setName(String name) {
		this.name = name;
	}
	
	@Interceptors(LogInterceptor.class)
	public String getAlbumId() {
		return albumId;
	}
	
	@XmlElement
	@Interceptors(LogInterceptor.class)
	public void setAlbumId(String albumId) {
		this.albumId = albumId;
	}

	@Interceptors(LogInterceptor.class)
	public SpotifyAlbum getSpotifyAlbum() {
		return spotifyAlbum;
	}

	@XmlElement
	@Interceptors(LogInterceptor.class)
	public void setSpotifyAlbum(SpotifyAlbum spotifyAlbum) {
		this.spotifyAlbum = spotifyAlbum;
		this.albumId = spotifyAlbum.getAlbumId();
	}
	
}
