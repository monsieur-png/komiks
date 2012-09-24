package comics.user;

import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

public class UserAction extends ActionSupport implements SessionAware{
	
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
	
	
	//view own account
	public String execute() throws Exception{
		String email = (String) session.get("email");
		if ( email == null)
			return null;
		
		UserAccess access = new UserAccess();
		user = access.read( email );

		return SUCCESS;
	}
	
	
	public String update() throws Exception{	
		String email = (String) session.get("email");
		if ( email == null)
			return null;
		
		UserAccess access = new UserAccess();
		if ( access.userNameExists(user.getUserName(), user.getEmail()) ){
			addFieldError("userName", "Username already exists.");
			return INPUT;
		}
		
		access.update( user );
			
    	//session
		session.put("userName", user.getUserName());
		
		return SUCCESS;
	}
	
}
