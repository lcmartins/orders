package com.lcmartins.domain.entities.food;

import com.lcmartins.domain.validators.Handler;
import com.lcmartins.domain.validators.DomainError;
import com.lcmartins.domain.validators.Validator;

import java.util.Objects;

public class FoodValidator extends Validator<Food> {
    public FoodValidator(Handler handler) {
        super(handler);
    }

    @Override
    public void validate(final Food food) {
        if(Objects.isNull(food.getName()) || food.getName().isBlank()) {
            this.getHandler().add(new DomainError("name cannot be null or empty"));
        }

        if (!food.getPrice().isValid()) {
            this.getHandler().add(new DomainError("price %s is not valid".formatted(food.getPrice().getValue())));
        }

        if(Objects.isNull(food.getIngredients()) ||
                food.getIngredients().isEmpty() ||
                !food.getIngredients().stream().allMatch(Ingredient::isValid)) {
            this.getHandler().add(new DomainError("check the ingredientes, they can be in invalid state"));
        }
    }

}
