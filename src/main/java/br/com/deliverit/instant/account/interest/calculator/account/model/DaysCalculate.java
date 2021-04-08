package br.com.deliverit.instant.account.interest.calculator.account.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

/**
 * @author Daniel Santos
 */
public interface DaysCalculate {

    static Integer totalDaysOverdueBy(LocalDateTime valueDueDate, LocalDateTime valuePayDay) {
        validateDateRangeParameters(valueDueDate);
        return Math.toIntExact(ChronoUnit.DAYS.between(valueDueDate.toLocalDate(), valuePayDay.toLocalDate()));
    }

    static void validateDateRangeParameters(LocalDateTime valueDate) {
        if (isInvalidDateRangeParameters(valueDate))
            throw new IllegalArgumentException("Date range parameters are invalid.ß");
    }

    static boolean isInvalidDateRangeParameters(LocalDateTime valueDate) {
        return (Objects.isNull(valueDate));
    }
}
