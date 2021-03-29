package br.com.deliverit.instant.account.interest.calculator.account.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountPayableRequest {
    private String name;
    private BigDecimal originValue = BigDecimal.ZERO;
    private LocalDateTime dueDate;
    private LocalDateTime payDay;
}
