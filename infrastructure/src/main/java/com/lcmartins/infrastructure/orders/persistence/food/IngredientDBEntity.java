package com.lcmartins.infrastructure.orders.persistence.food;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "tbl_ingredient")
public class IngredientDBEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ingredientId;

    private String name;

    private  int quantityInStock;

    public IngredientDBEntity() {
    }

    public IngredientDBEntity(Integer ingredientId, String name, int quantityInStock) {
        this.ingredientId = ingredientId;
        this.name = name;
        this.quantityInStock = quantityInStock;
    }

    public Integer getIngredientId() {
        return ingredientId;
    }

    public String getName() {
        return name;
    }

    public int getQuantityInStock() {
        return quantityInStock;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof IngredientDBEntity that)) return false;
        return Objects.equals(ingredientId, that.ingredientId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(ingredientId);
    }
}
