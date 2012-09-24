package comics.comics;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;


public class ChapterAction extends ActionSupport implements SessionAware{

	private static final long serialVersionUID = 1L;
	private Map<String, Object> session;
	
	private String key = null;						//comics key
	private Chapter chapter = null;
	private List<String> pageBlobKeys = null;
	private List<String> allPageBlobKeys = null;
	private List<String> pageKeys = null;
	
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
		
	public List<String> getPageKeys() {
		return pageKeys;
	}
	public void setPageKeys(List<String> pageKeys) {
		this.pageKeys = pageKeys;
	}
	
	
	public String add() throws Exception{
		String email = (String) session.get("email");
		if ( email == null)
			return LOGIN; 

		ComicsAccess access = new ComicsAccess();
		access.addChapter(email, chapter, key);
		
		return SUCCESS;
	}
	
	
	//view a chapter for update
	public String view() throws Exception{
		String email = (String) session.get("email");
		if ( email == null)
			return LOGIN;
		
		ComicsAccess access = new ComicsAccess();

		chapter = access.readChapter(key);
		if ( ! chapter.getComics().getUser().getEmail().equals( email ) )
			return NONE;
			
		return SUCCESS;
	}
	
	
	public String save() throws Exception{
		String email = (String) session.get("email");
		if ( email == null)
			return LOGIN;

		//if all pages were removed
		if ( pageBlobKeys == null ){
			pageBlobKeys = new ArrayList<String>();
			pageKeys = new ArrayList<String>();
		}

		ComicsAccess access = new ComicsAccess();
		allPageBlobKeys.removeAll( pageBlobKeys );
		access.saveChapter(email, key, pageKeys, allPageBlobKeys);

		return SUCCESS;
	}
	
	
	public String delete() throws Exception{
		String email = (String) session.get("email");
		if ( email == null)
			return LOGIN;
		
		ComicsAccess access = new ComicsAccess();
		key = access.deleteChapter(email, key );
		
		return SUCCESS;
	}
	
	
	public String update() throws Exception{
		String email = (String) session.get("email");
		if ( email == null)
			return LOGIN;
		
		ComicsAccess access = new ComicsAccess();
		access.updateChapter(email, key, chapter.getTitle());
		
		return SUCCESS;
	}
	
}
