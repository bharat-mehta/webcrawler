package com.bharatmehta.webcrawler;

import java.net.MalformedURLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class WebcrawlerApplication {

	private static final Logger LOGGER = LogManager.getLogger(WebcrawlerApplication.class.getName());
	
	public static void main(String[] args) throws  MalformedURLException {
		Integer maxDepth = 100;
		Integer maxLinks = 50000;;
		
		try{
			 maxDepth = Integer.parseInt(args[1]);
		}catch(NumberFormatException e){
			LOGGER.error(e);
		}catch(IndexOutOfBoundsException e) {
			LOGGER.error(e);
		}
		
		try{
			 maxLinks = Integer.parseInt(args[2]);
		}catch(NumberFormatException e){
			LOGGER.error(e);
		}catch(IndexOutOfBoundsException e) {
			LOGGER.error(e);
		}
		
		String urlLink   = args[0];
		PageCrawler crawler  = new PageCrawler(urlLink, maxDepth, maxLinks);
		crawler.crawl();
	}
}
