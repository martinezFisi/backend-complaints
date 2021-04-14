package com.martinez.complaints.exception;

public class EmptySearchCriteriaListException extends RuntimeException {

    public EmptySearchCriteriaListException(String message) {
        super(message);
    }

    public EmptySearchCriteriaListException(String message, Throwable cause) {
        super(message, cause);
    }
}
