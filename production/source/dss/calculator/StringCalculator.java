package dss.calculator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.regex.Pattern;

public class StringCalculator {

    final static String DIVIDER_BETWEEN_SEPARATORS_AND_NUMBERS = "\n";
    final static String BEGIN_SEPARATOR_PART = "//";
    final static String COMMA_SEPARATOR = ",";

    public static int add(String input) {
        final String comma = ",";
        String numbersPart = extractNumbersAndReplaceSeparators(input);
        List<String> negativeNumberStrings = new ArrayList<>();

        Function<Integer, Integer> convertAndFindNegativeNumbers = value -> {
            if (value < 0) negativeNumberStrings.add("" + value);
            return value;
        };

        int value = numbersPart.isEmpty() ? 0 : Arrays.stream(numbersPart.split(comma))
                .map(Integer::valueOf)
                .filter(item -> item < 1000)
                .map(convertAndFindNegativeNumbers::apply)
                .reduce(0, Integer::sum);

        if (negativeNumberStrings.size() > 0) {
            throw new RuntimeException("Negatives not allowed " + String.join(",", negativeNumberStrings));
        }

        return value;
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
