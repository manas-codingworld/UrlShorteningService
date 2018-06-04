package com.manas.uss.endpoints;

import java.io.IOException;
import java.net.URISyntaxException;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.manas.uss.businessservices.URLConverterService;
import com.manas.uss.datamodel.ShortenRequest;
import com.manas.uss.utils.URLValidator;

@Named
@Path("/app")
public class UrlShorteningResource{
	private static final Logger LOGGER = LoggerFactory.getLogger(UrlShorteningResource.class);

	@Inject
	private URLConverterService urlConverterService;

	@POST
	@Path("/shorten")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
	public String shortenUrl(final ShortenRequest shortenRequest, @Context UriInfo ui) throws Exception {
		LOGGER.info("Received url to shorten: " + shortenRequest.getUrl());
		LOGGER.info("App path: " + ui.getBaseUri().getPath());
		String longUrl = shortenRequest.getUrl();
		if (URLValidator.validateURL(longUrl)) {
			String localURL = "http://localhost:8008/api/app";//request.getRequestURL().toString();
			String shortenedUrl = urlConverterService.shortenURL( localURL,shortenRequest.getUrl());
			LOGGER.info("Shortened url to: " + shortenedUrl);
			return shortenedUrl;
		}
		throw new WebApplicationException("Please enter a valid URL",Status.BAD_REQUEST);
	}

	@GET
	@Path("/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
	public void redirectUrl(@PathParam("id") String id, @Context HttpServletResponse resp) throws IOException, URISyntaxException, Exception {
		LOGGER.debug("Received shortened url to redirect: " + id);
		String redirectUrlString = urlConverterService.getLongURLFromID(id);
		if (redirectUrlString != null)
			resp.sendRedirect(redirectUrlString);
		else
			resp.sendError(HttpServletResponse.SC_NOT_FOUND);
	}
}

