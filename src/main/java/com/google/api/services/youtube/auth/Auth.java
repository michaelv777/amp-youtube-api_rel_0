package com.google.api.services.youtube.auth;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.CredentialRefreshListener;
import com.google.api.client.auth.oauth2.StoredCredential;
import com.google.api.client.auth.oauth2.TokenErrorResponse;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow.Builder;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.Clock;
import com.google.api.client.util.store.DataStore;
import com.google.api.client.util.store.FileDataStoreFactory;


/**
 * Shared class used by every sample. Contains methods for authorizing a user and caching credentials.
 */
public class Auth {

    /**
     * Define a global instance of the HTTP transport.
     */
    public static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();

    /**
     * Define a global instance of the JSON factory.
     */
    public static final JsonFactory JSON_FACTORY = new JacksonFactory();

    /**
     * This is the directory that will be used under the user's home directory where OAuth tokens will be stored.
     */
    private static final String CREDENTIALS_DIRECTORY = ".oauth-credentials";

    /**
     * Authorizes the installed application to access user's protected data.
     *
     * @param scopes              list of scopes needed to run youtube upload.
     * @param credentialDatastore name of the credential datastore to cache OAuth tokens
     */
    public static Credential autorize(List<String> scopes, String credentialDatastore) throws IOException 
    {
    	Reader clientSecretReader = null;
    	
    	try
    	{
	        // Load client secrets.
	        //clientSecretReader = new InputStreamReader(Auth.class.getResourceAsStream("/client_secrets.json"));
    		clientSecretReader = new InputStreamReader(Auth.class.getResourceAsStream("/allmarker1_auth2_client_secret.json"));
    		
	        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, clientSecretReader);
	
	        // Checks that the defaults have been replaced (Default = "Enter X here").
	        if (clientSecrets.getDetails().getClientId().startsWith("Enter") || 
	        	clientSecrets.getDetails().getClientSecret().startsWith("Enter ")) 
	        {
	            System.out.println(
	                    "Enter Client ID and Secret from https://console.developers.google.com/project/_/apiui/credential "
	                            + "into src/main/resources/client_secrets.json");
	            
	            return null;
	        }
	
	        // This creates the credentials datastore at ~/.oauth-credentials/${credentialDatastore}
	        FileDataStoreFactory fileDataStoreFactory = new FileDataStoreFactory(
	        		new File(System.getProperty("user.home") + "/" + CREDENTIALS_DIRECTORY));
	        
	        DataStore<StoredCredential> datastore = 
	        		fileDataStoreFactory.getDataStore(credentialDatastore);
	
	        Builder builder = new GoogleAuthorizationCodeFlow.Builder(
	                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, scopes);
	        builder.setAccessType("offline");
	        //builder.setApprovalPrompt("force");
	        builder.setClock(Clock.SYSTEM);
	        
	        GoogleAuthorizationCodeFlow flow = builder.setCredentialDataStore(datastore).build();
	        
	        /*
	        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
	                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, scopes).setCredentialDataStore(datastore)
	                .build();
	        */
	      
	        // Build the local server and bind it to port 8080
	        LocalServerReceiver localReceiver = new LocalServerReceiver.Builder().setPort(8080).build();
	
	        // Authorize.
	        Credential credentials = new AuthorizationCodeInstalledApp(flow, localReceiver).authorize("user");
	        //credentials.refreshToken();
	        
	        return credentials;
    	}
    	catch( Exception e )
    	{
    		e.printStackTrace();
    		
    		System.out.println(e.getMessage());
    		
    		return null;
    	}
    	finally
    	{
    		if ( clientSecretReader != null )
    		{
    			clientSecretReader.close();
    		}
    	}
    }
     
    public static Credential createCredential(List<String> scopes)
    {
    	Credential credential = null;
    	
    	Reader clientSecretReader = null;
    	
    	try 
    	{

            // Create a listener for automatic refresh OAuthAccessToken
            List<CredentialRefreshListener> list = new ArrayList<CredentialRefreshListener>();
            list.add(new CredentialRefreshListener() 
            {

                public void onTokenResponse(Credential credential,
                        TokenResponse tokenResponse) throws IOException 
                {
                	if ( tokenResponse != null )
                	{
                		System.out.println(tokenResponse.toPrettyString());
                	}

                }

                public void onTokenErrorResponse(Credential credential,
                        TokenErrorResponse tokenErrorResponse)
                        throws IOException 
                {
                	if ( tokenErrorResponse != null )
                	{
	                    System.err.println("Error: "
	                            + tokenErrorResponse.toPrettyString());
                	}
                }
            });
            
            String pFilePath = Auth.class.getResource("/" + "Allmarket-8c09af7d18be.p12").getFile();
            File pFile = new File(pFilePath);
            
            /*PrivateKey privateKey = SecurityUtils.loadPrivateKeyFromKeyStore(
                    SecurityUtils.getPkcs12KeyStore(),
                    new FileInputStream(pFile), // ???P12??
                    "notasecret", "privatekey", "notasecret");
            */
            
            clientSecretReader = new InputStreamReader(
            		Auth.class.getResourceAsStream("/client_secrets.json"));
            GoogleClientSecrets clientSecrets = 
            		GoogleClientSecrets.load(JSON_FACTORY, clientSecretReader);
            
            //new
            InputStream credentialsJSON = 
            		Auth.class.getResourceAsStream("/Allmarket-28aaa058bde1.json");
            
            GoogleCredential gcFromJson = GoogleCredential.fromStream(
            		credentialsJSON, HTTP_TRANSPORT, JSON_FACTORY).createScoped(scopes);
            
            // Create a GoogleCredential for authenticate with ServiceAccount
            // service
            /*
            credential = new GoogleCredential.Builder()
                    .setTransport(HTTP_TRANSPORT)
                    .setJsonFactory(JSON_FACTORY)
                    .setServiceAccountId("allmarket@allmarket-project-777.iam.gserviceaccount.com")
                    .setServiceAccountScopes(scopes)
                    .setClock(Clock.SYSTEM)
                    .setServiceAccountPrivateKey(privateKey)
                    //.setServiceAccountPrivateKeyFromP12File(pFile)
                    .setClientSecrets(clientSecrets)
                    .setRefreshListeners(list).build();
			*/
            credential = new GoogleCredential.Builder()
                    .setTransport(gcFromJson.getTransport())
                    .setJsonFactory(gcFromJson.getJsonFactory())
                    .setServiceAccountId(gcFromJson.getServiceAccountId())
                    .setServiceAccountScopes(gcFromJson.getServiceAccountScopes())
                    .setClock(Clock.SYSTEM)
                    .setServiceAccountPrivateKey(gcFromJson.getServiceAccountPrivateKey())
                    .setClientSecrets(clientSecrets)
                    .setRefreshListeners(list).build();
            
            credential.refreshToken();
            
            return credential;
            
        } /*catch (GeneralSecurityException e) {
            e.printStackTrace();
        } */
    	catch (IOException e) 
    	{
            e.printStackTrace();
            
            return null;
        }
    	finally
    	{
    		if ( clientSecretReader != null )
    		{
    			try {
					clientSecretReader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    		}
    	}
    }
    
    public Credential createWebCredentials(List<String> scopes, String credentialDatastore) throws IOException 
    {
    	Reader clientSecretReader = null;
    	
    	try
    	{
	        // Load client secrets.
	        //clientSecretReader = new InputStreamReader(Auth.class.getResourceAsStream("/client_secrets.json"));
    		clientSecretReader = new InputStreamReader(Auth.class.getResourceAsStream("/allmarker1_auth2_oclient_secret.json"));
	        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, clientSecretReader);
	
	        // Checks that the defaults have been replaced (Default = "Enter X here").
	        if (clientSecrets.getDetails().getClientId().startsWith("Enter") || 
	        	clientSecrets.getDetails().getClientSecret().startsWith("Enter ")) 
	        {
	            System.out.println(
	                    "Enter Client ID and Secret from https://console.developers.google.com/project/_/apiui/credential "
	                            + "into src/main/resources/client_secrets.json");
	            
	            return null;
	        }
	
	        // This creates the credentials datastore at ~/.oauth-credentials/${credentialDatastore}
	        FileDataStoreFactory fileDataStoreFactory = new FileDataStoreFactory(
	        		new File(System.getProperty("user.home") + "/" + CREDENTIALS_DIRECTORY));
	        
	        DataStore<StoredCredential> datastore = 
	        		fileDataStoreFactory.getDataStore(credentialDatastore);
	
	        Builder builder = new GoogleAuthorizationCodeFlow.Builder(
	                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, scopes);
	        builder.setAccessType("offline");
	        //builder.setApprovalPrompt("force");
	        builder.setClock(Clock.SYSTEM);
	        
	        GoogleAuthorizationCodeFlow flow = builder.setCredentialDataStore(datastore).build();
	      
	        // Build the local server and bind it to port 8080
	        LocalServerReceiver localReceiver = new LocalServerReceiver.Builder().setPort(8080).build();
	
	        // Authorize.
	        Credential credentials = new AuthorizationCodeInstalledApp(flow, localReceiver).authorize("user");
	        //credentials.refreshToken();
	        
	        return credentials;
    	}
    	catch( Exception e )
    	{
    		e.printStackTrace();
    		
    		System.out.println(e.getMessage());
    		
    		return null;
    	}
    	finally
    	{
    		if ( clientSecretReader != null )
    		{
    			clientSecretReader.close();
    		}
    	}
    }
    //---
    public Credential createServiceCredentials(List<String> scopes)
    {
    	GoogleCredential  credential = null;
    	
    	File is = null;
    	
    	try 
    	{
    		URL resource = this.getClass().getResource("/allmarket1-sa1_7a1070fe4319.json");
    		
    	    try 
    	    {
    	        is = new File(resource.toURI());
    	    } 
    	    catch (URISyntaxException e1) 
    	    {
    	        // TODO Auto-generated catch block
    	        e1.printStackTrace();
    	        
    	        return null;
    	    }
	        
    		credential = GoogleCredential.fromStream(new FileInputStream(is))
    			    .createScoped(scopes);
           
            return credential;
            
        } 
    	
    	catch (IOException e) 
    	{
            e.printStackTrace();
            
            return null;
        }
    	finally
    	{
    		
    	}
    }
    //---
    public Credential createApplicationCredentials(List<String> scopes) throws Exception 
    {
    	HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        
    	FileDataStoreFactory dataStoreFactory = 
    			new FileDataStoreFactory(
    					new File(System.getProperty("user.home") + "/" + CREDENTIALS_DIRECTORY));
        
    	  // load client secrets
    	  GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY,
    	      new InputStreamReader(Auth.class.getResourceAsStream("/allmarker1_auth2_client_secret.json")));
    	  
    	  // set up authorization code flow
    	  GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
    	      httpTransport, JSON_FACTORY, 
    	      clientSecrets,
    	      scopes).setDataStoreFactory(
    	      dataStoreFactory).setAccessType("offline").build();
    	  // authorize
    	  // Build the local server and bind it to port 8080
	      LocalServerReceiver localReceiver = new LocalServerReceiver.Builder().setPort(8080).build();
	        
    	  return new AuthorizationCodeInstalledApp(flow, localReceiver).authorize("user");
    	}
}
