package org.hubson404.javadevelopertest.exceptions;

import lombok.Data;

import java.util.List;
import java.util.Map;


@Data
public class ErrorInformation {

    private String errorMessage;
    private Map<String, List<String>> errors;

    public ErrorInformation() {
    }

    public ErrorInformation(Map<String, List<String>> errors) {
        this.errors = errors;
    }

    public ErrorInformation(String errorMessage) {
        this.errorMessage = errorMessage;
    }

}
