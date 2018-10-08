/**
 * 
 */
package com.google.api.services.youtube.interfaces;

import java.util.List;

import com.google.api.services.youtube.model.SearchResult;

/**
 * @author MVEKSLER
 *
 */
public interface RestYoutubeInterface 
{
	public List<SearchResult> getPosts(
			String sourceId, int pageLimit, long minutesAgo);
	
	public boolean processPosts(
			String sourceId); 
	
	public boolean publishLink(
			String targetId, String linkUrl, String message); 
	
	public boolean publishPostComment(
			String cChannelId, String targetId, String linkUrl, String message); 
}
