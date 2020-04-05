package com.nilfactor.activity4.data;

import java.util.ArrayList;
import java.util.List;

import com.nilfactor.activity4.model.User;

/**
 * import javax.ejb.Stateless;
 * import javax.persistence.EntityManager;
 * import javax.persistence.PersistenceContext;
 */

// @Stateless
public class UserService {
    /**
	 * @PersistenceContext
	 * private EntityManager db;
	 */
	private static User mockUser = new User((long) 1, "User", "Name", "uname@example.com", "user", "123", true);
	
	/* R operations of CRUD */
	public static User getById(Long id) {
	    /**
		 * try {
		 * 	return db.createQuery("FROM User WHERE user_id = :uid", User.class)
		 * 			.setParameter("uid", id)
		 * 			.getSingleResult();
		 * } catch (Exception e) {
		 * 	return new User();
		 * }
		 */
		if (id == mockUser.getUserId()) {
			return mockUser;
		}
		
		return new User();
	}
	
	public static User getByUsername(String username) {
	    /**
		 * try {
		 * 	return db.createQuery("FROM User WHERE usernmae = :uname", User.class)
		 * 			.setParameter("uname", username)
		 * 			.getSingleResult();
		 * } catch (Exception e) {
		 * 	return new User();
		 * }
		 */
		
		if (username.equals(mockUser.getUsername())) {
			return mockUser;
		}
		
		return new User();
	}
	
	public static User getByEmail(String email) {
	    /**
		 * try {
		 * 	return db.createQuery("FROM User WHERE email = :email", User.class)
		 * 			.setParameter("email", email)
		 * 			.getSingleResult();
		 * } catch (Exception e) {
		 * 	return new User();
		 * }
		 */
		
		if (email.equals(mockUser.getEmail())) {
			return mockUser;
		}
		
		return new User();
	}
	
	public static List<User> getUsers(long limit, long start) {
	    /**
		 * try {
		 * 	return db.createQuery("FROM User WHERE 1 ORDER BY user_id LIMIT :start, :limit", User.class)
		 * 			.setParameter("start", start)
		 * 			.setParameter("limit", limit)
		 * 			.getResultList();
		 * } catch (Exception e) {
		 * 	return new ArrayList<>();
		 * }
		 */
		List<User> users = new ArrayList<>();
		users.add(mockUser);
		return users;
	}
}
