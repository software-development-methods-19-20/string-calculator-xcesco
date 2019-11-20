package dss.calculator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.regex.Pattern;

public class StringCalculator {
    static final String SEPARATOR_PARTS = "\\n";
    static final String BEGIN_SEPARATOR_PART = "//";

    public static int add(String input) {
        final String comma = ",";
        String numbersPart = manageSplitterString(input);
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

    private static String manageSplitterString(String input) {
        String numbersPart = input;
        if (input.startsWith(BEGIN_SEPARATOR_PART) && input.indexOf(SEPARATOR_PARTS) > 0) {
            int indexOfBeginNumbers = input.indexOf(SEPARATOR_PARTS);
            String separatorPart = input.substring(BEGIN_SEPARATOR_PART.length(), indexOfBeginNumbers);
            numbersPart = input.substring(indexOfBeginNumbers + SEPARATOR_PARTS.length());

            if (separatorPart.startsWith("[") && separatorPart.endsWith("]")) {
                separatorPart = separatorPart.substring(1, separatorPart.length() - 1);
            }
            for (String item : separatorPart.split("\\]\\[")) {
                numbersPart = numbersPart.replaceAll(Pattern.quote(item), ",");
            }
        } else {
            numbersPart = numbersPart.replaceAll("\n", ",");
        }
        return numbersPart;
    }
}
