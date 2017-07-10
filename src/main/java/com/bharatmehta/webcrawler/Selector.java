/**
 * 
 */
package com.bharatmehta.webcrawler;

/**
 * @author Bharat.Mehta
 *
 */
public enum Selector {
	
	MEDIA("[src]","src"),
	LINKS("a[href]","href");
	
	
	private String css;
	private String name;

	private Selector(String css,String name) {
		this.name = name;
		this.css = css;
	}
	
	public String toString(){
		return css;
	}
	
	public String attribute(){
		return name;
	}
	
}
