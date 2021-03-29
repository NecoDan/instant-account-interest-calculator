package br.com.deliverit.instant.account.interest.calculator.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * @author Daniel Santos
 */
public final class RandomUtil {

    private static final int LIMIT_MIN_RANDOM_INTEGER = 1;
    private static final double LIMIT_MIN_RANDOM_DOUBLE = 1D;

    private static final String LEXICON = "ABCDEFGHIJKLMNOPQRSTUVWXYZ12345674890";

    // consider using a Map<String,Boolean> to say whether the identifier is being used or not
    private static final Set<String> IDENTIFICADORES = new HashSet<>();

    private RandomUtil() {
    }

    private static Random getRandom() {
        return new Random();
    }

    public static Integer generateRandomValueUntil(int max) {
        return LIMIT_MIN_RANDOM_INTEGER + getRandom().nextInt(max);
    }

    public static BigDecimal generateDecimalRandomValue(double limiteMax) {
        return BigDecimal.valueOf(LIMIT_MIN_RANDOM_DOUBLE + getRandom().nextDouble() * (limiteMax - LIMIT_MIN_RANDOM_DOUBLE))
                .setScale(2, RoundingMode.HALF_UP);
    }

    public static String generateIdentityNameRandom() {
        StringBuilder builder = new StringBuilder();
        while (builder.toString().length() == 0) {
            int length = getRandom().nextInt(5) + 5;

            for (int i = 0; i < length; i++) {
                builder.append(LEXICON.charAt(getRandom().nextInt(LEXICON.length())));
            }

            if (IDENTIFICADORES.contains(builder.toString())) {
                builder = new StringBuilder();
            }
        }

        return builder.toString();
    }
}
