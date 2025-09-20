package org.jerry.common.dto.notification;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jerry.common.constant.PaymentMethod;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PaymentNotificationRequest {
    private String orderReference;
    private BigDecimal amount;
    private PaymentMethod paymentMethod;
    private String customerFirstName;
    private String customerLastName;
    private String customerEmail;
}
