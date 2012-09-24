package comics.comics;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.apache.commons.beanutils.BeanUtils;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import comics.search.Indexes;
import comics.user.User;
import comics.util.BlobUtil;
import comics.util.EMF;


public class ComicsAccess {

	public Comics add(Comics comics, String email) throws PersistenceException{
		EntityManager em = EMF.get().createEntityManager();
		EntityTransaction tx = null;
		User user = null;
		try{
			tx = em.getTransaction();
			tx.begin();
			
			user = em.find(User.class, email);
			comics.setUser(user);
			
			em.persist(comics);
			
			//increment comics count
			int count = user.getComicsCount();
			user.setComicsCount( count + 1 );
			
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

		return comics;
	}
	
	
	public Comics update(Comics comics, String email) throws PersistenceException{
		EntityManager em = EMF.get().createEntityManager();
		EntityTransaction tx = null;
		Comics emComics = null;
		try{
			tx = em.getTransaction();
			tx.begin();
			
			emComics = em.find(Comics.class, comics.getKey());

			if ( emComics.getUser().getEmail().equals( email ) ){
				emComics.setTitle( comics.getTitle() );
				emComics.setDescription( comics.getDescription() );
				emComics.setDateLastUpdated( new Date() );
				emComics.setUrl( comics.getUrl() );
				emComics.setGenre( comics.getGenre() );
				emComics.setStatus( comics.getStatus() );
				
				
				if ( comics.getCoverBlobKey() != null ){
					emComics.setCoverBlobKey( comics.getCoverBlobKey());
				}
				
				Indexes index = new Indexes();
				index.indexComics(emComics, email);
			}
			
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

		return emComics;
	}
	
	
	public Comics read(String key) throws IllegalAccessException, InvocationTargetException{
		EntityManager em = EMF.get().createEntityManager();
		Comics comics = new Comics();
		try{
			Comics emComics = em.find(Comics.class, KeyFactory.stringToKey(key));
			BeanUtils.copyProperties(comics, emComics);
			BeanUtils.copyProperties(comics.getUser(), emComics.getUser());
			comics.setChapters(new ArrayList<Chapter> (emComics.getChapters()));
			
		}finally{
			em.close();
		}
		return comics;
	}
	
	
	//read comics given with SEO friendly url and comics random number
	public Comics readComics(int random, String url) throws IllegalAccessException, InvocationTargetException  {
		EntityManager em = EMF.get().createEntityManager();
		Comics comics = new Comics();

		try{
			Query q = em.createNamedQuery("Comics.getComics");
			q.setParameter("random", random);
			q.setParameter("url", url);
	
			Comics emComics = (Comics) q.getSingleResult();
			BeanUtils.copyProperties(comics, emComics);
			BeanUtils.copyProperties(comics.getUser(), emComics.getUser());
			comics.setChapters(new ArrayList<Chapter>(emComics.getChapters()));

		} catch(NoResultException ex){
			comics = null;
		} finally{
			em.close();
		}

		return comics;
	}
	
	
	public ArrayList<Comics> listComics(String email) throws PersistenceException{
		EntityManager em = EMF.get().createEntityManager();
		ArrayList<Comics> list = null;
		
		try{
			User user = em.find(User.class, email);
			list = new ArrayList<Comics>(user.getComics());
			
		}finally{
			em.close();
		}
		
		return list;
	}
	
	
	public Chapter addChapter(String email, Chapter chapter, String key) throws PersistenceException{
		EntityManager em = EMF.get().createEntityManager();
		EntityTransaction tx = null;
		Comics comics = null;
		try{
			tx = em.getTransaction();
			tx.begin();
			
			comics = em.find(Comics.class, KeyFactory.stringToKey(key));

			if ( comics.getUser().getEmail().equals( email ) ){
				//chapter order
				int chapterCount = comics.getChapterCount();
				if ( chapterCount != 0 ){
					int order = comics.getChapters().get( chapterCount - 1 ).getOrder() + 1;
					chapter.setOrder( order );
				}
				
				chapter.setComics(comics);
				comics.setChapterCount( chapterCount + 1 );
				
				em.persist(chapter);
			}

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

		return chapter;
	}
	
	
	public Chapter readChapter(String key) throws IllegalAccessException, InvocationTargetException {
		EntityManager em = EMF.get().createEntityManager();
		Chapter chapter = new Chapter();
		try{
			
			Chapter ch = em.find(Chapter.class, KeyFactory.stringToKey(key));

			BeanUtils.copyProperties(chapter, ch);
			BeanUtils.copyProperties(chapter.getComics(), ch.getComics());
			chapter.setPages(new ArrayList<Page>(ch.getPages()));

		} finally{
			em.close();
		}

		return chapter;
	}
	
	
	public Page addPage(String email, Page page, String key) throws PersistenceException {
		EntityManager em = EMF.get().createEntityManager();
		EntityTransaction tx = null;
		Chapter chapter = null;
		try{
			tx = em.getTransaction();
			tx.begin();
			
			chapter = em.find(Chapter.class, KeyFactory.stringToKey(key));
			if ( chapter.getComics().getUser().getEmail().equals( email ) ){
				//page order
				int pageCount = chapter.getPageCount();
				if ( pageCount != 0 ){
					int order = chapter.getPages().get( pageCount - 1 ).getOrder() + 1;
					page.setOrder( order );
				}
				
				page.setChapter(chapter);
				chapter.setPageCount( pageCount + 1);
				em.persist(page);
			}
			
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

		return page;
	}
	
	
	public void addViews(Key key) throws PersistenceException{
		EntityManager em = EMF.get().createEntityManager();
		EntityTransaction tx = null;
		Comics comics = null;
		try{
			tx = em.getTransaction();
			tx.begin();
			
			comics = em.find(Comics.class, key);
			comics.setViews(comics.getViews() + 1 );
			
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
	
	
	public void saveChapter(String email, String key, List<String> pagesKey, List<String> removedPageBlobKeys) throws PersistenceException{
		EntityManager em = EMF.get().createEntityManager();
		EntityTransaction tx = null;
		Chapter chapter = null;
		try{
			tx = em.getTransaction();
			tx.begin();
			
			chapter = em.find(Chapter.class, KeyFactory.stringToKey(key) );
			String ownerEmail = new String( chapter.getComics().getUser().getEmail() );
			if ( ownerEmail.equals( email ) ){			
				chapter.setPageCount( pagesKey.size() );
			}
			
			tx.commit();

			if ( ownerEmail.equals( email ) ){		
				for (int i=0; i < pagesKey.size(); i++){
					updatePage(pagesKey.get(i), i);
				}
				
				for (String pageBlobKey: removedPageBlobKeys){
					deletePage( pageBlobKey );
					BlobUtil.deleteBlob( pageBlobKey );
				}
				
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
	}
	
	
	//delete a chapter
	public String deleteChapter(String email, String key) throws PersistenceException{
		EntityManager em = EMF.get().createEntityManager();
		EntityTransaction tx = null;
		try{
			tx = em.getTransaction();
			tx.begin();
			
			Chapter chapter = em.find(Chapter.class, KeyFactory.stringToKey(key) );
			if (chapter.getComics().getUser().getEmail().equals( email) ){
				key = KeyFactory.keyToString(chapter.getComics().getKey());
				chapter.getComics().setChapterCount( chapter.getComics().getChapterCount() - 1);
				em.remove(chapter);
				
				BlobUtil.deleteBlobs( chapter.getPages() );
			}
			
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
		
		return key;
	}
	
	
	public Chapter updateChapter(String email, String key, String title) throws PersistenceException {
		EntityManager em = EMF.get().createEntityManager();
		EntityTransaction tx = null;
		Chapter chapter = null;
		try{
			tx = em.getTransaction();
			tx.begin();

			chapter = em.find(Chapter.class, KeyFactory.stringToKey(key) );
			if ( chapter.getComics().getUser().getEmail().equals( email ) )
				chapter.setTitle( title );
			
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
				
		return chapter;
	}
	
	
	public Chapter updateChapterOrder(String email, String key, int order) throws PersistenceException {
		EntityManager em = EMF.get().createEntityManager();
		EntityTransaction tx = null;
		Chapter chapter = null;
		try{
			tx = em.getTransaction();
			tx.begin();

			chapter = em.find(Chapter.class, KeyFactory.stringToKey(key) );
			if ( chapter.getComics().getUser().getEmail().equals( email ) )
				chapter.setOrder( order );
			
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
				
		return chapter;
	}
	
	
	//delete a comics
	//return genrae for updating genre counts
	public Set<String> delete(String email, String key) throws PersistenceException{
		EntityManager em = EMF.get().createEntityManager();
		EntityTransaction tx = null;
		Set<String> genreSet = new HashSet<String>();
		try{
			tx = em.getTransaction();
			tx.begin();
			
			Comics comics = em.find(Comics.class, KeyFactory.stringToKey(key) );
			if ( comics.getUser().getEmail().equals( email )){
				genreSet.addAll( comics.getGenre() );
				em.remove(comics);
				
				Indexes index = new Indexes();
				index.removeIndex( Indexes.COMICS_INDEX, key );
				
				//decrement comics count
				int count = comics.getUser().getComicsCount();
				comics.getUser().setComicsCount( count - 1);
	
				//delete coverBlobKey
				BlobUtil.deleteBlob(comics.getCoverBlobKey());
				
				//delete pageBlobKeys
				for(Chapter ch: comics.getChapters()){
					BlobUtil.deleteBlobs( ch.getPages() );
				}
			}

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
		
		return genreSet;
	}
	
	
	public Page updatePage(String pageBlobKey, int order) throws PersistenceException{
		EntityManager em = EMF.get().createEntityManager();
		EntityTransaction tx = null;
		Page emPage = null;
		try{
			tx = em.getTransaction();
			tx.begin();
			
			emPage = em.find(Page.class, KeyFactory.stringToKey(pageBlobKey) );
			emPage.setOrder(order);
			
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

		return emPage;
	}
	
	
	public boolean deletePage(String pageBlobKey) throws NoResultException {
		EntityManager em = EMF.get().createEntityManager();
		EntityTransaction tx = null;

		int count = 0;
		try{
			tx = em.getTransaction();
			tx.begin();
			
			Query q = em.createQuery("DELETE FROM Page p WHERE p.pageBlobKey = :pbk");
			q.setParameter("pbk", pageBlobKey);
			count = q.executeUpdate();

			tx.commit();
			
		} finally{
			try {
				if (tx != null && tx.isActive()) {
					tx.rollback();
				}
			} finally {
				em.close();
			}
		}

		return count > 0;
	}
	
	
}
