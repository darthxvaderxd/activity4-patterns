package com.nilfactor.activity3.data;

import java.util.ArrayList;
import java.util.List;

import com.nilfactor.activity3.model.SpotifyAlbum;
import com.nilfactor.activity3.util.SpotifyClient;

import javax.ejb.*;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class AlbumService {
	private static List<SpotifyAlbum> albums = new ArrayList<SpotifyAlbum>();
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public static int getAlbumCount() {
		return albums.size();
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public static void addAlbum(String id) {
		try {
			SpotifyAlbum album = SpotifyClient.lookupAlbum(id);
			albums.add(album);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public static void addAlbum(SpotifyAlbum album) {
		albums.add(album);
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public static void removeAlbum(SpotifyAlbum album) {
		albums.remove(album);
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public static SpotifyAlbum getAlbum(String id) {
		for (int i = 0; i < albums.size(); i += 1) {
			SpotifyAlbum album = albums.get(i);
			if (album.getId().equals(id)) {
				return album;
			}
		}
		 
		return null;
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public static List<SpotifyAlbum> getAllAlbums() {
		return albums;
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
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
