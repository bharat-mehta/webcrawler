/**
 * 
 */
package com.bharatmehta.webcrawler;

import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * 
 * Class denoting a web page
 * @author Bharat.Mehta
 *
 */
public class Page  {
	
	
	/**
	 * URL of the page
	 */
	private final  URL url;
	
	/**
	 * Depth of the URL from the referrer
	 */
	private final  int depth ;
	
	/**
	 * Referrer of the URL, Will be null if no referrer
	 */
	private final  URL referrer;
	
	/**
	 * HTTP status code on connecting to the page URL
	 */
	private  int statusCode;
	
	/**
	 * Milliseconds to scan the whole page
	 */
	private  long milliSeconds;
	
	
	
	/**
	 *  HTML elements with src attribute
	 */
	private final Map<String, HashSet<URL>> media = new HashMap<String,HashSet<URL>>();
	
	/**
	 * HTML links like CSS or some other imported page
	 */
	private final Map<String, HashSet<URL>> imports = new HashMap<String,HashSet<URL>>();
	
	/**
	 * Anchor tags in the page
	 */
	private final Set<URL> hyperLinks = new LinkedHashSet<URL>();

	
	
	
	public Page(URL url, int depth, URL referrer) {
		super();
		this.url = url;
		this.depth = depth;
		this.referrer = referrer;
	}


	public int getDepth() {
		return depth;
	}

	public URL getUrl() {
		return url;
	}


	
	

	
	public URL getReferrer() {
		return referrer;
	}


	public Map<String, HashSet<URL>> getMedia() {
		return media;
	}


	public Map<String, HashSet<URL>> getImports() {
		return imports;
	}


	public Set<URL> getHyperLinks() {
		return hyperLinks;
	}


	public boolean addURL(Selector selector , String tagName, URL url){
		{
			boolean isAdded = false;
			
			switch(selector){
			case LINKS:
				isAdded = hyperLinks.add(url);
				break;
			case IMPORTS:
				if(!imports.containsKey(tagName)){
					HashSet<URL> list = new HashSet<URL>();
					imports.put(tagName,list);
				}
				
				isAdded = imports.get(tagName).add(url);
				break;
			case MEDIA:
				if(!media.containsKey(tagName)){
					HashSet<URL> list = new HashSet<URL>();
					media.put(tagName,list);
				}
				
				isAdded = media.get(tagName).add(url);
				break;
			}
			
			return isAdded;
			
		}
		
	}
	
	

	public int getStatusCode() {
		return statusCode;
	}


	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}


	
	public long getMilliSeconds() {
		return milliSeconds;
	}


	public void setMilliSeconds(long milliSeconds) {
		this.milliSeconds = milliSeconds;
	}

	

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Page [url=");
		builder.append(url);
		builder.append(", depth=");
		builder.append(depth);
		builder.append(", referrer=");
		builder.append(referrer);
		builder.append(", statusCode=");
		builder.append(statusCode);
		builder.append(", milliSeconds=");
		builder.append(milliSeconds);
		builder.append("]");
		return builder.toString();
	}

	
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((url == null) ? 0 : url.hashCode());
		return result;
	}


	
	/**
	 * Returns the JSON version of the Page which is used for reporting
	 * @return
	 */
	public String json(){
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
	
		String json = gson.toJson(this);
		
		return json;
	}
	

	
}
