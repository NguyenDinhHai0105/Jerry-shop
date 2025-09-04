package org.jerry.order.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class BusinessException extends RuntimeException {
    private final String msg;

    public BusinessException(String msg) {
        super(msg);
        this.msg = msg;
    }
}
