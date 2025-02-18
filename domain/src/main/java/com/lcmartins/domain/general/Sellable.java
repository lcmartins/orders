package com.lcmartins.domain.general;


import com.lcmartins.domain.entities.DomainEntity;
import com.lcmartins.domain.entities.EntityId;

public abstract class Sellable extends DomainEntity{
    public abstract String getName();
    public abstract Price getPrice();
    public abstract EntityId getId();
}
