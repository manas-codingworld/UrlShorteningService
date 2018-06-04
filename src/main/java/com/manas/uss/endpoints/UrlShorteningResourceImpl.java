package com.manas.uss.endpoints;

import java.io.IOException;
import java.net.URISyntaxException;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.PathParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.manas.uss.businessservices.URLConverterService;
import com.manas.uss.datamodel.ShortenRequest;
import com.manas.uss.datamodel.ShortenResponse;
import com.manas.uss.utils.UrlValidator;

/**
 * @author manasranjan
 * resource interface for URL shortening service
 */
@Named
public class UrlShorteningResourceImpl implements URLShorteningResource{
	private static final Logger LOGGER = LoggerFactory.getLogger(UrlShorteningResourceImpl.class);

	@Inject
	private URLConverterService urlConverterService;

	/**
	 * 
	 * @param shortenRequest containing the original url
	 * @param uri info to get the absolute path of the host
	 * @return response containing shortened URL
	 * @throws Exception
	 */
	public Response shortenUrl(final ShortenRequest shortenRequest, @Context UriInfo ui) throws Exception {
		LOGGER.info("App path: " + ui.getAbsolutePath());
		String longUrl = shortenRequest.getUrl();
		if (UrlValidator.validateURL(longUrl)) {
			String localURL = ui.getAbsolutePath().toString();
			String shortenedUrl = urlConverterService.shortenURL( localURL,shortenRequest.getUrl());
			LOGGER.info("Shortened url to: " + shortenedUrl);
			return Response.ok(new ShortenResponse(shortenedUrl)).build();
		}
		throw new WebApplicationException("Please enter a valid URL",Status.BAD_REQUEST);
	}

	/**
	 * 
	 * @param id- path param in the shortened url
	 * @param http response for redirecting to original url
	 * @throws IOException
	 * @throws URISyntaxException
	 * @throws Exception
	 */
	public void redirectUrl(@PathParam("id") String id, @Context HttpServletResponse resp) throws IOException, URISyntaxException, Exception {
		LOGGER.debug("Received shortened url to redirect: " + id);
		String redirectUrlString = urlConverterService.getLongURLFromID(id);
		if (redirectUrlString != null)
			resp.sendRedirect(redirectUrlString);
		else
			resp.sendError(HttpServletResponse.SC_NOT_FOUND);
	}
}

