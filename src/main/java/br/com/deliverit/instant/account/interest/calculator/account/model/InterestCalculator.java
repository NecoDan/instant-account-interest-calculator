package br.com.deliverit.instant.account.interest.calculator.account.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class InterestCalculator {

    private static final int TOTAL_DAYS_MONTH = 30;
    private static final int DEFAULT_PERCENTAGE_VALUE = 100;

    private BigDecimal payValue;
    private BigDecimal interestRate;
    private BigDecimal assessmentRate;
    private Integer totalDaysLate;

    public BigDecimal calculateInterest() {
        return this.payValue.add(BigDecimal.valueOf(getLateInterestAmount()))
                .add(BigDecimal.valueOf(getValueAssessment())).setScale(2, RoundingMode.HALF_DOWN);
    }

    private double getLateInterestAmount() {
        return BigDecimal.valueOf(getValueTotalDaysLate() / DEFAULT_PERCENTAGE_VALUE * payValue.doubleValue()).doubleValue();
    }

    private double getValueAssessment() {
        return (assessmentRate.doubleValue() * payValue.doubleValue());
    }

    private double getValueInterestRatePerMonthOf() {
        return (Objects.isNull(this.interestRate)) ? BigDecimal.ZERO.doubleValue() : perfomCalculationValueInterestRatePerMonthOf();
    }

    private double perfomCalculationValueInterestRatePerMonthOf() {
        return BigDecimal.valueOf((this.interestRate.doubleValue() / TOTAL_DAYS_MONTH) * DEFAULT_PERCENTAGE_VALUE).setScale(3, RoundingMode.HALF_DOWN).doubleValue();
    }

    private double getValueTotalDaysLate() {
        return (Objects.isNull(this.totalDaysLate)) ? BigDecimal.ZERO.doubleValue() : perfomCalculationValueTotalDaysLate();
    }

    private double perfomCalculationValueTotalDaysLate() {
        return (getValueInterestRatePerMonthOf() * this.totalDaysLate.doubleValue());
    }

    public BigDecimal calculatePercentage(BigDecimal obtained, BigDecimal total) {
        return BigDecimal.valueOf(obtained.doubleValue() / total.doubleValue() * DEFAULT_PERCENTAGE_VALUE);
    }
}
