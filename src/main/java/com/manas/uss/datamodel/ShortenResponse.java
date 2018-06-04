package com.manas.uss.datamodel;

/**
 * @author manasranjan
 * data model class for response
 */
public class ShortenResponse {
	
	private String shortUrl;

	public ShortenResponse() {

	}

	public ShortenResponse( String shortUrl) {
		this.shortUrl = shortUrl;
	}

	public String getShortUrl() {
		return shortUrl;
	}

	public void setShortUrl(String shortUrl) {
		this.shortUrl = shortUrl;
	}

}
