package br.com.deliverit.instant.account.interest.calculator.rule_calculation.service;

import br.com.deliverit.instant.account.interest.calculator.rule_calculation.model.InterestCalculationRule;

import java.util.List;
import java.util.Optional;

public interface IInterestCalculationRuleService {

    Optional<InterestCalculationRule> findById(Long id);

    List<InterestCalculationRule> findAll();
}
