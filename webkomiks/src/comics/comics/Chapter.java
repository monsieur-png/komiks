package comics.comics;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import com.google.appengine.api.datastore.Key;

@Entity
public class Chapter {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Key key = null;

	@ManyToOne(fetch=FetchType.LAZY, targetEntity=Comics.class)
	private Comics comics = null;
	
	@OneToMany(targetEntity=Page.class, mappedBy="chapter", fetch=FetchType.LAZY, cascade={CascadeType.PERSIST, CascadeType.REMOVE})
	@OrderBy("order")
	private List<Page> pages = null;
	
	private String title = null;
	private int pageCount = 0;
	private int order = 0;
	
	public Key getKey() {
		return key;
	}
	public void setKey(Key key) {
		this.key = key;
	}
	
	public Comics getComics() {
		return comics;
	}
	public void setComics(Comics comics) {
		this.comics = comics;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	public int getPageCount() {
		return pageCount;
	}
	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}
	
	public List<Page> getPages() {
		return pages;
	}
	public void setPages(List<Page> pages) {
		this.pages = pages;
	}

	public int getOrder() {
		return order;
	}
	public void setOrder(int order) {
		this.order = order;
	}

}
