package com.example.quarkus.hello;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.util.Map.Entry;
import java.util.concurrent.ThreadLocalRandom;

import org.jboss.logging.Logger;

import io.vertx.core.http.HttpServerRequest;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/")
public class Greeter {

    private static final Logger logger = Logger.getLogger(Greeter.class);

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("")
    public String home() throws UnknownHostException {
        logger.info("Home endpoint called! " + InetAddress.getLocalHost() + " : " + InetAddress.getLocalHost().getHostName());
        return String.format("%s Hello World! : IP %15s : hostname %20s%n", LocalDateTime.now(), InetAddress.getLocalHost().getHostAddress(), InetAddress.getLocalHost().getHostName());
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

        headers.getRequestHeaders().entrySet().stream()
            .sorted(Entry.comparingByKey())
            .forEach(e -> sb.append(String.format("Header %s = %s%n", e.getKey(), e.getValue()))
        );

        return Response.ok(sb.toString()).build();
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("queries")
    public Response queries(@Context HttpServerRequest request) {
        logger.info("queries endpoint called");

        StringBuilder sb = new StringBuilder();

        request.params().entries().stream()
            .sorted(Entry.comparingByKey())
            .forEach(e -> sb.append(String.format("QueryParam '%s' = %s%n", e.getKey(), e.getValue()))
        );

        return Response.ok(sb.toString()).build();
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("envs")
    public Response envVars() {
        logger.info("envs endpoint called");
        StringBuilder sb = new StringBuilder();

        System.getenv().entrySet().stream()
            .sorted(Entry.comparingByKey())
            .forEach(e -> sb.append(String.format("%s = %s%n", e.getKey(), e.getValue()))
        );

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
    @Path("busy")
    public String busy(@QueryParam("iterations") int iterations) {
        logger.info("busy endpoint called");

        double x = 0.1;
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < iterations; i++) {
            // random noise
            x = Math.sin(x);
            x = Math.cos(x);
            x = Math.tan(x);
            x = Math.exp(x);
            x = Math.log(x);
        }
        long totalTime = System.currentTimeMillis() - startTime;
        logger.info(String.format("Total CPU busy time for %d iterations was %d milliseconds", iterations, totalTime));

        return String.format("Busy for %d milliseconds", totalTime);
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("health")
    public String health() {
        logger.info("health endpoint called");
        return "status.:.UP";
    }

}