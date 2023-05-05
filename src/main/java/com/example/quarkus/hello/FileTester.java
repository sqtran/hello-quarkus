package com.example.quarkus.hello;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

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