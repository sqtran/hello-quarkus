package com.example.quarkus.hello;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class GreeterUnitTest {

    @Test
    public void testHealthz() {
       Assertions.assertEquals("status.:.UP", new Greeter().health());
    }

    @Test
    public void testHello() {
       Assertions.assertEquals("hello world", new Greeter().hello());
    }

}