package comics.comics;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.google.appengine.api.datastore.Key;

@Entity
public class Page {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Key key = null;
	
	@ManyToOne(fetch=FetchType.LAZY, targetEntity=Chapter.class)
	private Chapter chapter = null;
	
	private String pageBlobKey = null;
	private int order = 0;

	public Page(String pageBlobKey) {
		this.pageBlobKey = pageBlobKey;
	}
	
	public Key getKey() {
		return key;
	}
	public void setKey(Key key) {
		this.key = key;
	}

	public String getPageBlobKey() {
		return pageBlobKey;
	}
	public void setPageBlobKey(String pageBlobKey) {
		this.pageBlobKey = pageBlobKey;
	}
	
	public Chapter getChapter() {
		return chapter;
	}
	public void setChapter(Chapter chapter) {
		this.chapter = chapter;
	}

	public int getOrder() {
		return order;
	}
	public void setOrder(int order) {
		this.order = order;
	}
	
}
