package com.bharatmehta.webcrawler;

import java.net.MalformedURLException;

public class WebcrawlerApplication {

	public static void main(String[] args) throws NumberFormatException, MalformedURLException {
		
		new PageCrawler(args[0], Integer.parseInt(args[1]), Integer.parseInt(args[2]));
	}
}
