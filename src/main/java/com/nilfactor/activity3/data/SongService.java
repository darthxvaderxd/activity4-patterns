package com.nilfactor.activity3.data;

import java.util.ArrayList;
import java.util.List;

import com.nilfactor.activity3.model.SpotifyAlbum;
import com.nilfactor.activity3.model.SpotifySong;
import com.nilfactor.activity3.util.SpotifyClient;
import javax.ejb.*;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class SongService {
	private static List<SpotifySong> songs = new ArrayList<SpotifySong>();
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public static int getSongCount() {
		return songs.size();
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public static void addSong(SpotifySong song) {
		SpotifyAlbum album = song.getSpotifyAlbum();
		
		if (album == null) {
			song.setSpotifyAlbum(AlbumService.getAlbum(song.getAlbum()));
		}
		
		songs.add(song);
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public static void removeSong(SpotifySong song) {
		songs.remove(song);
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public static SpotifySong getSong(String id) {
		for (int i = 0; i < songs.size(); i += 1) {
			SpotifySong song = songs.get(i);
			if (song.getId().equals(id)) {
				return song;
			}
		}
		
		return null;
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public static void addSongs(List<SpotifySong> s) {
		for (int i = 0; i < s.size(); i += 1) {
			songs.add(s.get(i));
		}
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public static List<SpotifySong> getAllSongs() {
		return songs;
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public static List<SpotifySong> findSongForAlbum(String id) {
		try {
			return SpotifyClient.lookupAlbumTracks(id, (long) 0);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
}
