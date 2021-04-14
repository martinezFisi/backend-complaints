package com.martinez.complaints.exception;

public class WrongSearchCriteriaException extends RuntimeException {

    public WrongSearchCriteriaException() {
    }

    public WrongSearchCriteriaException(String message) {
        super(message);
    }

    public WrongSearchCriteriaException(String message, Throwable cause) {
        super(message, cause);
    }
}
