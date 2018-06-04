package com.manas.uss.businessservices;

/**
 * @author manasranjan
 * service interface to support URL shortening and vice-versa
 * 
 */
public interface URLConverterService {
	/**
	 * @param localURL is the absolute URI of the request(domain name with the application name)
	 * @param longUrl is the originalURL to shorten 
	 * @return shortened URL
	 */
	String shortenURL(String localURL, String longUrl);
	
	/**
	 * 
	 * @param uniqueID is the path param value in the shortened url
	 * @return id stored as key in the db
	 * @throws Exception
	 */
	String getLongURLFromID(String uniqueID) throws Exception;

}
