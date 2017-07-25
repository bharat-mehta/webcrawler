/**
 * 
 */
package com.bharatmehta.webcrawler;

import java.io.IOException;
import java.net.MalformedURLException;

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
		PageCrawler crawler = new PageCrawler(("http://example.com"), 10, 10);
		crawler.crawl();
	}

}
