[![Build Status](https://travis-ci.org/onlyskin/java-httpserver.svg?branch=master)](https://travis-ci.org/onlyskin/java-httpserver)

# HTTP Server application written in Java

This is an HTTP Server application written to conform to the 8th Light cob spec test suite which can be found at
[https://github.com/8thlight/cob_spec](https://github.com/8thlight/cob_spec).

You can run the test suite by following the instructions at the above URL. You
will need to edit the user-defined variables to match the path to the compiled
jar file for this server, and the path to the cloned cob_spec repo.

## Building

### To build the server, run:
```
gradle clean
gradle jar
```

### To run the server, run
```
java -jar build/libs/httpserver-1.0-SNAPSHOT.jar -p 5000 -d DIRECTORY_TO_SERVE
```

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
