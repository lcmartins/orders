package com.lcmartins.infrastructure.orders.persistence.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderMysqlRepository extends JpaRepository<OrderDBEntity, String> {
}
