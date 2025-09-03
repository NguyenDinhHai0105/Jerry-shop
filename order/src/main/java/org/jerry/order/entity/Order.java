package org.jerry.order.entity;

import java.math.BigDecimal;
import java.util.UUID;

public class Order {
    private UUID id;
    private String reference;
    private BigDecimal totalAmount;
    private UUID customerId;
}
