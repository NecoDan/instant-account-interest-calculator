package br.com.deliverit.instant.account.interest.calculator.account.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author Daniel Santos
 * @since 28/03/2021
 */
@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AccountPayableModel {
    private String name;
    private BigDecimal originValue = BigDecimal.ZERO;
    private BigDecimal correctedValue = BigDecimal.ZERO;
    private Integer totalDaysLate;
    private LocalDateTime payDay;
}
