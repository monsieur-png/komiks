package comics.comics;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;
import comics.util.BlobUtil;

public class ChapterAction extends ActionSupport implements SessionAware{

	private static final long serialVersionUID = 1L;
	private Map<String, Object> session;
	
	private String key = null;						//comics key
	private Chapter chapter = null;
	private List<String> pageBlobKeys = null;
	private List<String> allPageBlobKeys = null;
	
	public Map<String, Object> getSession() {
		return session;
	}
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}
	
	public Chapter getChapter() {
		return chapter;
	}
	public void setChapter(Chapter chapter) {
		this.chapter = chapter;
	}
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	
	public List<String> getPageBlobKeys() {
		return pageBlobKeys;
	}
	public void setPageBlobKeys(List<String> pageBlobKeys) {
		this.pageBlobKeys = pageBlobKeys;
	}
	
	public List<String> getAllPageBlobKeys() {
		return allPageBlobKeys;
	}
	public void setAllPageBlobKeys(List<String> allPageBlobKeys) {
		this.allPageBlobKeys = allPageBlobKeys;
	}
	
	
	public String add(){
		String email = (String) session.get("email");
		if ( email == null)
			return LOGIN; 

		ComicsAccess access = new ComicsAccess();
		access.addChapter(email, chapter, key);
		
		return SUCCESS;
	}
	
	
	//view a chapter for update
	public String view(){
		String email = (String) session.get("email");
		if ( email == null)
			return LOGIN;
		
		ComicsAccess access = new ComicsAccess();
		try {
			chapter = access.readChapter(key);
			if ( ! chapter.getComics().getUser().getEmail().equals( email ) )
				return NONE;
			
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return SUCCESS;
	}
	
	
	public String save(){
		String email = (String) session.get("email");
		if ( email == null)
			return LOGIN;
		
		ComicsAccess access = new ComicsAccess();
		access.saveChapter(email, key, pageBlobKeys);
		
		//remove blob of deleted pages
		allPageBlobKeys.removeAll( pageBlobKeys );
		for( String blobKey: allPageBlobKeys )
			BlobUtil.deleteBlob( blobKey );

		
		return SUCCESS;
	}
	
	
	public String delete(){
		String email = (String) session.get("email");
		if ( email == null)
			return LOGIN;
		
		ComicsAccess access = new ComicsAccess();
		key = access.deleteChapter(email, key );
		
		return SUCCESS;
	}
	
	
	public String update(){
		String email = (String) session.get("email");
		if ( email == null)
			return LOGIN;
		
		ComicsAccess access = new ComicsAccess();
		access.updateChapter(email, key, chapter.getTitle());
		
		
		return SUCCESS;
	}
	
}
