package com.example.quarkus.hello;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.config.inject.ConfigProperty;

@Path("/file")
public class FileTester {

    @ConfigProperty(name = "resource.path")
    String path;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/")
    public String testQuarkusFileNative() throws IOException {
        return Files.readString(Paths.get(path + "/test.json"));
    }

}