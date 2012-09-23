package comics.admin.genre;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.google.appengine.api.datastore.Text;


@Entity
public class Genre {
	
	@Id
	private String genre = null;
	private Text description = null;
	private int count = 0;
	
	public String getGenre() {
		return genre;
	}
	public void setGenre(String genre) {
		this.genre = genre;
	}
	
	public String getDescription() {
		return description.getValue();
	}
	public void setDescription(String description) {
		this.description = new Text(description);;
	}
	
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	
}
