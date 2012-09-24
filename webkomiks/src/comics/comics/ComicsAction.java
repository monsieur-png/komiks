package comics.comics;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.struts2.interceptor.SessionAware;

import com.google.appengine.api.search.Cursor;
import com.google.appengine.api.search.Results;
import com.google.appengine.api.search.ScoredDocument;
import com.opensymphony.xwork2.ActionSupport;
import comics.admin.genre.GenreAccess;
import comics.search.SearchAccess;
import comics.util.BlobUtil;

public class ComicsAction extends ActionSupport implements SessionAware{
	
	private static final long serialVersionUID = 1L;
	private Map<String, Object> session;
	
	private Comics comics = null;
	private ArrayList<Comics> list = null;
	private String nextCursor = null;
	private String key = null;
	private String chapterKey = null;
	private Chapter chapter = null;
	private List<String> chKeys = null;					//chapter keys
	
	public Map<String, Object> getSession() {
		return session;
	}
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}
	
	public Comics getComics() {
		return comics;
	}
	public void setComics(Comics comics) {
		this.comics = comics;
	}

	public ArrayList<Comics> getList() {
		return list;
	}
	public void setList(ArrayList<Comics> list) {
		this.list = list;
	}

	public String getNextCursor() {
		return nextCursor;
	}

	public void setNextCursor(String nextCursor) {
		this.nextCursor = nextCursor;
	}
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}

	public String getChapterKey() {
		return chapterKey;
	}
	public void setChapterKey(String chapterKey) {
		this.chapterKey = chapterKey;
	}
	
	public Chapter getChapter() {
		return chapter;
	}
	public void setChapter(Chapter chapter) {
		this.chapter = chapter;
	}
		
	public List<String> getChKeys() {
		return chKeys;
	}
	public void setChKeys(List<String> chKeys) {
		this.chKeys = chKeys;
	}
	
	
	//list new comics
	public String list() throws Exception{

		SearchAccess access = new SearchAccess();
		Cursor cursor = null;
		Results<ScoredDocument> results = null;

		cursor = Cursor.newBuilder().build();
		results = access.searchComics(null, cursor);
		
		list = new ArrayList<Comics>();
		Comics comics = null;
		ComicsAccess comicsAccess = new ComicsAccess();
		for (ScoredDocument document : results) {
			comics = comicsAccess.read( document.getId() );
			list.add( comics );
		}
		
		cursor = results.getCursor();
		if (cursor != null)
			nextCursor = cursor.toWebSafeString();


		return SUCCESS;
	}
	
	
	//list new comics
	public String nextList() throws Exception{
		SearchAccess access = new SearchAccess();
		Cursor cursor = null;
		Results<ScoredDocument> results = null;

		cursor = Cursor.newBuilder().build( nextCursor );
		results = access.searchComics(null, cursor);
		
		list = new ArrayList<Comics>();
		Comics comics = null;
		ComicsAccess comicsAccess = new ComicsAccess();
		for (ScoredDocument document : results) {
			comics = comicsAccess.read(document.getId());
			comics.setCoverBlobKey(BlobUtil.servingUrl(comics.getCoverBlobKey()) + "=s700");
			list.add( comics );
		}
		
		cursor = results.getCursor();
		nextCursor = (cursor == null)? null : cursor.toWebSafeString();
			
		return SUCCESS;
	}
	
	
	public String read() throws Exception{
		ComicsAccess access = new ComicsAccess();
		
		comics = access.readComics(comics.getRandom(), comics.getUrl());
		access.addViews( comics.getKey() );

		if ( comics == null)
			return NONE;
		
		return SUCCESS;
	}
	
	
	//view, carousel
	public String readChapter() throws Exception{
		ComicsAccess access = new ComicsAccess();
	
		if ( chapterKey == null || chapterKey.equals("cover"))
			comics = access.read( key );
		else{
			comics = access.read( key );
			chapter = access.readChapter( chapterKey );
		}

		return SUCCESS;
	}
	
	
	public String myComics() throws Exception{
		String email = (String) session.get("email");
		if ( email == null)
			return LOGIN;
		
		ComicsAccess access = new ComicsAccess();
		list = access.listComics( email );

		return SUCCESS;
	}
	
	
	//view comics for update by author
	public String view() throws Exception{
		String email = (String) session.get("email");
		if ( email == null)
			return LOGIN;
	
		ComicsAccess access = new ComicsAccess();
		comics = access.read( key );
		if ( ! comics.getUser().getEmail().equals( email ))
			return NONE;
			
		
		return SUCCESS;
	}
	
	
	public String delete() throws Exception{
		String email = (String) session.get("email");
		if ( email == null)
			return LOGIN;

		ComicsAccess comics = new ComicsAccess();
		Set<String> genreSet = comics.delete( email, key );

		//update genre count
		GenreAccess genreAccess = new GenreAccess();
		genreAccess.updateCount(genreSet, - 1 );
    	
		return SUCCESS;
	}

	
	//save chapter order
	public String save() throws Exception{
		String email = (String) session.get("email");
		if ( email == null)
			return LOGIN;
		
		ComicsAccess access = new ComicsAccess();
		for (int i = 0; i < chKeys.size(); i++){
			access.updateChapterOrder(email, chKeys.get(i), i);
		}

		return SUCCESS;
	}
	
}
