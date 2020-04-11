package com.nilfactor.activity4.data;

import java.util.ArrayList;
import java.util.List;

import com.nilfactor.activity4.model.SpotifyAlbum;
import com.nilfactor.activity4.model.SpotifySong;
import com.nilfactor.activity4.util.HibernateUtil;
import com.nilfactor.activity4.util.SpotifyClient;

import javax.ejb.*;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class SongService {
	private static List<SpotifySong> songs = getSongs();
	
	/* C of CRUD */
	public static void saveSong(SpotifySong song) {
		 Transaction transaction = null;
	     try {
	       	Session session = HibernateUtil.getSessionFactory().openSession();
	        			
	        // start a transaction
	        transaction = session.beginTransaction();
	        session.save(song);
	        
	        // commit transaction
	       transaction.commit();
	     } catch (Exception e) {
	    	 if (transaction != null) {
	    		 transaction.rollback();
	         }
	         e.printStackTrace();
	     }
	 }
	
	/* R of CRUD */
	@SuppressWarnings("unchecked")
	public static SpotifySong getById(String id) {
		Transaction transaction = null;
		 try {
			// start a transaction
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			transaction = session.beginTransaction();
			
			String hql = "select s from com.nilfactor.activity4.model.SpotifySong s where id = :id";
			List<SpotifySong> results = session.createQuery(hql)
					.setParameter("id", id)
					.list();
			
			transaction.commit();
			
			System.out.println("Size => " + results.size());
			
			if (results.size() > 0) {
				return results.get(0);
			}
	     } catch (Exception e) {
	    	 if (transaction != null) {
	    		 transaction.rollback();
	         }
	         e.printStackTrace();
	     }
		 
		 return null;
	}
	
	@SuppressWarnings("unchecked")
	public static List<SpotifySong> getSongsByAlbumId(String albumId) {
		Transaction transaction = null;
		 try {
			// start a transaction
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			transaction = session.beginTransaction();
			
			String hql = "select s from com.nilfactor.activity4.model.SpotifySong s where albumId = :albumId";
			List<SpotifySong> results = session.createQuery(hql)
					.setParameter("albumId", albumId)
					.list();
			
			transaction.commit();
			
			System.out.println("Size => " + results.size());
			
			if (results.size() > 0) {
				return results;
			}
	     } catch (Exception e) {
	    	 if (transaction != null) {
	    		 transaction.rollback();
	         }
	         e.printStackTrace();
	     }
		 
		 return null;
	}
	
	/* D of CRUD */
	public static void deleteSong(SpotifySong song) {
		Transaction transaction = null;
		 try {
			// start a transaction
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			transaction = session.beginTransaction();
			session.delete(song);
			transaction.commit();
			
	     } catch (Exception e) {
	    	 if (transaction != null) {
	    		 transaction.rollback();
	         }
	         e.printStackTrace();
	     }
	}
	
	@SuppressWarnings("unchecked")
	public static List<SpotifySong> getSongs() {
		 Transaction transaction = null;
		 try {
			 Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			 transaction = session.beginTransaction();
			 Query q = session.createQuery("select s from com.nilfactor.activity4.model.SpotifySong s");
			 List<SpotifySong> songs = q.list();
			 
			 transaction.commit();
			 return songs;
		 } catch (Exception e) {
			 if (transaction != null) {
	    		 transaction.rollback();
	         }
	         e.printStackTrace();
	     }
		 
		 return new ArrayList<SpotifySong>();
	}
	
	public static int getSongCount() {
		return songs.size();
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public static void addSong(SpotifySong song) {
		SpotifyAlbum album = song.getSpotifyAlbum();
		
		if (album == null) {
			song.setSpotifyAlbum(AlbumService.getAlbum(song.getAlbumId()));
		}
		saveSong(song);
		songs.add(song);
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public static void removeSong(SpotifySong song) {
		songs.remove(song);
		deleteSong(song);
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
