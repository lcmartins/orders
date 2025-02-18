package com.lcmartins.domain.entities.order;


public record TransientOrderItem(
    String id,
    Integer quantity
){

    public static TransientOrderItem with(String  id, Integer quantity) {
       return new TransientOrderItem(id, quantity);
    }
}
