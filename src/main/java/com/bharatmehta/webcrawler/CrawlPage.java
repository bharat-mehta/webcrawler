/**
 * 
 */
package com.bharatmehta.webcrawler;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.Callable;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.StopWatch;
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
	
	private Page page;
	

	
	public CrawlPage(URL url , int depth){
		page = new Page(url , depth);
	}
	
	
	/* (non-Javadoc)
	 * @see java.util.concurrent.Callable#call()
	 */
	@Override
	public Page call() throws Exception {
		LOGGER.info("Reading {} ",page.getUrl() );
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		String userAgent = System.getProperty("http.agent");
		Document document = Jsoup.connect(page.getUrl().toString()).timeout(TIMEOUT).ignoreContentType(true)
				.userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0").get();
 
		for ( Selector i : Selector.values()){
			LOGGER.info("Scanning {} for {} ",page.getUrl() , i.toString());
			processLinks(i.toString() ,document.select(i.toString()));
		}
		stopWatch.stop();
        LOGGER.info("Finished reading {} in {} milliseconds", page.getUrl(), stopWatch.getTime());
		return page;
	}
 
	private void processLinks(String selector ,Elements elements) {
		LOGGER.info("Found {} instances of {}  in {}",elements.toArray().length , selector ,page.getUrl());
	
		for (Element element : elements) {
			String href = element.attr("abs:href");
			if (StringUtils.isBlank(href) ||  href.startsWith("#")) {
				continue;
			}
 
			try {
				URL nextUrl = new URL( href);
				
				boolean isAdded  = page.addURL(selector,nextUrl);
				if(isAdded){
					LOGGER.debug("Found {}", nextUrl);
					
				}
			} catch (MalformedURLException e) { 
				
				LOGGER.debug("Ignored {}", href);
			}
		}
	}
	

}
