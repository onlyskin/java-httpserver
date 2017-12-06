[![Build Status](https://travis-ci.org/onlyskin/java-httpserver.svg?branch=master)](https://travis-ci.org/onlyskin/java-httpserver)

# HTTP Server application written in Java

This is an HTTP Server application, written to conform to the 8th Light cob spec test suite, which can be found at
[https://github.com/8thlight/cob_spec](https://github.com/8thlight/cob_spec)

You can run the test suite by following the instructions at the above URL, editing the user-defined variables
to match the compiled jar file from building the server in this repository, and the path to your cloned
cob_spec repo.

## Building

### To build the server, run:
```
gradle clean
gradle jar
```

### To run the server, run `java -jar build/libs/httpserver-1.0-SNAPSHOT.jar`

## Development

### The project has continuous integration with Travis CI, you can see results at
[https://travis-ci.org/onlyskin/java-httpserver/branches](https://travis-ci.org/onlyskin/java-httpserver/branches)

### To run the unit tests for the project, run:
```
gradle test
```

### To run the tests continuously for development, run:
```
gradle -t test
```
