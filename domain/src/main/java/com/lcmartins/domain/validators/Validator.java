package com.lcmartins.domain.validators;

public abstract class Validator<O> {
    private Handler handler;

    public Validator(Handler handler) {
        this.handler = handler;
    }

    public abstract void validate(O object);

    public Handler getHandler() {
        return this.handler;
    }
}
