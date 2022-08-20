package com.martinez.complaints.controller.handler;

import com.martinez.complaints.exception.EmptySearchCriteriaListException;
import com.martinez.complaints.exception.WrongSearchCriteriaException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;
import java.util.Map;

import static java.util.stream.Collectors.toList;

@Slf4j
@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    public static final String ERROR = "error";

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        var errors = ex.getBindingResult()
                       .getFieldErrors()
                       .stream()
                       .map(DefaultMessageSourceResolvable::getDefaultMessage)
                       .collect(toList());

        log.error(errors.toString());

        return ResponseEntity.status(status)
                             .headers(headers)
                             .body(Map.of("errors", errors));
    }

    @ExceptionHandler({
            WrongSearchCriteriaException.class,
            EmptySearchCriteriaListException.class,
            ConversionFailedException.class
    })
    protected ResponseEntity<Object> handleComplaintsApplicationExceptions(Exception e) {
        log.error(e.getMessage(), e);

        return ResponseEntity.badRequest()
                             .body(Map.of(ERROR, e.getMessage()));
    }

    @ExceptionHandler({
            DataAccessException.class
    })
    protected ResponseEntity<Object> handleDatabaseExceptions(DataAccessException e) {
        log.error(e.getMessage(), e);

        return ResponseEntity.badRequest()
                             .body(Map.of(ERROR, e.getRootCause().getMessage()));
    }

    @ExceptionHandler({
            EntityNotFoundException.class
    })
    protected ResponseEntity<Object> handleEntityNotFoundException(EntityNotFoundException e) {
        log.error(e.getMessage(), e);

        return new ResponseEntity<>(Map.of(ERROR, e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({
            Exception.class
    })
    protected ResponseEntity<Object> handleEverythingElseExceptions(Exception e) {
        log.error(e.getMessage(), e);

        return new ResponseEntity<>(Map.of(ERROR, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
