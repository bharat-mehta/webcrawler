/**
 * 
 */
package com.bharatmehta.webcrawler;

/**
 * @author Bharat.Mehta
 *
 */
public enum Selector {
	
	MEDIA("[src]"),
	LINKS("a[href]"),
	IMPORTS("[src]");
	
	private String name;

	private Selector(String name) {
		this.name = name;
	}
	
	public String toString(){
		return name;
	}
	
}
