package com.manas.uss.datamodel;

public class ShortenRequest {

	private String url;

	public ShortenRequest() {

	}

	public ShortenRequest( String url) {
		this.url = url;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
