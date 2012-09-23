package comics.util;

import java.util.List;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.blobstore.UploadOptions;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.images.ServingUrlOptions;
import comics.comics.Page;

public class BlobUtil {

	public static final BlobstoreService bs = BlobstoreServiceFactory.getBlobstoreService();
	
	//upload url for blob
	public static String uploadUrl(String url){
		UploadOptions options = UploadOptions.Builder.withDefaults();
		options.maxUploadSizeBytes(12000000);
		options.maxUploadSizeBytesPerBlob(4000000);
	
		return bs.createUploadUrl(url, options);
	}
	
	
	//serving url for pictures
	public static String servingUrl(String blobKey){
		BlobKey key = new BlobKey( blobKey );
		ServingUrlOptions option = ServingUrlOptions.Builder.withBlobKey(key);
		
		ImagesService imagesService = ImagesServiceFactory.getImagesService();
		return imagesService.getServingUrl(option);
	}
	
	
	public static void deleteBlob(String blobKey){
		bs.delete( new BlobKey ( blobKey ));
	}
	
	
	public static void deleteBlobs(List<Page> list){
		for (Page p: list){
			bs.delete( new BlobKey ( p.getPageBlobKey() ));
		}
		
	}
	
	
}
