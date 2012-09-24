package comics.channel;

import java.util.ArrayList;

import com.google.appengine.api.search.Cursor;
import com.google.appengine.api.search.Results;
import com.google.appengine.api.search.ScoredDocument;
import com.opensymphony.xwork2.ActionSupport;
import comics.admin.genre.Genre;
import comics.admin.genre.GenreAccess;
import comics.comics.Comics;
import comics.comics.ComicsAccess;
import comics.search.SearchAccess;
import comics.util.BlobUtil;

public class GenreChannelAction extends ActionSupport{

	private static final long serialVersionUID = 1L;
	
	private ArrayList<Comics> list = null;
	private String nextCursor = null;
	private Genre genre = null;
	private String genreName = null;
	

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
	
	public Genre getGenre() {
		return genre;
	}
	public void setGenre(Genre genre) {
		this.genre = genre;
	}
	
	public String getGenreName() {
		return genreName;
	}
	public void setGenreName(String genreName) {
		this.genreName = genreName;
	}
	
	
	//list of comics by genre
	public String execute() throws Exception{
		GenreAccess access = new GenreAccess();
		genre = access.read( genreName );
		
		if (genre == null)
			return NONE;
		
		SearchAccess searchAccess = new SearchAccess();
		Cursor cursor = null;
		Results<ScoredDocument> results = null;

		cursor = Cursor.newBuilder().build();
		results = searchAccess.searchComicsByGenre(genre.getGenre(), cursor);
		
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
	
	
	//next list of comics by genre
	public String nextList() throws Exception{
		GenreAccess access = new GenreAccess();
		genre = access.read(genreName);

		if (genre == null)
			return null;
		
		SearchAccess searchAccess = new SearchAccess();
		Cursor cursor = null;
		Results<ScoredDocument> results = null;

		cursor = Cursor.newBuilder().build( nextCursor );
		results = searchAccess.searchComicsByGenre(genre.getGenre(), cursor);

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

		return SUCCESS;
	}
	
}
