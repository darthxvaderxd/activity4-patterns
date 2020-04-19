package com.nilfactor.activity4.model;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nilfactor.activity4.data.SongService;
import com.nilfactor.activity4.util.StringListConverter;

@Dependent
@Named
@Singleton
@Entity(name = "com.nilfactor.activity4.model.SpotifyAlbum")
@XmlRootElement
@Table(name = "spotify_album", uniqueConstraints = @UniqueConstraint(columnNames = "album_id"))
public class SpotifyAlbum implements Serializable {
	/**
     * Default value included to remove warning. Remove or modify at will.
     **/
    private static final long serialVersionUID = 1L;
    
    @Inject private SongService songService;
    
    @Id
    @Column(name = "album_id", unique=true, nullable = false)
    private String albumId;
    
    @NotNull
	@NotEmpty
    @Column(name="image_link")
	private String imageLink;
    
    @NotNull
    @Column(name="image_width")
	private long imageWidth;
    
    @NotNull
    @Column(name="image_height")
	private long imageHeight;
    
    @NotNull
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
    
    @OneToMany(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
    @JoinTable(name="spotify_album",
    	joinColumns= {@JoinColumn(name="album_id", referencedColumnName="albumId", insertable=false, updatable=false)},
    	inverseJoinColumns={@JoinColumn(name="album_id", referencedColumnName="albumId")}
    )
	private List<SpotifySong> songs;
    
    @PostConstruct
    public void init() {
    	System.out.println("Moo...");
    	if (songs == null) {
    		System.out.println("Moo...");
			songs = songService.findSongForAlbum(albumId);
		}
    }
	
	public String getImageLink() {
		return imageLink;
	}
	
	@XmlElement
	public void setImageLink(String imageLink) {
		this.imageLink = imageLink;
	}
	
	public long getImageWidth() {
		return imageWidth;
	}
	
	@XmlElement
	public void setImageWidth(long imageWidth) {
		this.imageWidth = imageWidth;
	}
	
	public long getImageHeight() {
		return imageHeight;
	}
	
	@XmlElement
	public void setImageHeight(long imageHeight) {
		this.imageHeight = imageHeight;
	}
	
	public long getTotalTracks() {
		return totalTracks;
	}
	
	@XmlElement
	public void setTotalTracks(long totalTracks) {
		this.totalTracks = totalTracks;
	}
	
	public String getArtists() {
		return getAllArtistsAsString();
	}

	@XmlElement
	public void setArtists(String artists) {
		StringListConverter convert = new StringListConverter();
		this.artists = convert.convertToEntityAttribute(artists);
	}
	
	public void setArtists(List<String> artists) {
		this.artists = artists;
	}
	
	public String getReleaseDate() {
		return releaseDate;
	}
	
	@XmlElement
	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}
	
	public String getName() {
		return name;
	}
	
	@XmlElement
	public void setName(String name) {
		this.name = name;
	}
	
	public String getLink() {
		return link;
	}
	
	@XmlElement
	public void setLink(String link) {
		this.link = link;
	}
	
	public String getAlbumId() {
		return albumId;
	}
	
	@XmlElement
	public void setAlbumId(String id) {
		this.albumId = id;
	}
	
	public String getAllArtistsAsString() {
		return String.join(", ", artists);
	}
	
	@JsonIgnore
	public void setSongs(List<SpotifySong> songs) {
		this.songs = songs;
	}
	
	@JsonIgnore
	public List<SpotifySong> getSongs() {
		return songs;
	}
}
