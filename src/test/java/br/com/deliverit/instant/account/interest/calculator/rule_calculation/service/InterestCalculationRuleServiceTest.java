package br.com.deliverit.instant.account.interest.calculator.rule_calculation.service;

import br.com.deliverit.instant.account.interest.calculator.rule_calculation.model.InterestCalculationRule;
import br.com.deliverit.instant.account.interest.calculator.rule_calculation.repository.InterestCalculationRuleRepository;
import br.com.deliverit.instant.account.interest.calculator.util.RandomUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@Slf4j
class InterestCalculationRuleServiceTest {

    @Mock
    private InterestCalculationRuleRepository interestCalculationRuleRepository;
    @Spy
    @InjectMocks
    private InterestCalculationRuleService interestCalculationRuleService;

    @BeforeEach
    @SneakyThrows
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    private void resetarMocks() {
        reset(interestCalculationRuleRepository);
        reset(interestCalculationRuleService);
    }

    @Test
    void testFindById() {
        log.info("{} ", "#TEST: testFindById: ");

        // -- 01_Cenário
        resetarMocks();
        Long id = Long.valueOf(RandomUtil.generateRandomValueUntil(50000));
        InterestCalculationRule interestCalculationRule = mock(InterestCalculationRule.class);

        // -- 02_Ação
        doCallRealMethod().when(interestCalculationRuleService).findById(any(Long.class));
        when(interestCalculationRuleService.findById(id)).thenReturn(Optional.of(interestCalculationRule));

        // -- 03_Verificação_Validação
        assertTrue(interestCalculationRuleService.findById(id).isPresent());
    }

    @Test
    void testFindAll() {
        log.info("{} ", "#TEST: testFindAll:");

        // -- 01_Cenário
        resetarMocks();
        List<InterestCalculationRule> interestCalculationRuleList = Arrays.asList(mock(InterestCalculationRule.class),
                mock(InterestCalculationRule.class), mock(InterestCalculationRule.class),
                mock(InterestCalculationRule.class), mock(InterestCalculationRule.class));

        // -- 02_Ação
        doCallRealMethod().when(interestCalculationRuleService).findAll();
        when(interestCalculationRuleService.findAll()).thenReturn(interestCalculationRuleList);

        // -- 03_Verificação_Validação
        assertEquals(interestCalculationRuleList, interestCalculationRuleService.findAll());
    }
}
