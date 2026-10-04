package calculator;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MainTest {
    private static String runWith(String input) {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(buffer, true, StandardCharsets.UTF_8);
        Main.run(new Scanner(input), out);
        return buffer.toString(StandardCharsets.UTF_8);
    }

    private static int count(String text, String part) {
        int count = 0;
        for (int i = text.indexOf(part); i >= 0; i = text.indexOf(part, i + 1)) {
            count++;
        }
        return count;
    }

    @Test
    void resultBecomesFirstOperandOfNextOperation() {
        String output = runWith("2 + 3 * 4 s");
        assertTrue(output.contains("Результат: 5"));
        assertTrue(output.contains("Результат: 20"));
    }

    @Test
    void resetAsksForFirstNumberAgain() {
        String output = runWith("2 + 3 c 7 + 1 s");
        assertTrue(output.contains("Результат: 8"));
        assertEquals(2, count(output, "Введите первое число"));
    }

    @Test
    void lowercaseResetWorks() {
        String output = runWith("2 + 3 c 7 - 1 s");
        assertTrue(output.contains("Результат: 6"));
    }

    @Test
    void uppercaseResetWorks() {
        String output = runWith("2 + 3 C 7 - 1 S");
        assertTrue(output.contains("Результат: 6"));
    }

    @Test
    void resetAsSecondOperandStartsOver() {
        String output = runWith("2 + c 9 + 1 s");
        assertTrue(output.contains("Результат: 10"));
        assertEquals(2, count(output, "Введите первое число"));
    }

    @Test
    void stopCommandIsCaseInsensitive() {
        assertTrue(runWith("2 s").contains("Выход."));
        assertTrue(runWith("2 S").contains("Выход."));
        assertTrue(runWith("S").contains("Выход."));
        assertTrue(runWith("2 + s").contains("Выход."));
    }

    @Test
    void stopEndsProgramBeforeFurtherInput() {
        String output = runWith("2 + 3 s + 4");
        assertTrue(output.contains("Результат: 5"));
        assertEquals(1, count(output, "Результат:"));
    }

    @Test
    void divisionByZeroReportsErrorAndKeepsResult() {
        String output = runWith("7 / 0 + 1 s");
        assertTrue(output.contains("деление на ноль"));
        assertTrue(output.contains("Результат: 8"));
    }

    @Test
    void unsupportedOperationReportsErrorAndKeepsState() {
        String output = runWith("2 ^ + 3 s");
        assertTrue(output.contains("неподдерживаемая операция: ^"));
        assertTrue(output.contains("Результат: 5"));
    }

    @Test
    void nonNumericFirstOperandIsRequestedAgain() {
        String output = runWith("abc 2 + 3 s");
        assertTrue(output.contains("«abc» не является числом"));
        assertTrue(output.contains("Результат: 5"));
    }

    @Test
    void nonNumericSecondOperandIsRequestedAgain() {
        String output = runWith("2 + x 3 s");
        assertTrue(output.contains("«x» не является числом"));
        assertTrue(output.contains("Результат: 5"));
    }

    @Test
    void endOfInputExitsGracefully() {
        assertTrue(runWith("").contains("Ввод завершён."));
        assertTrue(runWith("2 +").contains("Ввод завершён."));
        assertTrue(runWith("2 + 3").contains("Ввод завершён."));
    }

    @Test
    void wholeResultIsPrintedWithoutFraction() {
        String output = runWith("2 + 3 s");
        assertTrue(output.contains("Результат: 5" + System.lineSeparator()));
        assertTrue(!output.contains("5.0"));
    }

    @Test
    void fractionalResultIsPrintedAsDouble() {
        String output = runWith("5 / 2 s");
        assertTrue(output.contains("Результат: 2.5"));
    }
}
