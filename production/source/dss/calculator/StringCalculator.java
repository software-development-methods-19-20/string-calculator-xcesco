package dss.calculator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StringCalculator {
    static final String SEPARATOR_PARTS = "\\n";
    static final String BEGIN_SEPARATOR_PART = "//";

    public static int add(String numbers) {
        String separator = "[,\n]";
        if (numbers.startsWith(BEGIN_SEPARATOR_PART) && numbers.indexOf(SEPARATOR_PARTS) > 0) {
            int indexOfBeginNumbers = numbers.indexOf(SEPARATOR_PARTS);
            separator = numbers.substring(BEGIN_SEPARATOR_PART.length(), indexOfBeginNumbers);
            numbers = numbers.substring(indexOfBeginNumbers + SEPARATOR_PARTS.length());
        }
        List<String> negativeNumberStrings = new ArrayList<>();
        int value = numbers.isEmpty() ? 0 : Arrays.stream(numbers.split(separator))
                .map(Integer::valueOf)
                .filter(item -> item < 1000)
                .map(item -> {
                    if (item < 0) negativeNumberStrings.add("" + item);
                    return item;
                })
                .reduce(0, Integer::sum);

        if (negativeNumberStrings.size() > 0) {
            throw new RuntimeException("Negatives not allowed " + String.join(",", negativeNumberStrings));
        }

        return value;
    }
}
