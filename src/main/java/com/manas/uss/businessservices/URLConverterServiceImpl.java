package com.manas.uss.businessservices;

import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.manas.uss.repositories.URLRepository;
import com.manas.uss.utils.IDConverter;

/**
 * @author manasranjan
 * service class to support URL shortening and vice-versa
 * 
 */
@Named
public class URLConverterServiceImpl implements URLConverterService{

	private static final Logger LOGGER = LoggerFactory.getLogger(URLConverterServiceImpl.class);

	@Inject
	private final URLRepository urlRepository;

	@Autowired
	public URLConverterServiceImpl(URLRepository urlRepository) {
		this.urlRepository = urlRepository;
	}

	/**
	 * @param localURL is the absolute URI of the request(domain name with the application name)
	 * @param longUrl is the originalURL to shorten 
	 * @return shortened URL
	 */
	@Override
	public String shortenURL(String localURL, String longUrl) {
		Long id;
		//checking if longURL is present in the redis already or not
		if(urlRepository.getID(longUrl)!=null){
			id=(long) Integer.parseInt(urlRepository.getID(longUrl));
		}else{
			id = urlRepository.incrementID();
		}
		//encoded value using id
		String uniqueID = IDConverter.createUniqueID(id);
		urlRepository.saveUrl("url:"+id, longUrl);
		//appending the absolute url with the uniqueID
		String shortenedURL = localURL+ uniqueID;
		return shortenedURL;
	}

	/**
	 * 
	 * @param uniqueID is the path param value in the shortened url
	 * @return id stored as key in the db
	 * @throws Exception
	 */
	@Override
	public String getLongURLFromID(String uniqueID) throws Exception {
		//getting key from the redis server using the path param value
		Long dictionaryKey = IDConverter.getDictionaryKeyFromUniqueID(uniqueID);
		String longUrl = urlRepository.getUrl(dictionaryKey);
		LOGGER.info("Converting shortened URL back to {}", longUrl);
		return longUrl;
	}
}
