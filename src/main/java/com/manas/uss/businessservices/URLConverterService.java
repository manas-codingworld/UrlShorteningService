package com.manas.uss.businessservices;

import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.manas.uss.repositories.URLRepository;
import com.manas.uss.utils.IDConverter;

@Named
public class URLConverterService {

	private static final Logger LOGGER = LoggerFactory.getLogger(URLConverterService.class);

	@Inject
	private final URLRepository urlRepository;

	@Autowired
	public URLConverterService(URLRepository urlRepository) {
		this.urlRepository = urlRepository;
	}

	public String shortenURL(String localURL, String longUrl) {
		LOGGER.info("Shortening {}", longUrl);
		//
		Long id;
		if(urlRepository.getID(longUrl)!=null){
			id=(long) Integer.parseInt(urlRepository.getID(longUrl));
		}else{
			id = urlRepository.incrementID();
		}
		String uniqueID = IDConverter.INSTANCE.createUniqueID(id);
		urlRepository.saveUrl("url:"+id, longUrl);
		String baseString = formatLocalURLFromShortener(localURL);
		String shortenedURL = "http://localhost:8008/api/app/"+ uniqueID;
		return shortenedURL;
	}

	public String getLongURLFromID(String uniqueID) throws Exception {
		Long dictionaryKey = IDConverter.INSTANCE.getDictionaryKeyFromUniqueID(uniqueID);
		String longUrl = urlRepository.getUrl(dictionaryKey);
		LOGGER.info("Converting shortened URL back to {}", longUrl);
		return longUrl;
	}

	private String formatLocalURLFromShortener(String localURL) {
		String[] addressComponents = localURL.split("/");
		// remove the endpoint (last index)
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < addressComponents.length - 1; ++i) {
			sb.append(addressComponents[i]);
		}
		sb.append('/');
		return sb.toString();
	}


}
