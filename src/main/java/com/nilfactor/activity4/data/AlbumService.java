package com.nilfactor.activity4.data;

import java.util.ArrayList;
import java.util.List;

import com.nilfactor.activity4.model.SpotifyAlbum;
import com.nilfactor.activity4.util.HibernateUtil;
import com.nilfactor.activity4.util.SpotifyClient;

import javax.ejb.*;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class AlbumService {
	private static List<SpotifyAlbum> albums = getAlbums();
	
	/* C of CRUD */
	public static void saveAlbum(SpotifyAlbum album) {
		 Transaction transaction = null;
	     try {
	       	Session session = HibernateUtil.getSessionFactory().openSession();
	        			
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
	public static SpotifyAlbum getById(String id) {
		Transaction transaction = null;
		 try {
			// start a transaction
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
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
	public static void deleteAlbum(SpotifyAlbum album) {
		Transaction transaction = null;
		 try {
			// start a transaction
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
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
	public static List<SpotifyAlbum> searchWildCardAlbum(String search) {
		Transaction transaction = null;
		 try {
			// start a transaction
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
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
	public static List<SpotifyAlbum> getAlbums() {
		 Transaction transaction = null;
		 try {
			 Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			 transaction = session.beginTransaction();
			 Query q = session.createQuery("select a from com.nilfactor.activity4.model.SpotifyAlbum a");
			 List<SpotifyAlbum> albums = q.list();
			 
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
	public static int getAlbumCount() {
		return albums.size();
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public static void addAlbum(String id) {
		try {
			SpotifyAlbum album = SpotifyClient.lookupAlbum(id);
			saveAlbum(album);
			albums.add(album);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public static void addAlbum(SpotifyAlbum album) {
		saveAlbum(album);
		albums.add(album);
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public static void removeAlbum(SpotifyAlbum album) {
		deleteAlbum(album);
		albums.remove(album);
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public static SpotifyAlbum getAlbum(String id) {
		for (int i = 0; i < albums.size(); i += 1) {
			SpotifyAlbum album = albums.get(i);
			if (album.getAlbumId().equals(id)) {
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
