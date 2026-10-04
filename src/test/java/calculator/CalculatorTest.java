package calculator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CalculatorTest {
    @Test
    void addsOperand() {
        Calculator calculator = new Calculator();
        calculator.setResult(2);
        assertEquals(5, calculator.apply('+', 3));
        assertEquals(5, calculator.getResult());
    }

    @Test
    void subtractsOperand() {
        Calculator calculator = new Calculator();
        calculator.setResult(2);
        assertEquals(-1, calculator.apply('-', 3));
    }

    @Test
    void multipliesByOperand() {
        Calculator calculator = new Calculator();
        calculator.setResult(5);
        assertEquals(20, calculator.apply('*', 4));
    }

    @Test
    void dividesByOperand() {
        Calculator calculator = new Calculator();
        calculator.setResult(5);
        assertEquals(2.5, calculator.apply('/', 2));
    }

    @Test
    void divisionByZeroThrowsAndKeepsResult() {
        Calculator calculator = new Calculator();
        calculator.setResult(7);
        assertThrows(ArithmeticException.class, () -> calculator.apply('/', 0));
        assertEquals(7, calculator.getResult());
    }

    @Test
    void unknownOperatorThrowsAndKeepsResult() {
        Calculator calculator = new Calculator();
        calculator.setResult(7);
        assertThrows(IllegalArgumentException.class, () -> calculator.apply('^', 2));
        assertEquals(7, calculator.getResult());
    }

    @Test
    void resetSetsResultToZero() {
        Calculator calculator = new Calculator();
        calculator.setResult(7);
        calculator.reset();
        assertEquals(0, calculator.getResult());
    }
}
