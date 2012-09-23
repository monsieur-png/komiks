package comics.channel;

import java.util.ArrayList;

import com.google.appengine.api.search.Cursor;
import com.google.appengine.api.search.Results;
import com.google.appengine.api.search.ScoredDocument;
import com.opensymphony.xwork2.ActionSupport;
import comics.comics.Comics;
import comics.comics.ComicsAccess;
import comics.search.SearchAccess;
import comics.user.User;
import comics.user.UserAccess;
import comics.util.BlobUtil;

public class ChannelAction extends ActionSupport{

	private static final long serialVersionUID = 1L;
	
	private Comics comics = null;
	private ArrayList<Comics> list = null;
	private String nextCursor = null;
	private User user = null;
	private String userName = null;
	
	
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
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	
	//list of comics by a user
	public String execute(){
		UserAccess access = new UserAccess();
		user = access.searchUser( userName );
		SearchAccess searchAccess = new SearchAccess();
		Cursor cursor = null;
		Results<ScoredDocument> results = null;

		try{
			cursor = Cursor.newBuilder().build();
			results = searchAccess.searchComicsByUser(user.getEmail(), cursor);
			
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
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		
		return SUCCESS;
	}
	
	
	//next list of comics by a user
	public String nextList(){
		UserAccess access = new UserAccess();
		user = access.searchUser(userName);
		SearchAccess searchAccess = new SearchAccess();
		Cursor cursor = null;
		Results<ScoredDocument> results = null;

		try{
			cursor = Cursor.newBuilder().build( nextCursor );
			results = searchAccess.searchComicsByUser(user.getEmail(), cursor);
			
			list = new ArrayList<Comics>();
			Comics comics = null;
			ComicsAccess comicsAccess = new ComicsAccess();
			for (ScoredDocument document : results) {
				comics = comicsAccess.read( document.getId() );
				comics.setCoverBlobKey(BlobUtil.servingUrl(comics.getCoverBlobKey()));
				list.add( comics );
			}
			
			cursor = results.getCursor();
			nextCursor = (cursor == null)? null : cursor.toWebSafeString();
		}
		catch(Exception ex){
			ex.printStackTrace();
		}

		return SUCCESS;
	}
	
}
