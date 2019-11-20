package test;

import dss.calculator.StringCalculator;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

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

}
