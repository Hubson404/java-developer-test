package org.hubson404.javadevelopertest.controllers;

import lombok.extern.slf4j.Slf4j;
import org.hubson404.javadevelopertest.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorInformation handle(MethodArgumentNotValidException exception) {
        Map<String, List<String>> errorDetails = exception.getFieldErrors()
                .stream()
                .filter(fieldError -> fieldError.getDefaultMessage() != null)
                .collect(Collectors.groupingBy(FieldError::getField,
                        Collectors.mapping(FieldError::getDefaultMessage, Collectors.toList())));
        log.warn(exception.toString());
        return new ErrorInformation(errorDetails);
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorInformation handle(BadRequestException exception) {
        String errorDetails = exception.getMessage();
        log.warn(exception.toString());
        return new ErrorInformation(errorDetails);
    }

    @ExceptionHandler(IncorrectQueryDataException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorInformation handle(IncorrectQueryDataException exception) {
        String errorDetails = exception.getMessage();
        log.warn(exception.toString());
        return new ErrorInformation(errorDetails);
    }

    @ExceptionHandler(InsufficientQueryDataException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorInformation handle(InsufficientQueryDataException exception) {
        String errorDetails = exception.getMessage();
        log.warn(exception.toString());
        return new ErrorInformation(errorDetails);
    }

    @ExceptionHandler(ReportNotFoundExeption.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorInformation handle(ReportNotFoundExeption exception) {
        String errorDetails = exception.getMessage();
        log.warn(exception.toString());
        return new ErrorInformation(errorDetails);
    }

    @ExceptionHandler(CharacterNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorInformation handle(CharacterNotFoundException exception) {
        String errorDetails = exception.getMessage();
        log.warn(exception.toString());
        return new ErrorInformation(errorDetails);
    }

    @ExceptionHandler(DataProcessingErrorException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorInformation handle(DataProcessingErrorException exception) {
        String errorDetails = exception.getMessage();
        log.warn(exception.toString());
        return new ErrorInformation(errorDetails);
    }

    @ExceptionHandler(ExternalApiConnectionFailureException.class)
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public ErrorInformation handle(ExternalApiConnectionFailureException exception) {
        String errorDetails = exception.getMessage();
        log.warn(exception.toString());
        return new ErrorInformation(errorDetails);
    }

}
