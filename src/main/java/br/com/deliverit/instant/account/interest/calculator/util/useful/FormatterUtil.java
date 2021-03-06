package br.com.deliverit.instant.account.interest.calculator.util.useful;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * @author Daniel Santos
 * {@link FormatterUtil} classe útil que por motivação e finalidade múltiplas operações envolvendo formatações retornando
 * o conteúdo em  {@link String}
 * @since 27/03/2021
 */
public final class FormatterUtil {

    private static final String VALIDATION_MESSAGE = "Parameter referring to {DATE}, is invalid and/or non-existent.";

    private FormatterUtil() {
    }

    public static String formatterLocalDateTimeBy(LocalDateTime data, String strFormato) {
        if (Objects.isNull(data))
            throw new IllegalArgumentException(VALIDATION_MESSAGE);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(strFormato);
        return data.format(formatter);
    }

    public static String formatterLocalDateBy(LocalDate data, String strFormato) {
        if (Objects.isNull(data))
            throw new IllegalArgumentException(VALIDATION_MESSAGE);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(strFormato);
        return data.format(formatter);
    }

    public static String formatterLocalDateBy(LocalDate data) {
        return formatterLocalDateBy(data, "dd/MM/yyyy");
    }

    public static String formatterLocalDateTimeBy(LocalDateTime data) {
        return formatterLocalDateTimeBy(data, "dd/MM/yyyy HH:mm:ss");
    }

    public static String formatterLocalDateTimeFrom(Instant instant) {
        return formatterLocalDateTimeBy(DateUtil.getLocalDateTimeFrom(instant));
    }

    public static String formatterLocalDateFrom(Instant instant) {
        return formatterLocalDateBy(DateUtil.getLocalDateFrom(instant));
    }

    public static String removeNonNumericCharacters(String str) {
        return (Objects.isNull(str) || str.isEmpty()) ? "" : str.replaceAll("[^\\d]", "");
    }

    public static String formatterContentJsonFrom(String conteudo) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonParser jp = new JsonParser();
        JsonElement je = jp.parse(conteudo);
        return gson.toJson(je);
    }
}

