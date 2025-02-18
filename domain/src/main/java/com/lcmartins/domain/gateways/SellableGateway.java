package com.lcmartins.domain.gateways;

import com.lcmartins.domain.general.Sellable;

import java.math.BigDecimal;
import java.util.List;

public interface SellableGateway<T extends Sellable> {
    List<T> getItemsByIds(List<String> ids);
    BigDecimal getMininumOrderValue();
}
