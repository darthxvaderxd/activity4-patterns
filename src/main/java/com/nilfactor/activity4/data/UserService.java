package com.nilfactor.activity4.data;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.nilfactor.activity4.model.User;
import com.nilfactor.activity4.util.HibernateUtil;

// @Stateless
public class UserService {
	/* C of CRUD */
	public static void saveUserEntity(User user) {
		 Transaction transaction = null;
	     try {
	       	Session session = HibernateUtil.getSessionFactory().openSession();
	        			
	        // start a transaction
	        transaction = session.beginTransaction();
	        session.save(user);
	        
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
	public static User getById(Long id) {
	    
		return new User();
	}
	
	@SuppressWarnings("unchecked")
	public static User getByUsername(String username) {
		Transaction transaction = null;
		 try {
			// start a transaction
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			transaction = session.beginTransaction();
			
			String hql = "select u from com.nilfactor.activity4.model.User u where username = :username";
			List<User> results = session.createQuery(hql)
					.setParameter("username", username)
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
		 return new User();
	}
	
	@SuppressWarnings("unchecked")
	public static List<User> getUsers(long limit, long start) {
		 Transaction transaction = null;
		 try {
			 Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			 transaction = session.beginTransaction();
			 Query q = session.createQuery("select u from com.nilfactor.activity4.model.User u");
			 List<User> users = q.list();
			 
			 transaction.commit();
			 return users;
		 } catch (Exception e) {
			 if (transaction != null) {
	    		 transaction.rollback();
	         }
	         e.printStackTrace();
	     }
		 
		 return new ArrayList<User>();
	}
}
