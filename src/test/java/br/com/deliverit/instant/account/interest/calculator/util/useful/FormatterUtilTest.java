package br.com.deliverit.instant.account.interest.calculator.util.useful;

import br.com.deliverit.instant.account.interest.calculator.util.AccountPayableCreator;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;

import java.time.Instant;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
class FormatterUtilTest {

    @Test
    void testFormatterLocalDateTimeFrom() {
        Assertions.assertNotNull(FormatterUtil.formatterLocalDateTimeFrom(Instant.now()));
    }

    @Test
    void testFormatterLocalDateFrom() {
        Assertions.assertNotNull(FormatterUtil.formatterLocalDateFrom(Instant.now()));
    }

    @Test
    void testRemoveNonNumericCharacters() {
        String input = "712.896.880-60";
        String result = "71289688060";
        Assertions.assertEquals(result, FormatterUtil.removeNonNumericCharacters(input));
    }

    @Test
    @SneakyThrows
    void testFormatterContentJsonFrom() {
        // -- 01_Cenário
        ObjectMapper objectMapper = AccountPayableCreator.getInicializeObjectMapper();
        String input = objectMapper.writeValueAsString(AccountPayableCreator.getAccountPayable());

        // -- 02_Ação
        String result = FormatterUtil.formatterContentJsonFrom(input);

        // -- 03_Verificação_Validação
        Assertions.assertTrue(Objects.nonNull(result) && StringUtils.isNotBlank(result) && result.contains("\n"));
    }

    @Test
    void testShouldThrowExceptionWhenTryingToFormatterLocalDateTimeBy() {
        log.info("{} ", "#TEST: testShouldThrowExceptionWhenTryingToFormatterLocalDateTimeBy: ");

        // -- 01_Cenário
        String msg = "Parameter referring to {DATE}, is invalid and/or non-existent.";

        // -- 02_Ação
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> FormatterUtil.formatterLocalDateTimeBy(null));

        // -- 03_Verificação_Validação
        assertTrue(e.getMessage().contains(msg));
        log.info("{} ", "EXCEPTION: ".concat(e.getMessage()));
    }

    @Test
    void testShouldThrowExceptionWhenTryingToFormatterLocalDateBy() {
        log.info("{} ", "#TEST: testShouldThrowExceptionWhenTryingToFormatterLocalDateTimeBy: ");

        // -- 01_Cenário
        String msg = "Parameter referring to {DATE}, is invalid and/or non-existent.";

        // -- 02_Ação
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> FormatterUtil.formatterLocalDateBy(null));

        // -- 03_Verificação_Validação
        assertTrue(e.getMessage().contains(msg));
        log.info("{} ", "EXCEPTION: ".concat(e.getMessage()));
    }
}
