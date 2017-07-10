/**
 * 
 */
package com.bharatmehta.webcrawler;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @author Bharat.Mehta
 *
 */
public class Page  {
	
	
	private final  URL url;
	
	private final int depth ;
	
	private final Map<String, HashSet<URL>> links = new HashMap<String,HashSet<URL>>();

	
	
	
	public Page(URL url, int depth) {
		super();
		this.url = url;
		this.depth = depth;
	}


	public int getDepth() {
		return depth;
	}

	public URL getUrl() {
		return url;
	}


	public Map<String, HashSet<URL>> getLinks() {
		return links;
	}

	
	
	public boolean addURL(String selector , String url) throws MalformedURLException{
		{
			if(!links.containsKey(selector)){
				HashSet<URL> list = new HashSet<URL>();
				links.put(selector,list);
			}
			
			return links.get(selector).add(new URL(url));
		}
		
	}
	public boolean addURL(String selector , URL url){
		{
			if(!links.containsKey(selector)){
				HashSet<URL> list = new HashSet<URL>();
				links.put(selector,list);
			}
			
			return links.get(selector).add(url);
		}
		
	}
	
	
	
	public Set<URL> getURLs(String selector){
		return links.get(selector);
	}
	
	
	public int countSelector(String selector){
	     if(!links.containsKey(selector))
	    		 return 0;
	     else
	    	 return links.get(selector).size();
	    		 
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Page [url=");
		builder.append(url);
		builder.append(", links=");
		builder.append(links);
		builder.append("]");
		return builder.toString();
	}
	
	
	public String json(){
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
	
		String json = gson.toJson(this);
		
		return json;
	}
	

}
