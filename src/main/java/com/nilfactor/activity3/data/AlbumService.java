package com.nilfactor.activity3.data;

import java.util.ArrayList;
import java.util.List;

import com.nilfactor.activity3.model.SpotifyAlbum;
import com.nilfactor.activity3.util.SpotifyClient;

public class AlbumService {
	private static List<SpotifyAlbum> albums = new ArrayList<SpotifyAlbum>();
	
	public static void addAlbum(SpotifyAlbum album) {
		albums.add(album);
	}
	
	public static void removeAlbum(SpotifyAlbum album) {
		albums.remove(album);
	}
	
	public static SpotifyAlbum getAlbum(String id) {
		for (int i = 0; i < albums.size(); i += 1) {
			SpotifyAlbum album = albums.get(i);
			if (album.getId().equals(id)) {
				return album;
			}
		}
		 
		return null;
	}
	
	public static List<SpotifyAlbum> getAllAlbums() {
		return albums;
	}
	
	public static List<SpotifyAlbum> findAlbumsInService(String search) {
		try {
			return SpotifyClient.lookupAlbums(search);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
}
