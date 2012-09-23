package comics.util;

public class Seo {

	//search engine friendly url for a comics
	public static String seoUrl(String url){
		String seoURL = url
				.replaceAll(" ?- ?","-") 			//remove spaces around hyphens
				.replaceAll("[ ']","-") 			//turn spaces and quotes into hyphens
				.replaceAll("[^0-9a-zA-Z-]",""); 	//remove everything not in our allowed char set
		
		return seoURL;
	}
	
}
