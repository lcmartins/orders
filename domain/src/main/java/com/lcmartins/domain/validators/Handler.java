package com.lcmartins.domain.validators;

import java.util.List;

public interface Handler {
    Handler add(DomainError error);
    List<DomainError> getValidationErrors();
}
