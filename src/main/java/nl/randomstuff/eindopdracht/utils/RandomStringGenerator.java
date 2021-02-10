package nl.randomstuff.eindopdracht.utils;

import java.util.Random;

public class RandomStringGenerator {

    public static String generateAlphaNumeric(int length) {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .filter(value -> (value <= 57 || value >= 65) && (value <= 90 || value >= 97))
                .limit(length)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}
