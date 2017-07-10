/**
 * 
 */
package com.bharatmehta.webcrawler;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.Test;

/**
 * @author Bharat.Mehta
 *
 */
public class CrawlPageTest {

	/**
	 * Test method for {@link com.bharatmehta.webcrawler.CrawlPage#call()}.
	 */
	@Test
	public void basicTest() throws MalformedURLException, ExecutionException, InterruptedException {
		ExecutorService executorService = Executors.newSingleThreadExecutor();
		Future<Page> future = executorService.submit(new CrawlPage(new URL("http://example.com"),1));
		Page done = future.get();
		assertNotNull(done.getLinks());
		assertTrue(done.countSelector("a[href]") > 0);
		
	}
	
	
	
}
