package africa.xLogistics.data.models;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Wallet {
    private String id;
    private BigDecimal balance = BigDecimal.ZERO;
}
