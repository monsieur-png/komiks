package comics.user;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import comics.util.EMF;

public class UserAccess {

	public User add(User user) throws PersistenceException{
		EntityManager em = EMF.get().createEntityManager();
		EntityTransaction tx = null;
		try{
			tx = em.getTransaction();
			tx.begin();
			
			em.persist(user);
			
			tx.commit();
		}finally{
			try {
				if (tx != null && tx.isActive()) {
					tx.rollback();
				}
			} finally {
				em.close();
			}
		}

		return user;
	}
	
	
	public User read(String email) throws PersistenceException{
		EntityManager em = EMF.get().createEntityManager();
		User user = null;
		try{
			user = em.find(User.class, email);
			
		}finally{
			em.close();
		}
		return user;
	}
	
	
	public User update(User user) throws PersistenceException{
		EntityManager em = EMF.get().createEntityManager();
		EntityTransaction tx = null;
		User userx = null;
		try{
			tx = em.getTransaction();
			tx.begin();

			userx = em.find(User.class, user.getEmail());
			userx.setUserName(user.getUserName());
			userx.setFullName(user.getFullName());
			userx.setAboutMySelf(user.getAboutMySelf());
			userx.setLastLogin( new Date() );
			
			tx.commit();
		}finally{
			try {
				if (tx != null && tx.isActive()) {
					tx.rollback();
				}
			} finally {
				em.close();
			}
		}
				
		return user;
	}
	
	
	public User updateLastLogin(String email) throws PersistenceException{
		EntityManager em = EMF.get().createEntityManager();
		EntityTransaction tx = null;
		User user = null;
		try{
			tx = em.getTransaction();
			tx.begin();

			user = em.find(User.class, email);
			user.setLastLogin( new Date() );
			
			tx.commit();
		}finally{
			try {
				if (tx != null && tx.isActive()) {
					tx.rollback();
				}
			} finally {
				em.close();
			}
		}
				
		return user;
	}
	
	
	public User searchUser(String userName) throws PersistenceException{
		EntityManager em = EMF.get().createEntityManager();
		User user = null;
		try{
			Query q = em.createNamedQuery("User.getUser");
			q.setParameter("userName", userName);
			user = (User) q.getSingleResult();
			
		} catch(NoResultException ex){
			user = null;
		} finally{
			em.close();	
		}
		 
		return user;
	}
	
	
	public boolean userNameExists(String userName, String email) throws PersistenceException {
	    EntityManager em = EMF.get().createEntityManager();
	    User user = null;
	    try {

	    	user = searchUser( userName );
	    	
	    	return ( user != null && ! user.getEmail().equals( email ) );
	    } finally {
	        em.close();
	    }
	}
	
}
