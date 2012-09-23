package comics.user;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

import com.google.appengine.api.datastore.Text;
import comics.comics.Comics;

@NamedQueries({
	@NamedQuery(name="User.getUser",
		query="SELECT u FROM User u WHERE u.userName = :userName")
})

@Entity
public class User {

	@Id
	private String email = null;
	private String userName = null;
	private String fullName = null;
	private Text aboutMySelf = null;
	private Date dateCreated = null;
	private Date lastLogin = null;
	
	private int comicsCount = 0;
	
	@OneToMany(targetEntity=Comics.class, mappedBy="user", fetch=FetchType.LAZY, cascade={CascadeType.PERSIST, CascadeType.REMOVE})
	private List<Comics> comics = null;
	
	
	public User() {	}
	
	public User(String email, String userName) {
		this.email = email;
		this.userName = userName;
		Date date = new Date();
		this.dateCreated = date;
		this.lastLogin = date;
		this.aboutMySelf = new Text("");
	}


	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	
	public String getAboutMySelf() {
		return aboutMySelf.getValue();
	}
	public void setAboutMySelf(String aboutMySelf) {
		this.aboutMySelf = new Text(aboutMySelf);
	}
	
	public Date getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}
	
	public Date getLastLogin() {
		return lastLogin;
	}
	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}

	public int getComicsCount() {
		return comicsCount;
	}
	public void setComicsCount(int comicsCount) {
		this.comicsCount = comicsCount;
	}

	public List<Comics> getComics() {
		return comics;
	}
	public void setComics(List<Comics> comics) {
		this.comics = comics;
	}
	
}
