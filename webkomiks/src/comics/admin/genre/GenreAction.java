package comics.admin.genre;

import java.util.List;

import com.opensymphony.xwork2.ActionSupport;

public class GenreAction extends ActionSupport {

	private static final long serialVersionUID = 1L;
	private static final String GENRE_LIST = "genreList";
	
	private Genre genre = null;
	private List<Genre> list = null;
	private String genreName = null;		
	
	public Genre getGenre() {
		return genre;
	}
	public void setGenre(Genre genre) {
		this.genre = genre;
	}

	public List<Genre> getList() {
		return list;
	}
	public void setList(List<Genre> list) {
		this.list = list;
	}
	
	public String getGenreName() {
		return genreName;
	}
	public void setGenreName(String genreName) {
		this.genreName = genreName;
	}
	
	
	public String add() throws Exception{
		GenreAccess access = new GenreAccess();

		if ( access.genreExists( genre.getGenre()) ){
			addFieldError( "genre", genre.getGenre() + " genre already exist.");
			return INPUT;
		}
		else
			access.add( genre );
	
		
		addActionMessage( genre.getGenre() + " genre successfully saved.");
		return SUCCESS;
	}
	
	
	public String view() throws Exception{
		GenreAccess access = new GenreAccess();
		
		genre = access.read( genreName );
		if (genre == null){
			addActionError("genre does not exist.");
			return GENRE_LIST;
		}
		
		return SUCCESS;
	}
	
	
	public String list() throws Exception{
		
		GenreAccess access = new GenreAccess();
		list = access.list();
			
		return SUCCESS;
	}
	
	
	public String update() throws Exception{
		
		GenreAccess access = new GenreAccess();
		access.update(genre);
		
		addActionMessage( genre.getGenre() + " genre successfully updated.");
		return SUCCESS;
	}
	
	
	public String delete() throws Exception{
		GenreAccess access = new GenreAccess();
		
		genre = access.read( genreName );
		if (genre == null){
			addActionError( "genre does not exist" );
			return GENRE_LIST;
			
		}else if (genre.getCount() > 0){
			addActionError( genre.getGenre() + " genre can't be deleted" );
			return GENRE_LIST;
			
		} else{
			access.delete( genreName );
		}
			
		addActionMessage( genre.getGenre() + " genre successfully deleted");
		return SUCCESS;
	}
	
	
}
