package com.lcmartins.infrastructure.orders.persistence.food;


import com.lcmartins.domain.entities.EntityId;
import com.lcmartins.domain.general.Price;
import com.lcmartins.domain.general.Sellable;
import com.lcmartins.domain.validators.Handler;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "tbl_food")
public class FoodDBEntity extends Sellable {
    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "tbl_food_ingredient",
            joinColumns = @JoinColumn(name = "food_id"),
            inverseJoinColumns = @JoinColumn(name = "ingredient_id"))
    private List<IngredientDBEntity> ingredients;

    public FoodDBEntity() {}

    public FoodDBEntity(String id, String name, BigDecimal price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public String getFoodId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Price getPrice() {
        return Price.with(price);
    }

    @Override
    public EntityId getId() {
        return new EntityId() {
            @Override
            public String getValue() {
                return id;
            }
        };
    }

    public List<IngredientDBEntity> getIngredients() {
        return ingredients;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof FoodDBEntity that)) return false;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public void validate(Handler handlerHandler) {
    }
}
