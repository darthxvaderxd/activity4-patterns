package com.nilfactor.activity4.data;

import java.util.ArrayList;
import java.util.List;

import com.nilfactor.activity4.model.SpotifyAlbum;
import com.nilfactor.activity4.model.SpotifySong;
import com.nilfactor.activity4.util.HibernateUtil;
import com.nilfactor.activity4.util.SpotifyClient;

import javax.annotation.PostConstruct;
import javax.ejb.*;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

@Dependent
@Named
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class AlbumService {
	private List<SpotifyAlbum> albums;
	@Inject private HibernateUtil hibernateUtil;
	@Inject private SpotifyClient spotifyClient;
	
	
	public AlbumService() {
		
	}
	
	@PostConstruct
	void init() {
		albums = getAlbums();
	}
	
	/* C of CRUD */
	public void saveAlbum(SpotifyAlbum album) {
		 Transaction transaction = null;
	     try {
	       	Session session = hibernateUtil.getSessionFactory().openSession();
	        			
	        // start a transaction
	        transaction = session.beginTransaction();
	        session.save(album);
	        
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
	public SpotifyAlbum getById(String id) {
		Transaction transaction = null;
		 try {
			// start a transaction
			Session session = hibernateUtil.getSessionFactory().getCurrentSession();
			transaction = session.beginTransaction();
			
			String hql = "select a from com.nilfactor.activity4.model.SpotifyAlbum a where albumId = :id";
			List<SpotifyAlbum> results = session.createQuery(hql)
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
	
	/* D of CRUD */
	public void deleteAlbum(SpotifyAlbum album) {
		Transaction transaction = null;
		 try {
			// start a transaction
			Session session = hibernateUtil.getSessionFactory().getCurrentSession();
			transaction = session.beginTransaction();
			session.delete(album);
			transaction.commit();
			
	     } catch (Exception e) {
	    	 if (transaction != null) {
	    		 transaction.rollback();
	         }
	         e.printStackTrace();
	     }
	}
	
	@SuppressWarnings("unchecked")
	public List<SpotifyAlbum> searchWildCardAlbum(String search) {
		Transaction transaction = null;
		 try {
			// start a transaction
			Session session = hibernateUtil.getSessionFactory().getCurrentSession();
			transaction = session.beginTransaction();
			
			Criteria criteria = session.createCriteria(SpotifyAlbum.class);
			Criterion artist = Restrictions.ilike("artists", search, MatchMode.ANYWHERE);
			Criterion name = Restrictions.ilike("name", search, MatchMode.ANYWHERE);
			
			List<SpotifyAlbum> results = criteria.add(Restrictions.or(artist, name))
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
	
	@SuppressWarnings("unchecked")
	public List<SpotifyAlbum> getAlbums() {
		 Transaction transaction = null;
		 try {
			 Session session = hibernateUtil.getSessionFactory().getCurrentSession();
			 transaction = session.beginTransaction();
			 Query q = session.createQuery("select a from com.nilfactor.activity4.model.SpotifyAlbum a");
			 List<SpotifyAlbum> albums = q.list();
			 
			 if (albums != null && albums.size() > 0) {
				 for (int i = 0; i < albums.size(); i += 1) {
					 SpotifyAlbum album = albums.get(i);
					 List<SpotifySong> sl = session.createQuery("select a from com.nilfactor.activity4.model.SpotifySong a where albumId = :albumId")
							 .setParameter("albumId", album.getAlbumId())
							 .list();
					 
					 if (sl != null && sl.size() > 0)
						 album.setSongs(sl);
				 }
			 }
			 
			 transaction.commit();
			 return albums;
		 } catch (Exception e) {
			 if (transaction != null) {
	    		 transaction.rollback();
	         }
	         e.printStackTrace();
	     }
		 
		 return new ArrayList<SpotifyAlbum>();
	}
	
	
	/* Utility functions to handle bl */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public int getAlbumCount() {
		return albums.size();
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void addAlbum(String id) {
		try {
			SpotifyAlbum album = spotifyClient.lookupAlbum(id);
			saveAlbum(album);
			albums.add(album);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void addAlbum(SpotifyAlbum album) {
		saveAlbum(album);
		albums.add(album);
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void removeAlbum(SpotifyAlbum album) {
		deleteAlbum(album);
		albums.remove(album);
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public SpotifyAlbum getAlbum(String id) {
		for (int i = 0; i < albums.size(); i += 1) {
			SpotifyAlbum album = albums.get(i);
			if (album.getAlbumId().equals(id)) {
				return album;
			}
		}
		 
		return null;
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<SpotifyAlbum> getAllAlbums() {
		return albums;
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<SpotifyAlbum> findAlbumsInService(String search) {
		try {
			return spotifyClient.lookupAlbums(search);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
}
