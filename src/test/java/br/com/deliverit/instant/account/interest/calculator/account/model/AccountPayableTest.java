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
class AccountPayableTest {

    private AccountPayable accountPayable;

    @BeforeEach
    @SneakyThrows
    void setUp() {
        this.accountPayable = new AccountPayable();
    }

    @Test
    void testCalculateAssessmentOverdue() {
        log.info("{} ", "#TEST: testCalculateAssessment: ");

        // -- 01_Cenário
        this.accountPayable.setName(RandomUtil.generateIdentityNameRandom());
        this.accountPayable.setPayValue(BigDecimal.valueOf(150.0));
        this.accountPayable.setPayDay(LocalDateTime.of(LocalDate.of(2021, 4, 6), LocalTime.now()));
        this.accountPayable.setDueDate(LocalDateTime.of(LocalDate.of(2021, 3, 27), LocalTime.now()));

        InterestCalculationRule interestCalculationRule = InterestCalculationRule.builder()
                .percentageAssessment(BigDecimal.valueOf(0.02))
                .percentageInterest(BigDecimal.valueOf(0.01))
                .build();

        this.accountPayable.setInterestCalculationRule(interestCalculationRule);
        this.accountPayable.generateIdRandom();
        this.accountPayable.generateTotalDaysLate();

        // -- 02_Ação
        BigDecimal expected = BigDecimal.valueOf(153.49).setScale(2, RoundingMode.HALF_DOWN);
        this.accountPayable.calculateAssessment();

        // -- 03_Verificação_Validação
        Assertions.assertEquals(expected, this.accountPayable.getCorrectedValue());
    }

    @Test
    void testCalculateAssessmentOverdueRandom() {
        log.info("{} ", "#TEST: testCalculateAssessmentOverdueRandom: ");

        // -- 01_Cenário
        this.accountPayable.setName(RandomUtil.generateIdentityNameRandom());
        this.accountPayable.setPayValue(RandomUtil.generateDecimalRandomValue(500));
        this.accountPayable.setPayDay(LocalDateTime.now());
        this.accountPayable.setDueDate(LocalDateTime.now().minusDays(RandomUtil.generateRandomValueUntil(30)));

        InterestCalculationRule interestCalculationRule = InterestCalculationRule.builder()
                .percentageAssessment(BigDecimal.valueOf(0.02))
                .percentageInterest(BigDecimal.valueOf(0.01))
                .build();

        this.accountPayable.setInterestCalculationRule(interestCalculationRule);
        this.accountPayable.generateIdRandom();
        this.accountPayable.generateTotalDaysLate();

        // -- 02_Ação
        this.accountPayable.calculateAssessment();

        // -- 03_Verificação_Validação
        Assertions.assertNotSame(this.accountPayable.getCorrectedValue(), this.accountPayable.getPayValue());
    }

    @Test
    void testCalculateAssessmentOverdueUpToThreeDaysRandom() {
        log.info("{} ", "#TEST: testCalculateAssessmentOverdueUpToThreeDaysRandom: ");

        // -- 01_Cenário
        this.accountPayable.setName(RandomUtil.generateIdentityNameRandom());
        this.accountPayable.setPayValue(RandomUtil.generateDecimalRandomValue(1000));
        this.accountPayable.setPayDay(LocalDateTime.now());
        this.accountPayable.setDueDate(LocalDateTime.now().minusDays(3));

        InterestCalculationRule interestCalculationRule = InterestCalculationRule.builder()
                .percentageAssessment(BigDecimal.valueOf(0.02))
                .percentageInterest(BigDecimal.valueOf(0.1))
                .build();

        this.accountPayable.setInterestCalculationRule(interestCalculationRule);
        this.accountPayable.generateIdRandom();
        this.accountPayable.generateTotalDaysLate();

        // -- 02_Ação
        this.accountPayable.calculateAssessment();

        // -- 03_Verificação_Validação
        Assertions.assertNotSame(this.accountPayable.getCorrectedValue(), this.accountPayable.getPayValue());
    }

    @Test
    void testCalculateAssessmentOverdueMoreThanThreeDaysRandom() {
        log.info("{} ", "#TEST: testCalculateAssessmentOverdueMoreThanThreeDaysRandom: ");

        // -- 01_Cenário
        this.accountPayable.setName(RandomUtil.generateIdentityNameRandom());
        this.accountPayable.setPayValue(RandomUtil.generateDecimalRandomValue(1000));
        this.accountPayable.setPayDay(LocalDateTime.now());
        this.accountPayable.setDueDate(LocalDateTime.now().minusDays(4));

        InterestCalculationRule interestCalculationRule = InterestCalculationRule.builder()
                .percentageAssessment(BigDecimal.valueOf(0.03))
                .percentageInterest(BigDecimal.valueOf(0.2))
                .build();

        this.accountPayable.setInterestCalculationRule(interestCalculationRule);
        this.accountPayable.generateIdRandom();
        this.accountPayable.generateTotalDaysLate();

        // -- 02_Ação
        this.accountPayable.calculateAssessment();

        // -- 03_Verificação_Validação
        Assertions.assertNotSame(this.accountPayable.getCorrectedValue(), this.accountPayable.getPayValue());
    }

    @Test
    void testCalculateAssessmentOverdueMoreThanFiveDaysRandom() {
        log.info("{} ", "#TEST: testCalculateAssessmentOverdueMoreThanFiveDaysRandom: ");

        // -- 01_Cenário
        this.accountPayable.setName(RandomUtil.generateIdentityNameRandom());
        this.accountPayable.setPayValue(RandomUtil.generateDecimalRandomValue(1000));
        this.accountPayable.setPayDay(LocalDateTime.now());
        this.accountPayable.setDueDate(LocalDateTime.now().minusDays(6));

        InterestCalculationRule interestCalculationRule = InterestCalculationRule.builder()
                .percentageAssessment(BigDecimal.valueOf(0.05))
                .percentageInterest(BigDecimal.valueOf(0.3))
                .build();

        this.accountPayable.setInterestCalculationRule(interestCalculationRule);
        this.accountPayable.generateIdRandom();
        this.accountPayable.generateTotalDaysLate();

        // -- 02_Ação
        this.accountPayable.calculateAssessment();

        // -- 03_Verificação_Validação
        Assertions.assertNotSame(this.accountPayable.getCorrectedValue(), this.accountPayable.getPayValue());
    }

    @Test
    void testCalculateAssessmentOverdueUpToThreeDays() {
        log.info("{} ", "#TEST: testCalculateAssessmentOverdueUpToThreeDays: ");

        // -- 01_Cenário
        this.accountPayable.setName(RandomUtil.generateIdentityNameRandom());
        this.accountPayable.setPayValue(BigDecimal.valueOf(415));
        this.accountPayable.setPayDay(LocalDateTime.now());
        this.accountPayable.setDueDate(LocalDateTime.now().minusDays(3));

        InterestCalculationRule interestCalculationRule = InterestCalculationRule.builder()
                .percentageAssessment(BigDecimal.valueOf(0.02))
                .percentageInterest(BigDecimal.valueOf(0.1))
                .build();

        this.accountPayable.setInterestCalculationRule(interestCalculationRule);
        this.accountPayable.generateIdRandom();
        this.accountPayable.generateTotalDaysLate();

        // -- 02_Ação
        BigDecimal expected = BigDecimal.valueOf(427.45).setScale(2, RoundingMode.HALF_DOWN);
        this.accountPayable.calculateAssessment();

        // -- 03_Verificação_Validação
        Assertions.assertEquals(expected, this.accountPayable.getCorrectedValue());
    }

    @Test
    void testCalculateAssessmentOverdueMoreThanThreeDays() {
        log.info("{} ", "#TEST: testCalculateAssessmentOverdueMoreThanThreeDays: ");

        // -- 01_Cenário
        this.accountPayable.setName(RandomUtil.generateIdentityNameRandom());
        this.accountPayable.setPayValue(BigDecimal.valueOf(580));
        this.accountPayable.setPayDay(LocalDateTime.now());
        this.accountPayable.setDueDate(LocalDateTime.now().minusDays(4));

        InterestCalculationRule interestCalculationRule = InterestCalculationRule.builder()
                .percentageAssessment(BigDecimal.valueOf(0.03))
                .percentageInterest(BigDecimal.valueOf(0.2))
                .build();

        this.accountPayable.setInterestCalculationRule(interestCalculationRule);
        this.accountPayable.generateIdRandom();
        this.accountPayable.generateTotalDaysLate();

        // -- 02_Ação
        BigDecimal expected = BigDecimal.valueOf(612.87).setScale(2, RoundingMode.HALF_DOWN);
        this.accountPayable.calculateAssessment();

        // -- 03_Verificação_Validação
        Assertions.assertEquals(expected, this.accountPayable.getCorrectedValue());
    }

    @Test
    void testCalculateAssessmentOverdueMoreThanFiveDays() {
        log.info("{} ", "#TEST: testCalculateAssessmentOverdueMoreThanFiveDays: ");

        // -- 01_Cenário
        this.accountPayable.setName(RandomUtil.generateIdentityNameRandom());
        this.accountPayable.setPayValue(BigDecimal.valueOf(200));
        this.accountPayable.setPayDay(LocalDateTime.now());
        this.accountPayable.setDueDate(LocalDateTime.now().minusDays(6));

        InterestCalculationRule interestCalculationRule = InterestCalculationRule.builder()
                .percentageAssessment(BigDecimal.valueOf(0.05))
                .percentageInterest(BigDecimal.valueOf(0.3))
                .build();

        this.accountPayable.setInterestCalculationRule(interestCalculationRule);
        this.accountPayable.generateIdRandom();
        this.accountPayable.generateTotalDaysLate();

        // -- 02_Ação
        BigDecimal expected = BigDecimal.valueOf(222.00).setScale(2, RoundingMode.HALF_DOWN);
        this.accountPayable.calculateAssessment();

        // -- 03_Verificação_Validação
        Assertions.assertEquals(expected, this.accountPayable.getCorrectedValue());
    }

}
