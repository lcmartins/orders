package com.lcmartins.infrastructure.orders.persistence.order;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcType;
import org.hibernate.type.descriptor.jdbc.VarcharJdbcType;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "tbl_order_item")
public class OrderItemDBEntity {

    @Id
    private String id;

    @Column(name = "item_name", nullable = false)
    private String itemName;

    @Column(name = "item_price", nullable = false)
    private BigDecimal itemPrice;

    @Column(name = "created_time", nullable = false)
    private Instant createdTime;

    @Column(name = "quantity")
    private Integer quantity;


    @Column(name = "total")
    private BigDecimal total;

    public OrderItemDBEntity() {
    }

    @ManyToOne
    @JoinColumn(name = "order_id")
    private OrderDBEntity orderDBEntity;


    public OrderItemDBEntity(String iItemName, BigDecimal iItemPrice, Integer iQuantity, OrderDBEntity iOrderDBEntity) {
        id = UUID.randomUUID().toString();
        itemName = iItemName;
        itemPrice = iItemPrice;
        orderDBEntity = iOrderDBEntity;
        createdTime = Instant.now();
        quantity = iQuantity;
        total = BigDecimal.valueOf(iQuantity).multiply(itemPrice);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderItemDBEntity orderItemDBEntity = (OrderItemDBEntity) o;
        return Objects.equals(id, orderItemDBEntity.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, itemName);
    }
}
