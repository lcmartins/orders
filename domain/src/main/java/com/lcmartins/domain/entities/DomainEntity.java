package com.lcmartins.domain.entities;

import com.lcmartins.domain.validators.Handler;

public abstract class DomainEntity {
    public abstract void validate(Handler handlerHandler);
}
