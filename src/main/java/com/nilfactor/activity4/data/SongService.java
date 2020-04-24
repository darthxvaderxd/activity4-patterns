package com.nilfactor.activity4.data;

import java.util.ArrayList;
import java.util.List;

import com.nilfactor.activity4.model.SpotifyAlbum;
import com.nilfactor.activity4.model.SpotifySong;
import com.nilfactor.activity4.util.HibernateUtil;
import com.nilfactor.activity4.util.LogInterceptor;
import com.nilfactor.activity4.util.ServiceService;
import com.nilfactor.activity4.util.SpotifyClient;

import javax.annotation.PostConstruct;
import javax.ejb.*;
import javax.enterprise.context.Dependent;
import javax.inject.Named;
import javax.interceptor.Interceptors;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

@Dependent
@Named
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
@Interceptors(LogInterceptor.class)
public class SongService {
	private List<SpotifySong> songs;
	private SpotifyClient spotifyClient = ServiceService.getSpotifyClient();
	private AlbumService albumService = ServiceService.getAlbumService();
	private HibernateUtil hibernateUtil = ServiceService.getHibernateUtil();
	
	public SongService() {
		
	}
	
	@Interceptors(LogInterceptor.class)
	@PostConstruct
	public void init() {
		songs = getSongs();
	}
	
	/* C of CRUD */
	@Interceptors(LogInterceptor.class)
	public void saveSong(SpotifySong song) {
		 Transaction transaction = null;
	     try {
	       	Session session = hibernateUtil.getSessionFactory().openSession();
	        			
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
	@Interceptors(LogInterceptor.class)
	@SuppressWarnings("unchecked")
	public SpotifySong getById(String id) {
		Transaction transaction = null;
		 try {
			// start a transaction
			Session session = hibernateUtil.getSessionFactory().getCurrentSession();
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
	
	@Interceptors(LogInterceptor.class)
	@SuppressWarnings("unchecked")
	public List<SpotifySong> getSongsByAlbumId(String albumId) {
		Transaction transaction = null;
		 try {
			// start a transaction
			Session session = hibernateUtil.getSessionFactory().getCurrentSession();
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
	@Interceptors(LogInterceptor.class)
	public void deleteSong(SpotifySong song) {
		Transaction transaction = null;
		 try {
			// start a transaction
			Session session = hibernateUtil.getSessionFactory().getCurrentSession();
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
	@Interceptors(LogInterceptor.class)
	public List<SpotifySong> getSongs() {
		 Transaction transaction = null;
		 try {
			 Session session = hibernateUtil.getSessionFactory().getCurrentSession();
			 transaction = session.beginTransaction();
			 Query q = session.createQuery("select s from com.nilfactor.activity4.model.SpotifySong s");
			 List<SpotifySong> songs = q.list();
			 
			 if (songs != null && songs.size() > 0) {
				 for (int i = 0; i < songs.size(); i += 1) {
					 SpotifySong song = songs.get(i);
					 List<SpotifyAlbum> al = session.createQuery("select a from com.nilfactor.activity4.model.SpotifyAlbum a where albumId = :albumId")
							 .setParameter("albumId", song.getAlbumId())
							 .list();
					 
					 if (al != null && al.size() > 0)
						 song.setSpotifyAlbum(al.get(0));
				 }
			 }
			 
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
	
	@Interceptors(LogInterceptor.class)
	public int getSongCount() {
		return songs.size();
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	@Interceptors(LogInterceptor.class)
	public void addSong(SpotifySong song) {
		SpotifyAlbum album = song.getSpotifyAlbum();
		
		if (album == null) {
			if (albumService == null) {
				albumService = new AlbumService();
			}
			song.setSpotifyAlbum(albumService.getAlbum(song.getAlbumId()));
		}
		saveSong(song);
		songs.add(song);
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	@Interceptors(LogInterceptor.class)
	public void removeSong(SpotifySong song) {
		songs.remove(song);
		deleteSong(song);
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	@Interceptors(LogInterceptor.class)
	public SpotifySong getSong(String id) {
		for (int i = 0; i < songs.size(); i += 1) {
			SpotifySong song = songs.get(i);
			if (song.getId().equals(id)) {
				return song;
			}
		}
		
		return null;
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	@Interceptors(LogInterceptor.class)
	public void addSongs(List<SpotifySong> s) {
		for (int i = 0; i < s.size(); i += 1) {
			songs.add(s.get(i));
		}
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	@Interceptors(LogInterceptor.class)
	public List<SpotifySong> getAllSongs() {
		return songs;
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	@Interceptors(LogInterceptor.class)
	public List<SpotifySong> findSongForAlbum(String id) {
		try {
			return spotifyClient.lookupAlbumTracks(id, (long) 0);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
}
