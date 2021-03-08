package com.example.quarkus.hello;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.*;

import org.jboss.logging.Logger;

@Path("/")
public class Greeter {

	private static final Logger logger = Logger.getLogger(Greeter.class);
	private static SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss:SSS");

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/")
	public String home() throws UnknownHostException {
		logger.info("Home endpoint called! " + InetAddress.getLocalHost() + " : " + InetAddress.getLocalHost().getHostName());
		return String.format("%s Hello World! : IP %15s : hostname %20s\n", formatter.format(new Date()), InetAddress.getLocalHost().getHostAddress(), InetAddress.getLocalHost().getHostName());
	}

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/hello")
	public String hello() {
		return "hello world";
	}

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/headers")
	public Response listAllHeaders(@Context HttpHeaders headers) {

		MultivaluedMap<String, String> headmap = headers.getRequestHeaders();
		String msg = String.format("Listed %d headers\n", headmap.size());

		for (String s : headmap.keySet()) {
        	String m = String.format("Header '%s' = %s\n", s, headmap.get(s));
            msg += m;
            logger.info(m);
		}

		return Response.ok(msg).build();
	}
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/health")
    public String healthz() {
      logger.info("status.:.UP");
    	return "status.:.UP";
    }

}