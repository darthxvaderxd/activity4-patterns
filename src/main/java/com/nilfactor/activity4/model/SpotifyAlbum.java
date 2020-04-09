package com.nilfactor.activity4.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.validator.constraints.NotEmpty;

import com.nilfactor.activity4.data.SongService;
import com.nilfactor.activity4.util.StringListConverter;

@Entity(name = "com.nilfactor.activity4.model.SpotifyAlbum")
@XmlRootElement
@Table(name = "spotify_album", uniqueConstraints = @UniqueConstraint(columnNames = "album_id"))
public class SpotifyAlbum implements Serializable {
	/**
     * Default value included to remove warning. Remove or modify at will.
     **/
    private static final long serialVersionUID = 1L;
    
    @Id
    @Column(name = "album_id", unique=true, nullable = false)
    private String albumId;
    
    @NotNull
	@NotEmpty
    @Column(name="image_link")
	private String imageLink;
    
    @NotNull
	@NotEmpty
    @Column(name="image_width")
	private long imageWidth;
    
    @NotNull
	@NotEmpty
    @Column(name="image_height")
	private long imageHeight;
    
    @NotNull
	@NotEmpty
    @Column(name="total_tracks")
	private long totalTracks;
    
    @Convert(converter = StringListConverter.class)
    @NotNull
	@NotEmpty
    @Column(name="artists")
	private List<String> artists;
    
    @NotNull
	@NotEmpty
    @Column(name="release_date")
	private String releaseDate;
    
    @NotNull
	@NotEmpty
    @Column(name="name")
	private String name;
    
    @NotNull
	@NotEmpty
    @Column(name="link")
	private String link;
    
    @OneToMany(cascade=CascadeType.ALL)
    @JoinColumn(name="album",referencedColumnName="album_id", insertable=false, updatable=false)
	private List<SpotifySong> songs;
	
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
	
	public String getAlbumId() {
		return albumId;
	}
	
	public void setAlbumId(String id) {
		this.albumId = id;
	}
	
	public String getAllArtistsAsString() {
		return String.join(", ", artists);
	}
	
	public void setSongs(List<SpotifySong> songs) {
		this.songs = songs;
	}
	
	public List<SpotifySong> getSongs() {
		if (songs == null) {
			songs = SongService.findSongForAlbum(albumId);
		}
		
		return songs;
	}
}
