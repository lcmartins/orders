package com.lcmartins.infrastructure.orders.persistence.food;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodMysqlRepository extends JpaRepository<FoodDBEntity, String> {
}
