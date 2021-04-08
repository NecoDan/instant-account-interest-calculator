package br.com.deliverit.instant.account.interest.calculator.account.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountPayableRequest {

    @NotBlank(message = "Not blank - Provide a valid name to register the new account.")
    @NotNull(message = "Not null - Provide a valid name to register the new account.")
    private String name;

    @Positive(message = "The original value for the account value must be positive, that is, greater than zero (0).")
    private BigDecimal originValue = BigDecimal.ZERO;

    @NotNull(message = "Provide a valid expiration date for registering your new account.")
    private LocalDate dueDate;

    @NotNull(message = "Provide a valid payment date for registering the new account.")
    private LocalDate payDayDate;
}
