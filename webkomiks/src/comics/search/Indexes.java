package comics.search;

import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.search.Consistency;
import com.google.appengine.api.search.Document;
import com.google.appengine.api.search.Field;
import com.google.appengine.api.search.Index;
import com.google.appengine.api.search.IndexSpec;
import com.google.appengine.api.search.SearchException;
import com.google.appengine.api.search.SearchServiceFactory;
import comics.comics.Comics;


public class Indexes {
	
	public static String COMICS_INDEX = "comicsIndex1";

	public Index getIndex( String index ) {
	    IndexSpec indexSpec = IndexSpec.newBuilder()
	        .setName( index )
	        .setConsistency(Consistency.PER_DOCUMENT)
	        .build();
	    
	    return SearchServiceFactory.getSearchService().getIndex( indexSpec );
	}
	
	
	public void indexComics(Comics comics, String email) throws SearchException{

		Document doc = Document.newBuilder()
			.setId( KeyFactory.keyToString(comics.getKey()) ) 
			.addField( Field.newBuilder().setName( "title" ).setText( comics.getTitle() ) )
			.addField( Field.newBuilder().setName( "description" ).setText( comics.getDescription() ) )
			.addField( Field.newBuilder().setName( "dateAdded" ).setDate( Field.date(comics.getDateAdded()) ) )
			.addField( Field.newBuilder().setName( "dateLastUpdated" ).setDate( Field.date(comics.getDateLastUpdated()) ) )
			.addField( Field.newBuilder().setName( "userEmail" ).setText( email ) )
			
			.addField( Field.newBuilder().setName( "genre" ).setText( comics.getGenre().toString() ) )
			
		    .build();
		
		getIndex( COMICS_INDEX ).add( doc );

	}
	
	
	public void removeIndex(String index, String id){
		
		getIndex( index ).remove( id );
	}
	
}
