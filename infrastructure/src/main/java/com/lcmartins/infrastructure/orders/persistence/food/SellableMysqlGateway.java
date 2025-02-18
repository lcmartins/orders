package com.lcmartins.infrastructure.orders.persistence.food;

import com.lcmartins.domain.gateways.SellableGateway;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class SellableMysqlGateway implements SellableGateway<FoodDBEntity> {
    public SellableMysqlGateway(FoodMysqlRepository foodMysqlRepository) {
        this.foodMysqlRepository = foodMysqlRepository;
    }

    private final FoodMysqlRepository foodMysqlRepository;


    @Override
    public List<FoodDBEntity> getItemsByIds(List<String> ids) {
        return foodMysqlRepository.findAllById(ids);
    }

    @Override
    public BigDecimal getMininumOrderValue() {
        return BigDecimal.valueOf(100);
    }
}
