/**
 * 
 */
package com.bharatmehta.webcrawler;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.Callable;

import org.apache.commons.lang3.time.StopWatch;
import org.jsoup.Connection;
import org.jsoup.HttpStatusException;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Bharat.Mehta
 *
 */
public class CrawlPage implements Callable<Page> {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CrawlPage.class);

	private static final int TIMEOUT = 120000;   //Two minute timeout 
	
	private final URL url;
	
	private final int depth;
	
	private final URL referrer;
	

	
	public CrawlPage(URL url , int depth, URL referrer){
		this.url = url;
		this.depth = depth;
		this.referrer = referrer;
	}
	
	
	/* (non-Javadoc)
	 * @see java.util.concurrent.Callable#call()
	 */
	@Override
	public Page call() throws Exception {
		LOGGER.info("Reading {} ", url );
		
		Page page = new Page(url, depth, referrer);
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		try{

			Connection.Response  response =  Utilities.connect(page.getUrl(), page.getReferrer(), TIMEOUT);
			page.setStatusCode(response.statusCode());
			if(response != null && response.statusCode() == 200){
				Document document = response.parse();
				for ( Selector i : Selector.values()){
					LOGGER.info("Scanning {} for {} ",page.getUrl() , i.toString());
					final Elements elements = document.select(i.toString());
					findElementsInPage( page ,i ,elements);
				}
			}

			
		}catch(HttpStatusException e){
			page.setStatusCode(e.getStatusCode());
			LOGGER.error("Exception while reading {} " ,url,e);
		}finally{
			stopWatch.stop();
			page.setMilliSeconds(stopWatch.getTime());
		}

		return page;
	}


	
 
	private void findElementsInPage(Page page, Selector selector ,Elements elements) {
	
		
		for (Element element : elements) {
			
			String elementLocation =  element.attr("abs:" + selector.attribute() );
			
			if (!Utilities.isValidURL(elementLocation)) {
				continue;
			}
 
			try {
				URL url = new URL(elementLocation);
				
				switch(selector){
					case IMPORTS:{
						page.addURL(selector,element.attr("rel"),url);
						break;
					}
					case MEDIA:{
						page.addURL(selector,element.tagName(),url);
						break;
					}
					case LINKS:{
						page.addURL(selector,element.tagName(),url);
						break;
					}
				}
				LOGGER.debug("Found {} -->  {} ", element.tagName(), url);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}
	}
	
}
