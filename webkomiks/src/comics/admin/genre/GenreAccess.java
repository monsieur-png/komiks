package comics.admin.genre;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import comics.util.EMF;

public class GenreAccess {


	public Genre add(Genre genre) throws PersistenceException{
		EntityManager em = EMF.get().createEntityManager();
		EntityTransaction tx = null;
		try{
			tx = em.getTransaction();
			tx.begin();
			
			em.persist(genre);
			
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

		return genre;
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Genre> list() throws PersistenceException{
		EntityManager em = EMF.get().createEntityManager();
		List<Genre> list = null;
		try{
			Query q = em.createQuery("SELECT g FROM Genre g");
			list = new ArrayList<Genre>( q.getResultList() );

		} finally {
	        em.close();
	    }
		return list;
	}
	
	
	public Genre read(String genreName) throws PersistenceException{
		EntityManager em = EMF.get().createEntityManager();
		Genre genre = null;

		try{
			
			genre = em.find(Genre.class, genreName);
		} finally{
			em.close();
		}
		
		return genre;
	}
	
	
	public Genre update(Genre genre) throws PersistenceException{
		EntityManager em = EMF.get().createEntityManager();
		EntityTransaction tx = null;
		Genre genre1 = null;
		try{
			tx = em.getTransaction();
			tx.begin();

			genre1 = em.find(Genre.class, genre.getGenre());
			genre1.setGenre(genre.getGenre());
			genre1.setDescription(genre.getDescription());
			
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
				
		return genre1;
	}
	
	
	public void delete(String genreName) throws PersistenceException{
		EntityManager em = EMF.get().createEntityManager();
		EntityTransaction tx = null;
		try{
			tx = em.getTransaction();
			tx.begin();
			
			Genre genre = em.find(Genre.class, genreName);
			em.remove(genre);
			
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
	}
	
	
	@SuppressWarnings("unchecked")
	public static List<String> genreList() throws PersistenceException{
		EntityManager em = EMF.get().createEntityManager();
		List<String> list = null;

		try{
			Query q = em.createQuery("SELECT g.genre FROM Genre g");
			list = new ArrayList<String>( q.getResultList() );
			
		} finally{
			em.close();
		}

		return list;
	}
	
	
	//update count
	public Genre updateCount(Set<String> genreSet, int count) throws PersistenceException {
		EntityManager em = EMF.get().createEntityManager();
		EntityTransaction tx = null;
		Genre genre = null;
		try{
			
			for (String str: genreSet){
				tx = em.getTransaction();
				tx.begin();
				
				genre = em.find(Genre.class, str);
				genre.setCount( genre.getCount() + count);
				
				tx.commit();
			}
			
		}finally{
			try {
				if (tx != null && tx.isActive()) {
					tx.rollback();
				}
			} finally {
				em.close();
			}
		}
				
		return genre;
	}
	
	
	public boolean genreExists(String genreName) throws PersistenceException {
	    EntityManager em = EMF.get().createEntityManager();
	    
	    try {
	    	
	        return em.find(Genre.class, genreName) != null;
	    } finally {
	        em.close();
	    }
	}
	
	
}
