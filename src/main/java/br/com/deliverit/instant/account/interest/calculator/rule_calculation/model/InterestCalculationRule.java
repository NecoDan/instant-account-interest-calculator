package br.com.deliverit.instant.account.interest.calculator.rule_calculation.model;

import lombok.*;
import lombok.experimental.Tolerate;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@ToString
@Builder
@AllArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "interest_calculation_rule", schema = "account_manager")
@Inheritance(strategy = InheritanceType.JOINED)
public class InterestCalculationRule implements Serializable {

    private static final long serialVersionUID = -8944959776388684739L;

    @Tolerate
    public InterestCalculationRule() {
        super();
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "days_late")
    private Integer daysLate;

    @Column(name = "descprition")
    private String description;

    @Column(name = "operator")
    private String operator;

    @Column(name = "percent_fine")
    private BigDecimal percentageFine;

    @Column(name = "percent_interest")
    private BigDecimal percentageInterest;

}

