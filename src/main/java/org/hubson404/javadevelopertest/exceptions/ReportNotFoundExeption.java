package org.hubson404.javadevelopertest.exceptions;

public class ReportNotFoundExeption extends RuntimeException {

    public ReportNotFoundExeption(Long message) {
        super("Could not find report with id: " + message);
    }

}
