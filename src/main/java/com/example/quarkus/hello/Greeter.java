package com.example.quarkus.hello;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.logging.Logger;

import io.vertx.core.MultiMap;
import io.vertx.core.http.HttpServerRequest;

@Path("/")
public class Greeter {

	private static final Logger logger = Logger.getLogger(Greeter.class);
	private static final SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss:SSS");

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("")
	public String home() throws UnknownHostException {
		logger.info("Home endpoint called! " + InetAddress.getLocalHost() + " : " + InetAddress.getLocalHost().getHostName());
		return String.format("%s Hello World! : IP %15s : hostname %20s\n", formatter.format(new Date()), InetAddress.getLocalHost().getHostAddress(), InetAddress.getLocalHost().getHostName());
	}

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("hello")
	public String hello() {
		logger.info("hello endpoint called");
		return "hello world";
	}

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("headers")
	public Response headers(@Context HttpHeaders headers) {
		logger.info("headers endpoint called");

		StringBuilder sb = new StringBuilder();
		headers.getRequestHeaders().forEach((k,v) -> sb.append(String.format("Header '%s' = %s\n", k,v)));
		return Response.ok(sb.toString()).build();
	}

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("queries")
	public Response queries(@Context HttpServerRequest request) {
		logger.info("queries endpoint called");

		// Get all query parameters as a map
		MultiMap queryParams = request.params();

		StringBuilder sb = new StringBuilder();
		queryParams.names().forEach((k -> sb.append(String.format("QueryParam '%s' = %s\n", k, queryParams.get(k)))));
		return Response.ok(sb.toString()).build();
	}

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("consleep")
	public String consleep(@QueryParam("time") int time) throws InterruptedException {
		logger.info("consleep endpoint called, sleeping for " + time + " milliseconds");
		Thread.sleep(time);
		return String.format("woke up after %d milliseconds", time);
	}

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("varsleep")
	public String varsleep(@QueryParam("min") int min, @QueryParam("max") int max) throws InterruptedException {
		int randomTime = ThreadLocalRandom.current().nextInt(min, (max == 0 ? min*2: max) + 1);
		logger.info("varsleep endpoint called, sleeping for " + randomTime + " milliseconds");
		Thread.sleep(randomTime);
		return String.format("woke up after %d milliseconds", randomTime);
	}


	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("health")
    public String health() {
    	logger.info("health endpoint called");
    	return "status.:.UP";
    }

}