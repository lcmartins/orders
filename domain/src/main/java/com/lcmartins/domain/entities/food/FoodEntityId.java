package com.lcmartins.domain.entities.food;

import com.lcmartins.domain.entities.EntityId;
import java.util.Objects;

public class FoodEntityId extends EntityId {
    private final String id;

    private FoodEntityId(String id) {
        this.id = id;
    }

    public static FoodEntityId with(String value) {
        return new FoodEntityId(value);
    }


    @Override
    public boolean equals(Object o) {
        if (!(o instanceof FoodEntityId foodId)) return false;
        return Objects.equals(getValue(), foodId.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getValue());
    }


    @Override
    public String getValue() {
        return id;
    }

}
