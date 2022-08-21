package com.martinez.complaints.exception;

public class GoogleSignInException extends RuntimeException{

    public GoogleSignInException(String message) {
        super(message);
    }

    public GoogleSignInException(String message, Throwable cause) {
        super(message, cause);
    }
}
