package br.com.deliverit.instant.account.interest.calculator.rule_calculation.repository;

import br.com.deliverit.instant.account.interest.calculator.rule_calculation.model.InterestCalculationRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InterestCalculationRuleRepository extends JpaRepository<InterestCalculationRule, Long> {

}
