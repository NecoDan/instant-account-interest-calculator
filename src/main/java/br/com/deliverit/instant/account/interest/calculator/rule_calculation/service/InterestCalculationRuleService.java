package br.com.deliverit.instant.account.interest.calculator.rule_calculation.service;

import br.com.deliverit.instant.account.interest.calculator.rule_calculation.model.InterestCalculationRule;
import br.com.deliverit.instant.account.interest.calculator.rule_calculation.repository.InterestCalculationRuleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class InterestCalculationRuleService implements IInterestCalculationRuleService {

    private final InterestCalculationRuleRepository interestCalculationRuleRepository;

    @Override
    public Optional<InterestCalculationRule> findById(Long id) {
        return interestCalculationRuleRepository.findById(id);
    }

    @Override
    public List<InterestCalculationRule> findAll() {
        return this.interestCalculationRuleRepository.findAll();
    }
}
