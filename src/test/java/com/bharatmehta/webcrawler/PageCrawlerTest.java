/**
 * 
 */
package com.bharatmehta.webcrawler;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.junit.Test;

/**
 * @author Bharat.Mehta
 *
 */
public class PageCrawlerTest {

	/**
	 * Test method for {@link com.bharatmehta.webcrawler.PageCrawler#crawl(java.net.URL)}.
	 * @throws InterruptedException 
	 * @throws IOException 
	 * @throws MalformedURLException 
	 */
	@Test
	public void testGo() throws MalformedURLException, IOException, InterruptedException {
		PageCrawler crawler = new PageCrawler(("http://wiprodigital.com"), Integer.MAX_VALUE, Integer.MAX_VALUE);
		crawler.crawl();
	}

}
