package com.nilfactor.activity3.data;

import java.util.ArrayList;
import java.util.List;

import com.nilfactor.activity3.model.SpotifySong;
import com.nilfactor.activity3.util.SpotifyClient;

public class SongService {
	private static List<SpotifySong> songs = new ArrayList<SpotifySong>();
	
	public static void addSong(SpotifySong song) {
		songs.add(song);
	}
	
	public static void removeSong(SpotifySong song) {
		songs.remove(song);
	}
	
	public static SpotifySong getSong(String id) {
		for (int i = 0; i < songs.size(); i += 1) {
			SpotifySong song = songs.get(i);
			if (song.getId().equals(id)) {
				return song;
			}
		}
		
		return null;
	}
	
	public static void addSongs(List<SpotifySong> s) {
		for (int i = 0; i < s.size(); i += 1) {
			songs.add(s.get(i));
		}
	}
	
	public static List<SpotifySong> getAllSongs() {
		return songs;
	}
	
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
