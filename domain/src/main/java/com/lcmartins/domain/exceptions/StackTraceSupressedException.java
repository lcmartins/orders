package com.lcmartins.domain.exceptions;

public class StackTraceSupressedException extends RuntimeException{
    public StackTraceSupressedException(String message) {
        super(message);
    }

    public StackTraceSupressedException(String message, Throwable cause) {
        super(message, cause);
    }
}
