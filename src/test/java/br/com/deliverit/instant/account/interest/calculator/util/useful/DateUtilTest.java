package br.com.deliverit.instant.account.interest.calculator.util.useful;

import br.com.deliverit.instant.account.interest.calculator.util.AccountPayableCreator;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
class DateUtilTest {

    @Test
    void getLocalDateFrom() {
        // -- 01_Cenário
        Instant instantInput = Instant.now();

        // -- 02_Ação
        LocalDate localDateResult = DateUtil.getLocalDateFrom(instantInput);

        // -- 03_Verificação_Validação
        Assertions.assertEquals(FormatterUtil.formatterLocalDateBy(localDateResult), FormatterUtil.formatterLocalDateFrom(instantInput));
    }

    @Test
    void getLocalDateTimeFrom() {
        // -- 01_Cenário
        Instant instantInput = Instant.now();

        // -- 02_Ação
        LocalDateTime localDateTimeResult = DateUtil.getLocalDateTimeFrom(instantInput);

        // -- 03_Verificação_Validação
        Assertions.assertEquals(FormatterUtil.formatterLocalDateTimeBy(localDateTimeResult), FormatterUtil.formatterLocalDateTimeFrom(instantInput));
    }

    @Test
    void testShouldThrowExceptionWhenTryingToGetLocalDateFrom() {
        log.info("{} ", "#TEST: testShouldThrowExceptionWhenTryingToGetLocalDateFrom: ");

        // -- 01_Cenário
        String msg = "Parameter referring to {DATE}, is invalid and/or non-existent.";

        // -- 02_Ação
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> DateUtil.getLocalDateFrom(null));

        // -- 03_Verificação_Validação
        assertTrue(e.getMessage().contains(msg));
        log.info("{} ", "EXCEPTION: ".concat(e.getMessage()));
    }

    @Test
    void testShouldThrowExceptionWhenTryingToGetLocalDateTimeFrom() {
        log.info("{} ", "#TEST: testShouldThrowExceptionWhenTryingToGetLocalDateTimeFrom: ");

        // -- 01_Cenário
        String msg = "Parameter referring to {DATE}, is invalid and/or non-existent.";

        // -- 02_Ação
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> DateUtil.getLocalDateTimeFrom(null));

        // -- 03_Verificação_Validação
        assertTrue(e.getMessage().contains(msg));
        log.info("{} ", "EXCEPTION: ".concat(e.getMessage()));
    }
}
