package com.canevim.integration.exception;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class ShelterExceptionHandler extends ResponseEntityExceptionHandler {


    @Override
    protected ResponseEntity<Object>  handleMethodArgumentNotValid(MethodArgumentNotValidException exception, HttpHeaders headers, HttpStatusCode httpStatus, WebRequest webRequest){
        final String path = webRequest.getDescription(false);

        List<String> validationErrors = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + " : " + error.getDefaultMessage())
                .collect(Collectors.toList());

        ShelterError sordmanError = ShelterError.builder()
                .timestamp(LocalDateTime.now())
                .status(httpStatus.value())
                .message(httpStatus.toString())
                .path(path)
                .detail(validationErrors.toString())
                .type(exception.getClass().getSimpleName())
                .build();

        return new ResponseEntity<>(sordmanError, httpStatus);
    }


    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> handleException(WebRequest webRequest, Exception exception) {
        ResponseStatus responseStatus = exception.getClass().getAnnotation(ResponseStatus.class);
        final HttpStatus httpStatus = Objects.nonNull(responseStatus) ? responseStatus.value() : HttpStatus.INTERNAL_SERVER_ERROR;
        final String localizedMessage = exception.getLocalizedMessage();
        final String path = webRequest.getDescription(false);
        final String message = Objects.nonNull(localizedMessage) ? localizedMessage : httpStatus.getReasonPhrase();

        ShelterError shelterError = ShelterError.builder()
                .timestamp(LocalDateTime.now())
                .status(httpStatus.value())
                .message(httpStatus.name())
                .path(path)
                .detail(exception.getCause() != null ? message : "")
                .type(exception.getClass().getSimpleName())
                .build();

        return new ResponseEntity<>(shelterError, httpStatus);
    }

    @ExceptionHandler(ShelterException.class)
    public ResponseEntity<Object> handleShelterException(WebRequest webRequest, ShelterException shelterException) {
        final String path = webRequest.getDescription(false);

        ShelterError shelterError = ShelterError.builder()
                .timestamp(LocalDateTime.now())
                .status(shelterException.getStatus().value())
                .message(shelterException.getMessage())
                .path(path)
                .detail(shelterException.getDetail())
                .type(shelterException.getClass().getSimpleName())
                .build();

        return new ResponseEntity<>(shelterError, shelterException.getStatus());
    }
}
