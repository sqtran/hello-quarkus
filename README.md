# hello-quarkus project

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

I bootstrapped this from the quarkus demo, and updated it with some endpoints that are good for demo purposes.

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```bash
mvn quarkus:dev
```

## Packaging and running the application

The application can be packaged using
```bash
mvn package
```

It produces the `hello-quarkus-1.0-SNAPSHOT-runner.jar` file in the `/target` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/lib` directory.

The application is now runnable using
```bash
java -jar target/hello-quarkus-1.0-SNAPSHOT-runner.jar
```

## Creating a native executable

You can create a native executable using:
```bash
mvn package -Pnative
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using:
```bash
mvn package -Pnative -Dquarkus.native.container-build=true
```

You can then execute your native executable with:
```bash
target/hello-quarkus-1.0-SNAPSHOT-runner
```

If you want to learn more about building native executables, please consult https://quarkus.io/guides/building-native-image.


## Troubleshooting
If you get an error about not being able to find `native-image`, it's because you haven't installed that tool yet.  Make sure you have `GRAALVM_HOME` set, and update the path to include `GRAALVM_HOME/bin`.  Then run the following.

```bash
gu install native-image
```