/**
 * 
 */
package com.google.api.services.youtube.mo;

import com.google.api.services.youtube.model.ResourceId;
import com.google.api.services.youtube.model.SearchResult;
import com.google.api.services.youtube.model.Thumbnail;

/**
 * @author MVEKSLER
 *
 */
public class SearchResultMO 
{
	protected SearchResult cSearchResult = 
			new SearchResult();
	
	protected Thumbnail cThumbnail = null;

	protected String cChannelId = "";
	protected String cItemId = "";
	protected String cTitle  = "";
	protected String cURL    = "";
	protected String cKind   = "";
	protected String cMessage= "";
     
	public SearchResult getcSearchResult() {
		return cSearchResult;
	}

	public void setcSearchResult(SearchResult cSearchResult) {
		this.cSearchResult = cSearchResult;
	}

	
	public Thumbnail getcThumbnail() {
		return cThumbnail;
	}


	public void setcThumbnail(Thumbnail cThumbnail) {
		this.cThumbnail = cThumbnail;
	}


	public String getcItemId() {
		return cItemId;
	}


	public void setcItemId(String cItemId) {
		this.cItemId = cItemId;
	}


	public String getcTitle() {
		return cTitle;
	}


	public void setcTitle(String cTitle) {
		this.cTitle = cTitle;
	}


	public String getcURL() {
		return cURL;
	}


	public void setcURL(String cURL) {
		this.cURL = cURL;
	}

	public String getcKind() {
		return cKind;
	}

	public void setcKind(String cKind) {
		this.cKind = cKind;
	}

	
	public String getcMessage() {
		return cMessage;
	}

	public void setcMessage(String cMessage) {
		this.cMessage = cMessage;
	}

	public String getcChannelId() {
		return cChannelId;
	}

	public void setcChannelId(String cChannelId) {
		this.cChannelId = cChannelId;
	}

	public SearchResultMO(SearchResult cSearchResult)
	{
		try
		{
			this.setcSearchResult(cSearchResult);
			
			this.initClassVariables();
		}
		catch( Exception e )
		{
			
		}
	}
	
	public boolean initClassVariables()
	{
		try
		{
		 	ResourceId cResourceId = this.cSearchResult.getId();
            // Confirm that the result represents a video. Otherwise, the
            // item will not contain a video ID.
	        if (cResourceId.getKind().equals("youtube#video")) 
            {
                this.cThumbnail = this.cSearchResult.getSnippet().getThumbnails().getDefault();
                this.cChannelId = this.cSearchResult.getSnippet().getChannelId();
                this.cItemId    = cResourceId.getVideoId();
                this.cKind      = cResourceId.getKind();
                this.cTitle     = this.cSearchResult.getSnippet().getTitle();
                this.cURL       = cThumbnail.getUrl();
            }
		     
	        this.cMessage = "You can find the related items here:";
	        
			return true;
		}
		catch( Exception e )
		{
			return false;
		}
	}
}
