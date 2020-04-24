package com.nilfactor.activity4.data;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;
import javax.interceptor.Interceptors;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.nilfactor.activity4.model.User;
import com.nilfactor.activity4.util.HibernateUtil;
import com.nilfactor.activity4.util.LogInterceptor;

@Dependent
@Named
@Stateless
@Interceptors(LogInterceptor.class)
public class UserService {
	@Inject private HibernateUtil hibernateUtil;
	
	public UserService() {
		
	}
	
	/* C of CRUD */
	@Interceptors(LogInterceptor.class)
	public int registerUser(String username, String password, String firstName, String lastName, String email) {
		User userExists = getByUsername(username);
		
		if (userExists == null) {
			User user = new User();
			user.setUsername(username);
			user.setPassword(password);
			user.setFirstName(firstName);
			user.setLastName(lastName);
			user.setEmail(email);
			saveUser(user);
			
			User doesUserExistNow = getByUsername(username);
			
			if (doesUserExistNow == null) {
				return -1;
			}
			
			return 1;
		}
		
		return 0;
	}
	
	@Interceptors(LogInterceptor.class)
	public void saveUser(User user) {
		 Transaction transaction = null;
	     try {
	       	Session session = hibernateUtil.getSessionFactory().openSession();
	        			
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
	@SuppressWarnings("unchecked")
	@Interceptors(LogInterceptor.class)
	public User getByUsername(String username) {
		Transaction transaction = null;
		 try {
			// start a transaction
			Session session = hibernateUtil.getSessionFactory().getCurrentSession();
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
		 
		 return null;
	}
	
	@SuppressWarnings("unchecked")
	@Interceptors(LogInterceptor.class)
	public List<User> getUsers() {
		 Transaction transaction = null;
		 try {
			 Session session = hibernateUtil.getSessionFactory().getCurrentSession();
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
