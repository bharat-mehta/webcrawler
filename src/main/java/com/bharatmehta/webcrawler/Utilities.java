/**
 * 
 */
package com.bharatmehta.webcrawler;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.apache.commons.validator.routines.UrlValidator;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * @author Bharat.Mehta
 *
 */
public class Utilities {

	
	public static boolean isValidURL(String url) {
		UrlValidator urlValidator = new UrlValidator();
		return urlValidator.isValid(url);
	}
	
	public static File file (String baseDirectory ,Page page){
		if( page.getReferrer() == null){
			return new File (baseDirectory + File.separator + "page.json" );
		}
		return new File (baseDirectory + File.separator + page.getUrl().getPath() + File.separator + "page.json");
	}
	
	public static String baseDirectory(URL url){
	    return  System.currentTimeMillis() + "_" + url.getHost();
	}
	
	
	public static  Connection.Response connect(URL url , URL referrer , int timeout) throws IOException  {
		 Connection.Response response = null;
		if(referrer != null){
			 response = Jsoup.connect(url.toString())
					.timeout(timeout)
					.ignoreContentType(true)
					.userAgent("Mozilla")
					.referrer(referrer.toString())
					.execute();
		}else{
			 response = Jsoup.connect(url.toString())
					.timeout(timeout)
					.ignoreContentType(true)
					.userAgent("Mozilla")
					.execute();
		}
		return response;
	}
	
	
}
