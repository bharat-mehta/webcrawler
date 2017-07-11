/**
 * 
 */
package com.bharatmehta.webcrawler;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
 
/**
 * @author Bharat.Mehta
 *
 */
public class PageCrawler {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PageCrawler.class);
	
	private static final int THREAD_COUNT = 5;
	private static final long PAUSE_TIME = 3000;
 
	private final Set<URL> visited = new LinkedHashSet<>();
	private final List<Future<Page>> futures = new ArrayList<>();
	private final ExecutorService executorService = Executors.newFixedThreadPool(THREAD_COUNT);
 
	private final URL rootURL;
 
	private final int maxDepth ;
	
	private final int maxLinks ;
	
	private final String reportDirectory;
	
 
 
	public PageCrawler(String urlBase, int maxDepth, int maxLinks) throws MalformedURLException {
		this.rootURL = new URL(urlBase);
		this.maxDepth = maxDepth;
		this.maxLinks = maxLinks;
		this.reportDirectory = Utilities.baseDirectory(rootURL);
	}

	public void crawl()  {
 
		StopWatch stopWatch = new StopWatch();
		LOGGER.info("Started crawling {} for {} depth and {} link ", rootURL, maxDepth , maxLinks );
		stopWatch.start();
		
		submitForCrawling(rootURL , 1, null);
 
		while (cancrawl()) ;
		stopWatch.stop();
 
		LOGGER.info("Found {} URLs in {} in {} millseconds ", visited.size() , rootURL, stopWatch.getTime() );
	}
 
	

	public Set<URL> getVisited() {
		return Collections.unmodifiableSet(visited);
	}

	public URL getRootURL() {
		return rootURL;
	}

	public int getMaxDepth() {
		return maxDepth;
	}

	public int getMaxLinks() {
		return maxLinks;
	}
	
	private boolean cancrawl()  {
		pause();
		
		Set<Page> pageSet = new HashSet<>();
		Iterator<Future<Page>> iterator = futures.iterator();
 
		while (iterator.hasNext()) {
			Future<Page> future = iterator.next();
			if (future.isDone()) {
				iterator.remove();
				try {
					final Page page = future.get(10 , TimeUnit.SECONDS);
					LOGGER.info(page.json());
					File file = Utilities.file(reportDirectory , page);
					FileUtils.writeStringToFile(file, page.json(), "UTF-8");
					pageSet.add(page);
				}catch (ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}catch(IOException e){
					e.printStackTrace();
				} catch (TimeoutException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
 
		for (Page page : pageSet) {
			addNewURLs(page);
		}
 
		return (futures.size() > 0);
	}

	
 
	private void addNewURLs(Page page) {
		final Set<URL> urLs = page.getHyperLinks();
		if(urLs != null){
			for (URL url : urLs) {
				if (url.toString().contains("#")) {
					try {
						url = new URL(StringUtils.substringBefore(url.toString(), "#"));
					} catch (MalformedURLException e) {
						LOGGER.error("Exception ", e);
					}
				}
	 
				submitForCrawling(url , page.getDepth() + 1,page.getUrl());
			}
		}
		
	}
 
	private void submitForCrawling(URL url , int depth ,URL referrer)  {
		if (shouldScan(url, depth)) {
			visited.add(url);
 
			CrawlPage grabPage = new CrawlPage(url , depth , referrer);
			Future<Page> future = executorService.submit(grabPage);
		
			futures.add(future);
		}else{
			LOGGER.debug("{} will not be scanned.", url);
		}
	}
 
	
	private boolean shouldScan(URL url, int depth) {
		if (visited.contains(url)) {
			return false;
		}
		if (!url.getHost().replaceFirst("www.", "").equals(rootURL.getHost().replaceFirst("www.", ""))) {
			return false;
		}
		
		if (depth > maxDepth ){
			return false;
		}
		
		if(visited.size() > maxLinks){
			return false;
		}
			
		
		return true;
	}
 
	private void pause() {
		try
		{
		    Thread.sleep(PAUSE_TIME); // Sleep for one second
		}
		catch (InterruptedException e)
		{
		    Thread.currentThread().interrupt();
		}
	}
	
}