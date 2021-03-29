package br.com.deliverit.instant.account.interest.calculator.account.model;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

/**
 * @author Daniel Santos
 */
public interface DaysCalculate {

    static Integer qtdeDiasPorIntervaloDatas(LocalDateTime valueDate1, LocalDateTime valueDate2) {
        validarParamsIntervaloDatasInvalido(valueDate1, valueDate2);
        return Math.toIntExact(ChronoUnit.DAYS.between(valueDate1, valueDate2));
    }

    static void validarParamsIntervaloDatasInvalido(LocalDateTime valueDate1, LocalDateTime valueDate2) {
        if (isParamsIntervaloDatasInvalido(valueDate1, valueDate2))
            throw new IllegalArgumentException("Parametros do intervalo de data(s) encontram-se inválida(s) e/ou inexistente(s).");
    }

    static boolean isParamsIntervaloDatasInvalido(LocalDateTime valueDate1, LocalDateTime valueDate2) {
        return (Objects.isNull(valueDate1) || Objects.isNull(valueDate2));
    }
}
