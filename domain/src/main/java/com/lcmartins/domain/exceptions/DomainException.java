package com.lcmartins.domain.exceptions;

import com.lcmartins.domain.validators.DomainError;

import java.util.List;

public class DomainException extends StackTraceSupressedException {
    private final List<DomainError> errors;

    public DomainException(String message, List<DomainError> errors) {
        super(message);
        this.errors = errors;
    }

    public DomainException(DomainError error) {
        super(error.message());
        this.errors = List.of(error);
    }
}
