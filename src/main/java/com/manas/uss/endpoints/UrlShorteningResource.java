package com.manas.uss.endpoints;

import java.io.IOException;
import java.net.URISyntaxException;

import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.manas.uss.datamodel.ShortenRequest;

/**
 * @author manasranjan
 * resource class for URL shortening service
 */
@Named
@Path("/app")
public interface URLShorteningResource {
	
	/**
	 * 
	 * @param shortenRequest containing the original url
	 * @param uri info to get the absolute path of the host
	 * @return response containing shortened URL
	 * @throws Exception
	 */
	@POST
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
	Response shortenUrl(final ShortenRequest shortenRequest, @Context UriInfo ui) throws Exception;
	
	/**
	 * 
	 * @param id- path param in the shortened url
	 * @param http response for redirecting to original url
	 * @throws IOException
	 * @throws URISyntaxException
	 * @throws Exception
	 */
	@GET
	@Path("/{id}")
	public void redirectUrl(@PathParam("id") String id, @Context HttpServletResponse resp) throws IOException, URISyntaxException, Exception;

}
