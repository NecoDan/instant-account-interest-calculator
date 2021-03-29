package br.com.deliverit.instant.account.interest.calculator.account.model;

import br.com.deliverit.instant.account.interest.calculator.rule_calculation.model.InterestCalculationRule;
import br.com.deliverit.instant.account.interest.calculator.util.domain.AbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.experimental.Tolerate;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author Daniel Santos
 * @since 28/03/2021
 */
@ToString
@SuperBuilder
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "account_payable", schema = "account_manager")
@Inheritance(strategy = InheritanceType.JOINED)
public class AccountPayable extends AbstractEntity implements DaysCalculate {

    private static final long serialVersionUID = 9203967709862542437L;

    @Tolerate
    public AccountPayable() {
        super();
    }

    @NotBlank(message = "Insira uma name válido para a conta a ser cadastrada.")
    @NotNull(message = "Insira uma name válido para a conta a ser cadastrada.")
    @Column(name = "name")
    private String name;

    @Positive(message = "O número de control atrelado ao pedido informado deve ser positivo, ou seja, maior que zero (0)")
    @DecimalMin(value = "1.0", inclusive = true)
    @Digits(integer = 19, fraction = 6)
    @Column(name = "value_origin")
    private BigDecimal originValue;

    @Column(name = "total_days_late")
    private Integer totalDaysLate;

    @Column(name = "value_pay")
    private BigDecimal payValue;

    @Column(name = "value_corrected")
    private BigDecimal correctedValue;

    @NotNull
    @Column(name = "dt_due")
    private LocalDateTime dueDate;

    @NotNull
    @Column(name = "dt_pay")
    private LocalDateTime payDay;

    @OneToOne
    @JoinColumn(name = "interest_calculation_rule_id")
    private InterestCalculationRule interestCalculationRule;

    public AccountPayable generateIdRandom() {
        this.generateId();
        return this;
    }

    public boolean isOpening() {
        return this.dueDate.isBefore(this.payDay);
    }

    private boolean isInvalidDatesParams() {
        return (!isValidDueDate() || !isValidPayDay());
    }

    private boolean isValidDueDate() {
        return Objects.nonNull(this.dueDate);
    }

    private boolean isValidPayDay() {
        return Objects.nonNull(this.payDay);
    }
}
