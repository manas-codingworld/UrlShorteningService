package com.manas.uss.repositories;

/**
 * 
 * @author manasranjan
 * repository interface to do operations in redis server
 */
public interface URLRepository {
	
	/**
	 * @return incremented ID from the redis server
	 */
	Long incrementID();
	
	/**
	 * save in the hash in the redis
	 */
	void saveUrl(String key, String longUrl);
	
	/**
	 * get the long url using the id from redis hash
	 */
	String getUrl(Long id) throws Exception;
	
	/**
	 *get id from the hash using the long url to avoid duplicate entries 
	 */
	String getID(String longUrl);
}
