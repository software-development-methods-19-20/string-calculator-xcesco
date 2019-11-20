package test;

import dss.calculator.StringCalculator;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AddNumbersTest {

    @Test
    void emptyString() {
        assertThat(StringCalculator.add(""), is(0));
    }

    @Test
    void oneNumber() {
        assertThat(StringCalculator.add("1"), is(1));
    }

    @Test
    void twoNumber() {
        assertThat(StringCalculator.add("1,2"), is(3));
    }

    @Test
    void fiveNumber() {
        assertThat(StringCalculator.add("1,2,3,4,5"), is(15));
    }

    @Test
    void supportForNewLine() {
        assertThat(StringCalculator.add("1\n2,3"), is(6));
    }

    @Test
    void supportForNewLineBadInput() {
        assertThat(StringCalculator.add("1,\n"), is(1));
    }

    @Test
    void supportCustomDelimiter() {
        assertThat(StringCalculator.add("//;\\n1;2"), is(3));
    }

    @Test
    void negativeNumberWithCustomDelimiter() {
        RuntimeException exception=assertThrows(RuntimeException.class, () -> {
            StringCalculator.add("//;\\n-1;2");
        });
        assertThat(exception.getMessage(), is("Negatives not allowed -1"));
    }

    @Test
    void negativeNumberWithStandardDelimiter() {
        RuntimeException exception=assertThrows(RuntimeException.class, () -> {
            StringCalculator.add("-1,2");
        });
        assertThat(exception.getMessage(), is("Negatives not allowed -1"));
    }

}
