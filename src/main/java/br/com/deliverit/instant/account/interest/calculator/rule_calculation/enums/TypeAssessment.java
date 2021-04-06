package br.com.deliverit.instant.account.interest.calculator.rule_calculation.enums;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

public enum TypeAssessment {

    NONE(1),

    TWO_PERCENT(2),

    THREE_PERCENT(3),

    FIVE_PERCENT(4);

    private static final Map<Integer, TypeAssessment> lookup;

    static {
        lookup = new HashMap<>();
        EnumSet<TypeAssessment> enumSet = EnumSet.allOf(TypeAssessment.class);

        for (TypeAssessment type : enumSet)
            lookup.put(type.code, type);
    }

    private int code;

    TypeAssessment(int code) {
        inicialize(code);
    }

    private void inicialize(int code) {
        this.code = code;
    }

    public static TypeAssessment from(int code) {
        if (lookup.containsKey(code))
            return lookup.get(code);
        throw new IllegalArgumentException(String.format("Assessment type code is invalid: %d", code));
    }

    public static TypeAssessment of(int code) {
        return Stream.of(TypeAssessment.values())
                .filter(p -> p.getCode() == code)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

    public int getCode() {
        return this.code;
    }

    public boolean isNone() {
        return (Objects.equals(this, NONE));
    }
}
