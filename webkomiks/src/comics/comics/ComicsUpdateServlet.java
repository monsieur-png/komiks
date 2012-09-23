package comics.comics;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.appengine.api.blobstore.BlobInfo;
import com.google.appengine.api.blobstore.BlobInfoFactory;
import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.datastore.KeyFactory;

import comics.admin.genre.GenreAccess;
import comics.search.Indexes;


public class ComicsUpdateServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	private BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();

    public void doPost(HttpServletRequest req, HttpServletResponse res)
        throws ServletException, IOException {
    	
    	Set<String> errorMessages = new HashSet<String>();
    	Map<String, List<BlobKey>> blobs = blobstoreService.getUploads(req);

    	BlobKey coverBlobKey = null;
    	try{
    		coverBlobKey = blobs.get("cover").get(0);
    	} catch(NullPointerException ex){
    		coverBlobKey = null;
    	}
    
    	
     	HttpSession session = req.getSession(false);
    	if( req.isRequestedSessionIdValid() )	{
                
                if ( session.getAttribute("email") == null){
                	//delete blobs
                	blobstoreService.delete( coverBlobKey );

                	res.sendRedirect("/comics/list");
                } else{
                	String email = session.getAttribute("email").toString();
                	
                	String title = req.getParameter("title");
                	String description = req.getParameter("description");
                	String[] oldGenre = req.getParameterValues("oldGenre");
                	String[] genre = req.getParameterValues("genre");
                	String key = req.getParameter("key");
                	
                	//validation
                	if ( title.equals("") )
                		errorMessages.add("Title is required.");
                	if ( genre == null )
                		errorMessages.add("Genre is required.");
                	
                	String coverBlobStr = null;
                	//check file type of cover photo
                	if ( coverBlobKey != null ){
	                	BlobInfo blobInfo = new BlobInfoFactory().loadBlobInfo( coverBlobKey );
	                	String contentType = blobInfo.getContentType();
	            		if (contentType.equals("image/jpeg") || contentType.equals("image/png") 
	            				|| contentType.equals("image/gif" )){
	            			coverBlobStr = coverBlobKey.getKeyString();
	            		}else{
	            			blobstoreService.delete( coverBlobKey );
	            			coverBlobStr = null;
	            		}
            		}
                	
                	if ( errorMessages.size() != 0 ){
                		
                		res.sendRedirect("/comics/update/" + key);
                	}else{
   
	                	Comics comics = new Comics(title, description, coverBlobStr);
	                	comics.setKey( KeyFactory.stringToKey( key ));
	                	//genre
	                	Set<String> genreSet = new HashSet<String>();
	                	Collections.addAll(genreSet, genre);
	                	comics.setGenre(genreSet);

	                	ComicsAccess access = new ComicsAccess();
	                	comics = access.update( comics, email );
	                	
	                	if ( comics.getUser().getEmail().equals( email )){
		                	Set<String> oldGenreSet = new HashSet<String>();
		                	Collections.addAll(oldGenreSet, oldGenre);
		                	
		                	//increment count for new genre added
		                	Set<String> temp = new HashSet<String>();
		                	temp.addAll(genreSet);
		                	temp.removeAll(oldGenreSet);
		                	GenreAccess genreAccess = new GenreAccess();
		                	genreAccess.updateCount(temp, 1);
		                	
		                	//decrement count for genre deleted
		                	temp = new HashSet<String>();
		                	temp.addAll(oldGenreSet);
		                	temp.removeAll(genreSet);
		                	genreAccess.updateCount(temp, -1);
		                	
		                	//index for full text search
		            		Indexes index = new Indexes();
		            		index.indexComics( comics, email );
	                	}
	            		//key = KeyFactory.keyToString(comics.getKey());
	            		res.sendRedirect("/comics/update/" + key);
                	}
                }
    	}else{
	    	res.sendRedirect("/comics/list");
    	}
    }
}
