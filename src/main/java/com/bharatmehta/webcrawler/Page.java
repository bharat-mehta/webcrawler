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
 * @author Bharat.Mehta
 *
 */
public class Page  {
	
	
	private final  URL url;
	
	private final  int depth ;
	
	private final  URL referrer;
	
	private final Map<String, HashSet<URL>> media = new HashMap<String,HashSet<URL>>();
	
	private final Map<String, HashSet<URL>> imports = new HashMap<String,HashSet<URL>>();
	
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
	
		

	
	
	
	public String json(){
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
	
		String json = gson.toJson(this);
		
		return json;
	}
	

}
