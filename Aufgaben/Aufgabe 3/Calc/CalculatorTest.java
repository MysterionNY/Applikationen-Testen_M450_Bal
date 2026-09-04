package ch.tbz.calculator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {

    private Calculator calculator;

    @BeforeEach
    void setUp() {
        calculator = new Calculator();
    }

    @Test
    @DisplayName("Zwei positive Zahlen addieren")
    void addTwoPositiveNumbers() {
        double result = calculator.add(10, 5);

        assertEquals(15, result);
    }

    @Test
    @DisplayName("Positive und negative Zahl addieren")
    void addPositiveAndNegativeNumber() {
        double result = calculator.add(10, -5);

        assertEquals(5, result);
    }

    @Test
    @DisplayName("Zwei Zahlen subtrahieren")
    void subtractTwoNumbers() {
        double result = calculator.subtract(10, 5);

        assertEquals(5, result);
    }

    @Test
    @DisplayName("Subtraktion mit negativem Ergebnis")
    void subtractWithNegativeResult() {
        double result = calculator.subtract(5, 10);

        assertEquals(-5, result);
    }

    @Test
    @DisplayName("Zwei Zahlen multiplizieren")
    void multiplyTwoNumbers() {
        double result = calculator.multiply(10, 5);

        assertEquals(50, result);
    }

    @Test
    @DisplayName("Multiplikation mit Null")
    void multiplyWithZero() {
        double result = calculator.multiply(10, 0);

        assertEquals(0, result);
    }

    @Test
    @DisplayName("Zwei Zahlen dividieren")
    void divideTwoNumbers() {
        double result = calculator.divide(10, 2);

        assertEquals(5, result);
    }

    @Test
    @DisplayName("Division mit Dezimalergebnis")
    void divideWithDecimalResult() {
        double result = calculator.divide(5, 2);

        assertEquals(2.5, result);
    }

    @Test
    @DisplayName("Division durch Null verhindern")
    void divisionByZeroThrowsException() {
        assertThrows(
                IllegalArgumentException.class,
                () -> calculator.divide(10, 0)
        );
    }
}