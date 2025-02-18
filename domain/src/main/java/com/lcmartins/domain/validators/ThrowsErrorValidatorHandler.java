package com.lcmartins.domain.validators;

import com.lcmartins.domain.exceptions.DomainException;

import java.util.Collections;
import java.util.List;

public class ThrowsErrorValidatorHandler implements Handler {
    @Override
    public Handler add(DomainError error) {
        throw new DomainException(error);
    }

    @Override
    public List<DomainError> getValidationErrors() {
        return Collections.emptyList();
    }
}
