package comics.user;

import java.util.Map;
import java.util.Random;

import org.apache.struts2.interceptor.SessionAware;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.opensymphony.xwork2.ActionSupport;

public class LogAction extends ActionSupport implements SessionAware{

	private static final long serialVersionUID = 1L;
	private Map<String, Object> session;
	private User user = null;

	public Map<String, Object> getSession() {
		return session;
	}
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	
	//google login
	public String execute(){
	    UserService userService = UserServiceFactory.getUserService();
	    com.google.appengine.api.users.User googleUser = userService.getCurrentUser();
	    
	    if (googleUser == null)
	    	return LOGIN;
	    
	    	
	    String email = googleUser.getEmail();
	    UserAccess access = new UserAccess();
	    
	    user = access.read(email);
	    if ( user == null ){
	    	String userName = googleUser.getNickname().replace(".", "") + (new Random().nextInt(100000));
	    	user = new User( googleUser.getEmail(), userName);
	    	access.add( user );
	    	
	    	//session
			session.put("email", email);
			session.put("userName", userName);
			
			return "update";
	    } else{
	    	
	    	//session
			session.put("email", email);
			session.put("userName", user.getUserName());
	    }
	    
		return SUCCESS;
	}
	
	
	//log out user
	public String logout(){
		session.remove("email");
		session.remove("userName");

		return LOGIN;
	}
	
}
