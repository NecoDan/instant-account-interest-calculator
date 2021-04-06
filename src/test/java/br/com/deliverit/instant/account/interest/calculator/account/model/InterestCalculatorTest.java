package br.com.deliverit.instant.account.interest.calculator.account.model;

import br.com.deliverit.instant.account.interest.calculator.rule_calculation.model.InterestCalculationRule;
import br.com.deliverit.instant.account.interest.calculator.util.RandomUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Slf4j
class InterestCalculatorTest {

    private InterestCalculator interestCalculator;

    @BeforeEach
    @SneakyThrows
    void setUp() {
        this.interestCalculator = new InterestCalculator();
    }

    @Test
    void testCalculateInterestOverdue() {
        log.info("{} ", "#TEST: testCalculateInterestOverdue: ");

        // -- 01_Cenário
        AccountPayable accountPayable = AccountPayable.builder()
                .name(RandomUtil.generateIdentityNameRandom())
                .payValue(BigDecimal.valueOf(150.0))
                .payDay(LocalDateTime.of(LocalDate.of(2021, 4, 6), LocalTime.now()))
                .dueDate(LocalDateTime.of(LocalDate.of(2021, 3, 27), LocalTime.now()))
                .interestCalculationRule(InterestCalculationRule.builder()
                        .percentageAssessment(BigDecimal.valueOf(0.02))
                        .percentageInterest(BigDecimal.valueOf(0.01))
                        .build())
                .build();

        accountPayable.generateIdRandom();
        accountPayable.generateTotalDaysLate();

        this.interestCalculator = InterestCalculator.builder()
                .payValue(accountPayable.getPayValue())
                .totalDaysLate(accountPayable.getTotalDaysLate())
                .assessmentRate(accountPayable.getInterestCalculationRule().getPercentageAssessment())
                .interestRate(accountPayable.getInterestCalculationRule().getPercentageInterest())
                .build();

        // -- 02_Ação && 03_Verificação_Validação
        BigDecimal expected = BigDecimal.valueOf(153.49).setScale(2, RoundingMode.HALF_DOWN);
        Assertions.assertEquals(expected, this.interestCalculator.calculateInterest());
    }

    @Test
    void testCalculateInterestOverdueRandom() {
        log.info("{} ", "#TEST: testCalculateInterestOverdueRandom: ");

        // -- 01_Cenário
        AccountPayable accountPayable = AccountPayable.builder()
                .name(RandomUtil.generateIdentityNameRandom())
                .payValue(RandomUtil.generateDecimalRandomValue(1000))
                .payDay(LocalDateTime.now())
                .dueDate(LocalDateTime.now().minusDays(RandomUtil.generateRandomValueUntil(30)))
                .interestCalculationRule(InterestCalculationRule.builder()
                        .percentageAssessment(BigDecimal.valueOf(0.02))
                        .percentageInterest(BigDecimal.valueOf(0.01))
                        .build())
                .build();

        accountPayable.generateIdRandom();
        accountPayable.generateTotalDaysLate();

        this.interestCalculator = InterestCalculator.builder()
                .payValue(accountPayable.getPayValue())
                .totalDaysLate(accountPayable.getTotalDaysLate())
                .assessmentRate(accountPayable.getInterestCalculationRule().getPercentageAssessment())
                .interestRate(accountPayable.getInterestCalculationRule().getPercentageInterest())
                .build();

        // -- 02_Ação && 03_Verificação_Validação
        Assertions.assertNotSame(accountPayable.getPayValue(), this.interestCalculator.calculateInterest());
    }


    @Test
    void testCalculatePercentage() {
        log.info("{} ", "#TEST: testCalculatePercentage: ");

        // -- 01_Cenário
        BigDecimal expected = BigDecimal.valueOf(83.33).setScale(2, RoundingMode.HALF_UP);

        // -- 02_Ação
        BigDecimal result = this.interestCalculator.calculatePercentage(BigDecimal.valueOf(500.0), BigDecimal.valueOf(600.0))
                .setScale(2, RoundingMode.HALF_UP);

        // -- 03_Verificação_Validação
        Assertions.assertEquals(expected, result);
    }

    @Test
    void testGetPayValue() {
    }

    @Test
    void testGetInterestRate() {
    }

    @Test
    void testGetAssessmentRate() {
    }

    @Test
    void testGetTotalDaysLate() {
    }

    @Test
    void testToString() {
    }
}
