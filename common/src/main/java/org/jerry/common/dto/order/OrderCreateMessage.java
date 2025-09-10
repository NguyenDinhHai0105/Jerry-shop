package org.jerry.common.dto.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jerry.common.dto.request.PurchaseRequest;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderCreateMessage {
    private UUID orderId;
    private List<PurchaseRequest> products;
}
