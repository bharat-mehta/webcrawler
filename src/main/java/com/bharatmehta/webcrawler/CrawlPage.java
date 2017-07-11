/**
 * 
 */
package com.bharatmehta.webcrawler;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.Callable;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
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
		Document document = null;
		if(referrer != null){
			 document = Jsoup.connect(page.getUrl().toString())
					.timeout(TIMEOUT)
					.ignoreContentType(true)
					.userAgent("Mozilla")
					.referrer(referrer.toString())
					.get();
		}else{
			 document = Jsoup.connect(page.getUrl().toString())
					.timeout(TIMEOUT)
					.ignoreContentType(true)
					.userAgent("Mozilla")
					.get();
		}
		
	 
		for ( Selector i : Selector.values()){
				LOGGER.info("Scanning {} for {} ",page.getUrl() , i.toString());
				final Elements elements = document.select(i.toString());
				findElementsInPage( page ,i ,elements);
		}
			
        
		
		return page;
	}
 
	private void findElementsInPage(Page page, Selector selector ,Elements elements) {
	
		for (Element element : elements) {
			
			String elementLocation =  element.attr("abs:" + selector.attribute() );
			
			if (StringUtils.isBlank(elementLocation) ||  elementLocation.startsWith("#")) {
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
				
				LOGGER.debug("Ignored {}", elementLocation);
			}
		}
	}
	

}
