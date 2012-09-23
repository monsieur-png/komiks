package comics.util;

import java.util.HashSet;
import java.util.Set;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.appengine.labs.repackaged.com.google.common.base.Joiner;

public class Url {

	//login url for google users
	public static String loginUrl(String url){
		UserService userService = UserServiceFactory.getUserService();
		return userService.createLoginURL( url );
	}
	
	
	//logout url for admin (google users)
	public static String logoutUrl(String url){
		UserService userService = UserServiceFactory.getUserService();
		return userService.createLogoutURL( url );
	}
	
	
	//genre URL
	public static String genreUrl(Set<String> set){
		String url = "/genre/";
		
		Set<String> tempSet = new HashSet<String>();
		for (String s: set){
			tempSet.add("<a href='" + url + s + "'>" + s + "</a>");
		}		
		String str = Joiner.on(", ").join(tempSet);
		
		return str;
	}
	
	
	//convert datastore key to string
	//used in url parameter
	public static String key(Key key){
		String str = KeyFactory.keyToString(key);

		return str;
	}
}
