# HTTP Server application written in Java

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
