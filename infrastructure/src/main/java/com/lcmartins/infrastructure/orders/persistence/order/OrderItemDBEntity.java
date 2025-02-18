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
    @GeneratedValue(strategy = GenerationType.UUID)
    @JdbcType(VarcharJdbcType.class)
    private UUID Id;

    @Column(name = "item_name", nullable = false)
    private String itemName;

    @Column(name = "item_price", nullable = false)
    private BigDecimal itemPrice;

    @Column(name = "created_time", nullable = false)
    private Instant createdTime;

    public OrderItemDBEntity() {
    }

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id")
    private OrderDBEntity orderDBEntity;

    public UUID getID() {
        return Id;
    }

    public String getFoodName() {
        return itemName;
    }

    public BigDecimal getFoodPrice() {
        return itemPrice;
    }

    public OrderDBEntity getOrderEntity() {
        return orderDBEntity;
    }

    public OrderItemDBEntity(String oItemName, BigDecimal oItemPrice, OrderDBEntity oOrderDBEntity) {
        itemName = oItemName;
        itemPrice = oItemPrice;
        orderDBEntity = oOrderDBEntity;
        createdTime = Instant.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderItemDBEntity orderItemDBEntity = (OrderItemDBEntity) o;
        return Objects.equals(Id, orderItemDBEntity.Id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Id, itemName);
    }
}
