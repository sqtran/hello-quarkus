#!/bin/bash

keytool -genkeypair -storepass password -keyalg RSA -keysize 2048 -dname "CN=server" -alias server -ext "SAN:c=DNS:localhost,IP:127.0.0.1" -keystore src/main/resources/META-INF/resources/hello-quarkus.jks -validity 1095