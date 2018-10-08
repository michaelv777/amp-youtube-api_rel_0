package com.google.api.services.youtube.impl;

import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.google.api.services.youtube.data.CommentThreads;
import com.google.api.services.youtube.data.Search;
import com.google.api.services.youtube.model.SearchResult;

public class RestYoutubeWorker extends RestYoutubeBase
{
	//---class variables-------------

	public RestYoutubeWorker()
	{
		String cMethodName = "";
		
		try
		{
			StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
	        StackTraceElement ste = stacktrace[1];
	        cMethodName = ste.getMethodName();
		}
		catch( Exception e)
		{
			System.out.println("M.V. Custom::" + cMethodName + "::" + e.getMessage());
			
			this.setLcRes(false);
		}
	}
	
	public RestYoutubeWorker(HashMap<String, String> cSystemConfiguration)
	{
		String cMethodName = "";
		
		try
		{
			StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
	        StackTraceElement ste = stacktrace[1];
	        cMethodName = ste.getMethodName();
	        
	        this.setcSystemConfiguration(cSystemConfiguration);
	        
	        this.setSystemProperties();
		}
		catch( Exception e)
		{
			System.out.println("M.V. Custom::" + cMethodName + "::" + e.getMessage());
			
			this.setLcRes(false);
		}
	}
	
	public RestYoutubeWorker(HashMap<String, String> cSystemConfiguration,
							 HashMap<String, String> cWorkerConfiguration) 
	{
		String cMethodName = "";
		
		try
		{
			StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
	        StackTraceElement ste = stacktrace[1];
	        cMethodName = ste.getMethodName();
	        
	        this.setcSystemConfiguration(cSystemConfiguration);
	        
	        this.setcWorkerConfiguration(cWorkerConfiguration);
	        
	        this.setSystemProperties();
	        
	        this.setWorkerProperties();
	        
		}
		catch( Exception e)
		{
			System.out.println("M.V. Custom::" + cMethodName + "::" + e.getMessage());
			
			this.setLcRes(false);
		}
	}

	//---class methods-------------
	
	//---
	@Override
	public boolean publishLink(String targetId, 
							   String linkUrl, 
							   String message) 
	{
		boolean cRes = true;
		
		String cMethodName = "";
		
		try
		{
			StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
	        StackTraceElement ste = stacktrace[1];
	        cMethodName = ste.getMethodName();
	     
	        if ( StringUtils.isBlank(targetId))
	        {
	        	System.out.println("M.V. Custom::" + cMethodName + "::targetId is null!");
	        	
	        	cRes = false;
	        }
	        
	        if ( StringUtils.isBlank(linkUrl))
	        {
	        	System.out.println("M.V. Custom::" + cMethodName + "::linkUrl is null!");
	        	
	        	cRes = false;
	        }
	        
	        
	        
	        return cRes;
		}
		catch( Exception e)
		{
			System.out.println("M.V. Custom::" + cMethodName + "::" + e.getMessage());
			
			e.printStackTrace();
			
			this.setLcRes(cRes = false);
			
			return cRes;
		}
	}
	
	//---
	
	
	
	//---
	@Override
	public boolean processPosts(String sourceId) 
	{
		boolean cRes = true;
		
		String cMethodName = "";
	
		try
		{
			StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
	        StackTraceElement ste = stacktrace[1];
	        cMethodName = ste.getMethodName();
	     
	        if ( StringUtils.isBlank(sourceId))
	        {
	        	System.out.println("M.V. Custom::" + cMethodName + "::targetId is null!");
	        	
	        	cRes = false;
	        }
	        
	        
	        
	        return cRes;
		}
		catch( Exception e)
		{
			System.out.println("M.V. Custom::" + cMethodName + "::" + e.getMessage());
			
			e.printStackTrace();
			
			this.setLcRes(cRes = false);
			
			return cRes;
		}
	}
	//---

	//---
	@Override
	public List<SearchResult> getPosts(String sourceId, int pageLimit, long minutesAgo)
	{
		@SuppressWarnings("unused")
		boolean cRes = true;
		
		String cMethodName = "";
		
		List<SearchResult> allPosts = 
				new LinkedList<SearchResult>();
		try
		{
			StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
	        StackTraceElement ste = stacktrace[1];
	        cMethodName = ste.getMethodName();
			
			final Calendar hoursAgoCalendar = Calendar.getInstance();
			hoursAgoCalendar.add(Calendar.MINUTE, (int)minutesAgo); //example = -24
			
			Search cSearch = new Search();
			
			allPosts = cSearch.getPosts(sourceId, pageLimit, minutesAgo);
			
			return allPosts;
		
		}
		catch( Exception e)
		{
			System.out.println("M.V. Custom::" + cMethodName + "::" + e.getMessage());
			
			this.setLcRes(cRes = false);
			
			return null;
		}
	}
	//---

	//---
	@Override
	public boolean publishPostComment(
			String channelId, 
			String targetId, 
			String linkUrls, 
			String message) 
	{
		boolean cRes = true;
		
		String cMethodName = "";
		
		try
		{
			StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
	        StackTraceElement ste = stacktrace[1];
	        cMethodName = ste.getMethodName();
	        
	        CommentThreads cCommentThread = new CommentThreads();
	        
	        cRes = cCommentThread.publishPostComment(channelId, targetId, linkUrls, message);
	        
			return cRes;
		}
		catch( Exception e)
		{
			System.out.println("M.V. Custom::" + cMethodName + "::" + e.getMessage());
			
			e.printStackTrace();
			
			this.setLcRes(cRes = false);
			
			return cRes;
		}
	}
	//---
}
