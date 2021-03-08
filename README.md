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


## OpenShift S2I

From here, you can deploy to OpenShift using the S2I process with the OpenJDK11 base image.  But, if you wanted to make this even better, you can build a native Quarkus binary using the Quarkus UBI image.


If you don't already have the image available, you can import it.

```bash
# first create the imagestream
oc create imagestream ubi-quarkus-native-s2i

# next import the image from quay.io, or wherever your images reside
oc create imagestreamtag ubi-quarkus-native-s2i:20.2.0-java11 --from-image=quay.io/quarkus/ubi-quarkus-native-s2i:20.2.0-java11
```

Then update your `BuildConfig` to use the native s2i image instead of the JDK11 base image.

## One Liner
If you want to shortcut a lot of this, just use the `oc new-app` command!

```bash
oc new-app quay.io/quarkus/ubi-quarkus-native-s2i:20.2.0-java11~https://github.com/sqtran/hello-quarkus.git
```

## Build it Faster!
One gotcha is the default resources are a little low for building native images.  You can speed up build times by increasing the resources available in your build pods.  It was taking ~6 minutes with the default settings on my test cluster.

```bash
oc patch bc/quarkus-weather -p '{"spec":{"resources":{"limits":{"cpu":"4", "memory":"4Gi"}}}}'
```

It just adds the following `yaml` stanza to your `BuildConfig`.  It cut the build time down in half, but you can adjust to much as you have available.  Note that a lot of the build time is due to downloading dependencies, so you're gonna hit a limit to your build speed eventually.
```yaml
resources:
  limits:
    cpu: '4'
    memory: 4Gi
```
The application is now runnable using
```bash
java -jar target/hello-quarkus-1.0-SNAPSHOT-runner.jar
```