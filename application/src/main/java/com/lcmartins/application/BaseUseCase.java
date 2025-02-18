package com.lcmartins.application;


public abstract class BaseUseCase <IN, OUT> {
    public abstract OUT execute(IN entry);
}
