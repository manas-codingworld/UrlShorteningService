package com.manas.uss.repositories;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response.Status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import redis.clients.jedis.Jedis;

@Repository
public class URLRepository {
	private final Jedis jedis;
	private final String idKey;
	private final String urlKey;
	private final String createURLKey;
	private static final Logger LOGGER = LoggerFactory.getLogger(URLRepository.class);

	public URLRepository() {
		this.jedis = new Jedis();
		this.idKey = "id";
		this.urlKey = "URLShortening";
		this.createURLKey="createURL";
	}

	public URLRepository(Jedis jedis, String idKey, String urlKey, String createURLKey) {
		this.jedis = jedis;
		this.idKey = idKey;
		this.urlKey = urlKey;
		this.createURLKey=createURLKey;
	}

	public Long incrementID() {
		Long id = jedis.incr(idKey);
		LOGGER.info("Incrementing ID: {}", id-1);
		return id - 1;
	}

	public void saveUrl(String key, String longUrl) {
		LOGGER.info("Saving: {} at {}", longUrl, key);
		jedis.hset(urlKey, key, longUrl);
		jedis.expire(key, 2592000);//setting expiry for 30days
		//added to avoid dup
		jedis.hset(createURLKey, longUrl,key.substring(4));
	}

	public String getUrl(Long id) throws Exception {
		LOGGER.info("Retrieving at {}", id);
		String url = jedis.hget(urlKey, "url:"+id);
		if (url == null) {
			throw new WebApplicationException("URL at key" + id + " does not exist/ is expired",Status.NOT_FOUND);
		}
		return url;
	}
	
	public String getID(String longUrl){
		return jedis.hget(createURLKey, longUrl);
	}
}
