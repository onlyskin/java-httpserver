[![Build Status](https://travis-ci.org/onlyskin/java-httpserver.svg?branch=master)](https://travis-ci.org/onlyskin/java-httpserver)

# HTTP Server application written in Java

This is an HTTP Server application written to conform to the 8th Light cob spec test suite which can be found at
[https://github.com/8thlight/cob_spec](https://github.com/8thlight/cob_spec).

You can run the test suite by following the instructions at the above URL. You
will need to edit the user-defined variables to match the path to the compiled
jar file for this server, and the path to the cloned cob_spec repo.

## Building

### To build the server, clone this repository then run:
```
gradle clean
gradle jar
```
\(There is sometimes a build error related to writing the XML test results.
If this happens, just run `gradle jar` again.\)

### To run the server, run:
```
java -jar build/libs/httpserver-1.0-SNAPSHOT.jar -p 5000 -d DIRECTORY_TO_SERVE
```
\(Replace `DIRECTORY_TO_SERVE` with the absolute path to the cloned `cob_spec/public/` folder
or any other directory you want to serve.\)

## Development

### The project has continuous integration with Travis CI, you can see results at:

[https://travis-ci.org/onlyskin/java-httpserver/branches](https://travis-ci.org/onlyskin/java-httpserver/branches)

### To run the unit tests for the project, run:
```
gradle test
```

### To run the tests continuously for development, run:
```
gradle -t test
```

### To run the cob_spec test suite:

Follow the instructions at
[https://github.com/8thlight/cob_spec](https://github.com/8thlight/cob_spec).

You will need to clone the cob_spec repository and start the testing server according
to the instructions. You will then need to open the testing suite in your browser
and edit the user-defined variables in the browser UI to match the start command
for this server \(`java -jar ABSOLUTE_PATH_TO_COMPILED_JAR.jar`\), and the absolute
path to the cloned cob_spec repo.

Note that when the cob_spec test suite is run, the files `public/form` and
`public/method_options` will be created. This is because the suite specifies
successful `POST` requests to these two routes and the server treats a `POST`
request as a file creating operation. The server will also create a file at
`public/logs` if it does not already exist.
