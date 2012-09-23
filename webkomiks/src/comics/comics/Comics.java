package comics.comics;

import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import com.google.appengine.api.datastore.Key;
import comics.user.User;
import comics.util.Seo;

@NamedQueries({
	@NamedQuery(name="Comics.getComics",
		query="SELECT c FROM Comics c WHERE c.random = :random AND c.url = :url")
})

@Entity
public class Comics{

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Key key = null;
	
	@ManyToOne(fetch=FetchType.LAZY, targetEntity=User.class)
	private User user = null;
	
	@OneToMany(targetEntity=Chapter.class, mappedBy="comics", fetch=FetchType.LAZY, cascade={CascadeType.PERSIST, CascadeType.REMOVE})
	@OrderBy("order")
	private List<Chapter> chapters = null;
	
	private String title = null;
	private String description = null;
	private Date dateAdded = null;
	private Date dateLastUpdated = null;
	private String coverBlobKey = null;
	private String url = null;				//SEO friendly URL
	private int status = 0;				//ongoing, complete
	private int random = 0;				//random number
	
	private int votes = 0;					//number of votes
	private int views = 0;					//number of page views
	private int chapterCount = 0;			//number of chapters
	
	public Comics() { }
	
	public Comics(String title, String description, String coverBlobKey) {
		this.title = title;
		this.description = description;
		this.coverBlobKey = coverBlobKey;
		Date date = new Date();
		this.dateAdded = date;
		this.dateLastUpdated = date;
		this.url = Seo.seoUrl(title);
		this.status = 0;
		this.random = new Random().nextInt(100000);
	}
	
	@Basic
	private Set<String> genre = null;
	
	public Key getKey() {
		return key;
	}
	public void setKey(Key key) {
		this.key = key;
	}

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public Date getDateAdded() {
		return dateAdded;
	}
	public void setDateAdded(Date dateAdded) {
		this.dateAdded = dateAdded;
	}

	public Date getDateLastUpdated() {
		return dateLastUpdated;
	}
	public void setDateLastUpdated(Date dateLastUpdated) {
		this.dateLastUpdated = dateLastUpdated;
	}

	public String getCoverBlobKey() {
		return coverBlobKey;
	}
	public void setCoverBlobKey(String coverBlobKey) {
		this.coverBlobKey = coverBlobKey;
	}
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}

	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}

	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	public int getRandom() {
		return random;
	}
	public void setRandom(int random) {
		this.random = random;
	}

	public Set<String> getGenre() {
		return genre;
	}
	public void setGenre(Set<String> genre) {
		this.genre = genre;
	}

	public int getVotes() {
		return votes;
	}
	public void setVotes(int votes) {
		this.votes = votes;
	}

	public int getViews() {
		return views;
	}
	public void setViews(int views) {
		this.views = views;
	}

	public int getChapterCount() {
		return chapterCount;
	}
	public void setChapterCount(int chapterCount) {
		this.chapterCount = chapterCount;
	}

	public List<Chapter> getChapters() {
		return chapters;
	}
	public void setChapters(List<Chapter> chapters) {
		this.chapters = chapters;
	}
	
}
