import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.Drive.Files;
import com.google.api.services.drive.Drive.Files.List;
import com.google.api.services.drive.DriveScopes;


public class piDriveHandler {

	 private static String CLIENT_ID = "388913030005-7sb20dob3imo2jubcvlrgs03qqokddss.apps.googleusercontent.com";
	  private static String CLIENT_SECRET = "N0U7gwOJbafKSAi2u4kk23PZ";

	  private static String REDIRECT_URI = "urn:ietf:wg:oauth:2.0:oob";
	  
	  public void driveRun() throws IOException {
	    HttpTransport httpTransport = new NetHttpTransport();
	    JsonFactory jsonFactory = new JacksonFactory();
	   
	    GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
	        httpTransport, jsonFactory, CLIENT_ID, CLIENT_SECRET, Arrays.asList(DriveScopes.DRIVE))
	        .setAccessType("online")
	        .setApprovalPrompt("auto").build();
	    
	    String url = flow.newAuthorizationUrl().setRedirectUri(REDIRECT_URI).build();
	    System.out.println("Please open the following URL in your browser then type the authorization code:");
	    System.out.println("  " + url);
	    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	    String code = br.readLine();
	    
	    GoogleTokenResponse response = flow.newTokenRequest(code).setRedirectUri(REDIRECT_URI).execute();
	    GoogleCredential credential = new GoogleCredential().setFromTokenResponse(response);

	    //Create a new authorized API client
	    Drive service = new Drive.Builder(httpTransport, jsonFactory, credential).build();
	    
	    
	    ArrayList<File> result = new ArrayList<File>();
	    Files.List request = service.files().list().setQ("title = 'New file'");

	    do {
	      try {
	        FileList files = request.execute();

	        result.addAll(files.getItems());
	        request.setPageToken(files.getNextPageToken());
	      } catch (IOException e) {
	        System.out.println("An error occurred: " + e);
	        request.setPageToken(null);
	      }
	    } while (request.getPageToken() != null &&
	             request.getPageToken().length() > 0);
	    
	    
	    
	  }
	    /**
	     * Download a file's content.
	     * 
	     * @param service Drive API service instance.
	     * @param file Drive File instance.
	     * @return InputStream containing the file's content if successful,
	     *         {@code null} otherwise.
	     */
	    private static InputStream downloadFile(Drive service, File file) {
	      if (file.getDownloadUrl() != null && file.getDownloadUrl().length() > 0) {
	        try {
	          HttpResponse resp =
	              service.getRequestFactory().buildGetRequest(new GenericUrl(file.getDownloadUrl()))
	                  .execute();
	          return resp.getContent();
	        } catch (IOException e) {
	          // An error occurred.
	          e.printStackTrace();
	          return null;
	        }
	      } else {
	        // The file doesn't have any content stored on Drive.
	        return null;
	      }
	    }
}
	

