package com.manas.uss.repositories;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response.Status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Repository;

import redis.clients.jedis.Jedis;

/**
 * 
 * @author manasranjan
 * repository class to do operations in redis server
 *
 */
@Repository
@PropertySource("classpath:application.properties")
public class URLRepositoryImpl implements URLRepository{
	
	//redis client
	private final Jedis jedis;
	
	@Value("${jedis.idKey}")
	private  String idKey;
	
	@Value("${jedis.short.url}")
	private  String urlKey;
	
	@Value("${jedis.created.url}")
	private  String createURLKey;
	
	@Value("${jedis.expiry.time}")
	private String expiry;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(URLRepositoryImpl.class);

	public URLRepositoryImpl() {
		this.jedis = new Jedis();
	}

	/**
	 * increment ID in the redis server hash
	 */
	@Override
	public Long incrementID() {
		Long id = jedis.incr(idKey);
		LOGGER.info("Incrementing ID: {}", id-1);
		return id - 1;
	}
	
	/**
	 * save in the hash in the redis
	 */
	@Override
	public void saveUrl(String key, String longUrl) {
		LOGGER.info("Saving: {} at {}", longUrl, key);
		jedis.hset(urlKey, key, longUrl);
		jedis.expire(key, Integer.parseInt(expiry));//setting expiry for 2 days
		//added to avoid duplicate entries
		jedis.hset(createURLKey, longUrl,key.substring(4));
		jedis.expire(createURLKey, Integer.parseInt(expiry));//setting expiry for 2 days
	}

	/**
	 * get the long url using the id from redis hash
	 */
	@Override
	public String getUrl(Long id) throws Exception {
		LOGGER.info("Retrieving at {}", id);
		String url = jedis.hget(urlKey, "url:"+id);
		if (url == null) {
			throw new WebApplicationException("URL at key" + id + " does not exist/ is expired",Status.NOT_FOUND);
		}
		return url;
	}
	
	/**
	 *get id from the hash using the long url to avoid duplicate entries 
	 */
	@Override
	public String getID(String longUrl){
		return jedis.hget(createURLKey, longUrl);
	}
}
