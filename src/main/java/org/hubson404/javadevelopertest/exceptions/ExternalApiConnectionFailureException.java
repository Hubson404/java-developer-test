package org.hubson404.javadevelopertest.exceptions;

public class ExternalApiConnectionFailureException extends RuntimeException {

    public ExternalApiConnectionFailureException(String message) {
        super(message);
    }

}
