package com.lcmartins.domain.entities.order;

import com.lcmartins.domain.entities.EntityId;

import java.util.Objects;
import java.util.UUID;

public class OrderEntityId extends EntityId {
    public final String id;

    private OrderEntityId(String id) {
        this.id = id;
    }

    public static OrderEntityId with(String id) {
        return new OrderEntityId(id);
    }

    public OrderEntityId withOut() {
        return new OrderEntityId(UUID.randomUUID().toString());
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof OrderEntityId orderId)) return false;
        return Objects.equals(id, orderId.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String getValue() {
        return id;
    }
}
