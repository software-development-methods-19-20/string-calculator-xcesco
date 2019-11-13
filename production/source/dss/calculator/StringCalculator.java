package dss.calculator;

import java.util.Arrays;

public class StringCalculator {
    public static int add(String numbers) {
        return numbers.isEmpty() ? 0 : Arrays.stream(numbers.split(","))
                .map(Integer::valueOf)
                .reduce(0, Integer::sum);
    }
}
