package com.lcmartins.infrastructure.orders.persistence.order;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "tbl_order")
public class OrderDBEntity {

    @Id
    @Column(name = "order_id", nullable = false)
    private String Id;

    @Column(name = "customer_id", nullable = false)
    private Long customerId;

    @Column(name = "total", nullable = false)
    private BigDecimal total;

    @Column(name = "created_time", nullable = false)
    private Instant createdTime;

    @Column(name = "updated_time")
    private Instant updatedTime;

    @OneToMany(mappedBy = "orderDBEntity", cascade = CascadeType.ALL)
    private List<OrderItemDBEntity> orderItemEntities;

    public String getId() {
        return Id;
    }

    public OrderDBEntity() {
    }

    public Long getCustomerId() {
        return customerId;
    }

    public List<OrderItemDBEntity> getOrderItemEntities() {
        return orderItemEntities;
    }

    public void setOrderItemEntities(List<OrderItemDBEntity> orderItemEntities) {
        this.orderItemEntities = orderItemEntities;
    }

    public OrderDBEntity(final String oId, final Long oCustomerId, final BigDecimal oTotal) {
        Id = oId;
        customerId = oCustomerId;
        total = oTotal;
        createdTime = Instant.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderDBEntity orderDBEntity = (OrderDBEntity) o;
        return Objects.equals(Id, orderDBEntity.Id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Id);
    }
}
