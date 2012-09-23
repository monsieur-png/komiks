package comics.admin;

import java.util.ArrayList;
import java.util.List;

import com.google.appengine.api.search.Document;
import com.google.appengine.api.search.ListRequest;
import com.google.appengine.api.search.ListResponse;
import com.opensymphony.xwork2.ActionSupport;
import comics.search.Indexes;

public class AdminConsole extends ActionSupport{

	private static final long serialVersionUID = 1L;


	public String resetFTS(){
		List<String> docIds = new ArrayList<String>();
        ListRequest request = ListRequest.newBuilder().build();		  // Return a set of document IDs.
        
        Indexes indexes = new Indexes();
        ListResponse<Document> response = indexes.getIndex(Indexes.COMICS_INDEX).listDocuments(request);
        for (Document doc : response) {
            docIds.add(doc.getId());
        }
        
        indexes.getIndex(Indexes.COMICS_INDEX).remove(docIds);
		return SUCCESS;
	}
	
}
