package africa.xLogistics.dtos.requests;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AddMoneyToWalletRequest {
    private String userId;
    private BigDecimal amount;
}
