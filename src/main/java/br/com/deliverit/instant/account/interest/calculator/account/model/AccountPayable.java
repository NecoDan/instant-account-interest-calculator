package br.com.deliverit.instant.account.interest.calculator.account.model;

import br.com.deliverit.instant.account.interest.calculator.account.exceptions.AccountPayableUnProcessableEntityException;
import br.com.deliverit.instant.account.interest.calculator.account.exceptions.InterestCalculationRuleNotFoundException;
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

    @NotNull
    @Column(name = "name")
    private String name;

    @NotNull
    @Digits(integer = 19, fraction = 6)
    @Column(name = "value_origin")
    private BigDecimal originValue = BigDecimal.ZERO;

    @NotNull
    @Column(name = "total_days_late")
    private Integer totalDaysLate;

    @NotNull
    @Digits(integer = 19, fraction = 6)
    @Column(name = "value_pay")
    private BigDecimal payValue = BigDecimal.ZERO;

    @NotNull
    @Digits(integer = 19, fraction = 6)
    @Column(name = "value_corrected")
    private BigDecimal correctedValue = BigDecimal.ZERO;

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
        generateId();
        return this;
    }

    public void generateTotalDaysLate() {
        if (isInvalidDatesParams()) {
            throw new AccountPayableUnProcessableEntityException("Invalid date, payment date and / or due date parameters. Invalid and / or non-existent.");
        }
        this.totalDaysLate = (!isOpening()) ? BigDecimal.ZERO.intValue() : DaysCalculate.totalDaysOverdueBy(this.dueDate, this.payDay);
    }

    public boolean isOverdue() {
        return (isValidTotalDaysLate() && this.totalDaysLate > 0);
    }

    public boolean isValidTotalDaysLate() {
        return (Objects.nonNull(this.totalDaysLate));
    }

    public void calculateAssessment() {
        if (Objects.isNull(this.interestCalculationRule)) {
            throw new InterestCalculationRuleNotFoundException("AccountPayable, error when calculating fine and interest for not finding calculation rule. Invalid and / or non-existent.");
        }

        payValue = this.originValue;
        InterestCalculator interestCalculator = InterestCalculator.builder()
                .payValue(this.payValue)
                .totalDaysLate(this.totalDaysLate)
                .assessmentRate(this.interestCalculationRule.getPercentageAssessment())
                .interestRate(this.interestCalculationRule.getPercentageInterest())
                .build();

        this.correctedValue = (isOverdue()) ? interestCalculator.calculateInterest() : this.payValue;
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
