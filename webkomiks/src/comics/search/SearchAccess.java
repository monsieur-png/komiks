package comics.search;

import javax.persistence.PersistenceException;

import com.google.appengine.api.search.Cursor;
import com.google.appengine.api.search.Query;
import com.google.appengine.api.search.QueryOptions;
import com.google.appengine.api.search.Results;
import com.google.appengine.api.search.ScoredDocument;
import com.google.appengine.api.search.SearchException;
import com.google.appengine.api.search.SortExpression;
import com.google.appengine.api.search.SortOptions;
import comics.comics.Comics;


public class SearchAccess {
	
	public static int SEARCH_RESULT = 3;

	public Results<ScoredDocument> searchComics(Comics comics, Cursor cursor) throws SearchException{
		
		Results<ScoredDocument> results = null;

		SortOptions sortOption = SortOptions.newBuilder()
				.addSortExpression(SortExpression.newBuilder()
						.setExpression("dateAdded")
						.setDirection(SortExpression.SortDirection.DESCENDING)
						.setDefaultValueNumeric(0.0))
						.build();

		QueryOptions options = QueryOptions.newBuilder()
				.setLimit( SEARCH_RESULT )
				.setSortOptions( sortOption )
				.setCursor(cursor)
				.build();

		//query string
		String queryStr = null;
		if ( comics == null )
			queryStr = "";


		Query query = Query.newBuilder().setOptions(options).build( queryStr );
		Indexes index = new Indexes();
		results = index.getIndex( Indexes.COMICS_INDEX ).search( query );

		return results;
	}
	
	
	//returns comics created by a user
	public Results<ScoredDocument> searchComicsByUser(String userEmail, Cursor cursor) throws PersistenceException{
		
		Results<ScoredDocument> results = null;

		SortOptions sortOption = SortOptions.newBuilder()
				.addSortExpression(SortExpression.newBuilder()
						.setExpression("dateAdded")
						.setDirection(SortExpression.SortDirection.DESCENDING)
						.setDefaultValueNumeric(0.0))
						.build();

		QueryOptions options = QueryOptions.newBuilder()
				.setLimit( SEARCH_RESULT )
				.setSortOptions( sortOption )
				.setCursor(cursor)
				.build();

		//query string
		String queryStr = "userEmail:"+userEmail;

		Query query = Query.newBuilder().setOptions(options).build( queryStr );
		Indexes index = new Indexes();
		results = index.getIndex( Indexes.COMICS_INDEX ).search( query );

		return results;
	}
	
	
	//returns comics in a given genre
	public Results<ScoredDocument> searchComicsByGenre(String genre, Cursor cursor) throws SearchException{
		
		Results<ScoredDocument> results = null;

		SortOptions sortOption = SortOptions.newBuilder()
				.addSortExpression(SortExpression.newBuilder()
						.setExpression("dateAdded")
						.setDirection(SortExpression.SortDirection.DESCENDING)
						.setDefaultValueNumeric(0.0))
						.build();

		QueryOptions options = QueryOptions.newBuilder()
				.setLimit( SEARCH_RESULT )
				.setSortOptions( sortOption )
				.setCursor(cursor)
				.build();

		//query string
		String queryStr = "genre:\"" + genre + "\"";

		Query query = Query.newBuilder().setOptions(options).build( queryStr );
		Indexes index = new Indexes();
		results = index.getIndex( Indexes.COMICS_INDEX ).search( query );

		return results;
	}
	
}
