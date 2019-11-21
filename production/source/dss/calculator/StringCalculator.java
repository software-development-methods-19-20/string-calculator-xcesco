package dss.calculator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.regex.Pattern;

public class StringCalculator {

    private final static String DIVIDER_BETWEEN_SEPARATORS_AND_NUMBERS = "\n";
    private final static String BEGIN_SEPARATOR_PART = "//";
    private final static String COMMA_SEPARATOR = ",";

    public static int add(String input) {
        String numbersPart = extractNumbersAndReplaceSeparators(input);
        List<String> negativeNumberStrings = new ArrayList<>();

        int value = numbersPart.isEmpty() ? 0 : sumValidAndCollectNegativeNumbers(numbersPart, negativeNumberStrings);

        if (negativeNumberStrings.size() > 0) {
            throw new RuntimeException("Negatives not allowed " + String.join(Pattern.quote(COMMA_SEPARATOR), negativeNumberStrings));
        }

        return value;
    }

    private static Integer sumValidAndCollectNegativeNumbers(String numbersPart, List<String> negativeNumberStrings) {
        Consumer<Integer> collectNegativeNumbers = value -> {
            if (value < 0) negativeNumberStrings.add("" + value);
        };

        return Arrays.stream(numbersPart.split(Pattern.quote(COMMA_SEPARATOR)))
                .map(Integer::valueOf)
                .filter(item -> item < 1000)
                .peek(collectNegativeNumbers)
                .reduce(0, Integer::sum);
    }

    private static String extractNumbersAndReplaceSeparators(String input) {
        String numbersPart = input;
        if (input.startsWith(BEGIN_SEPARATOR_PART) && input.indexOf(DIVIDER_BETWEEN_SEPARATORS_AND_NUMBERS) > 0) {
            numbersPart = extractCustomSeparators(input);
        } else {
            numbersPart = numbersPart.replaceAll("\n", COMMA_SEPARATOR);
        }
        return numbersPart;
    }

    private static String extractCustomSeparators(String input) {
        final String SEPARATOR_BEGIN = "[";
        final String SEPARATOR_END = "]";

        int indexOfBeginNumbers = input.indexOf(DIVIDER_BETWEEN_SEPARATORS_AND_NUMBERS);
        String separatorPart = input.substring(BEGIN_SEPARATOR_PART.length(), indexOfBeginNumbers);
        String numbersPart = input.substring(indexOfBeginNumbers + DIVIDER_BETWEEN_SEPARATORS_AND_NUMBERS.length());

        if (separatorPart.startsWith(SEPARATOR_BEGIN) && separatorPart.endsWith(SEPARATOR_END)) {
            separatorPart = separatorPart.substring(1, separatorPart.length() - 1);
        }
        for (String item : separatorPart.split(Pattern.quote(SEPARATOR_END + SEPARATOR_BEGIN))) {
            numbersPart = numbersPart.replaceAll(Pattern.quote(item), COMMA_SEPARATOR);
        }
        return numbersPart;
    }
}
