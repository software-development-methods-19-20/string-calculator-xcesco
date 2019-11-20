package dss.calculator;

import java.util.Arrays;

public class StringCalculator {
    static final String SEPARATOR_PARTS="\\n";
    static final String BEGIN_SEPARATOR_PART="//";

    public static int add(String numbers) {
        String separator="[,\n]";
        if (numbers.startsWith(BEGIN_SEPARATOR_PART) && numbers.indexOf(SEPARATOR_PARTS)>0) {
            int indexOfBeginNumbers=numbers.indexOf(SEPARATOR_PARTS);
            separator=numbers.substring(BEGIN_SEPARATOR_PART.length(), indexOfBeginNumbers);
            numbers=numbers.substring(indexOfBeginNumbers+SEPARATOR_PARTS.length());
        }
        return numbers.isEmpty() ? 0 : Arrays.stream(numbers.split(separator))
                .map(Integer::valueOf)
                .reduce(0, Integer::sum);
    }
}
