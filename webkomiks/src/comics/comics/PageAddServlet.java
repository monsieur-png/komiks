package comics.comics;

import java.io.IOException;
import java.util.List;
import java.util.Map;

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


public class PageAddServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	private BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();

    public void doPost(HttpServletRequest req, HttpServletResponse res)
        throws ServletException, IOException {
    	

    	Map<String, List<BlobKey>> blobs = blobstoreService.getUploads(req);

    	List<BlobKey> listBlobKeys = blobs.get("page");
    	String chapterKey = req.getParameter("key");
    	

     	HttpSession session = req.getSession(false);
    	if( req.isRequestedSessionIdValid() )	{
                
                if ( session.getAttribute("email") == null){
                	//delete blobs
                	for (BlobKey blobKey: listBlobKeys){
                		blobstoreService.delete( blobKey );
                	}

                	res.sendRedirect("/comics/list");
                } else{
                	String email = session.getAttribute("email").toString();
                	String coverBlobStr = null;
                	BlobInfo blobInfo = null;
                	String contentType = null;
                	
                	//check file type of page uploaded
                	for ( BlobKey blobKey: listBlobKeys){
	                	blobInfo = new BlobInfoFactory().loadBlobInfo( blobKey );
	                	contentType = blobInfo.getContentType();
	            		if (contentType.equals("image/jpeg") || contentType.equals("image/png") 
	            				|| contentType.equals("image/gif" )){
	            			coverBlobStr = blobKey.getKeyString();
	            			
	            			ComicsAccess access = new ComicsAccess();
	            			Page page = new Page(coverBlobStr);
	            			access.addPage(email, page, chapterKey);
	
	            		}else{
	            			blobstoreService.delete( blobKey );
	            			coverBlobStr = null;
	            		}
                	}

            		res.sendRedirect("/chapter/update/" + chapterKey);
                }
    	}else{
	    	res.sendRedirect("/comics/list");
    	}
    }
}
