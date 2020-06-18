package com.example.quarkus.hello;

import io.quarkus.test.junit.NativeImageTest;

@NativeImageTest
public class NativeGreeterIT extends GreeterTest {

    // Execute the same tests but in native mode.
}