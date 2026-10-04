package calculator;

import java.io.PrintStream;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        run(new Scanner(System.in), System.out);
    }

    static void run(Scanner in, PrintStream out) {
        Calculator calculator = new Calculator();
        boolean hasResult = false;
        try {
            while (true) {
                if (!hasResult) {
                    String token = readNumberOrCommand(in, out, "Введите первое число: ");
                    if (isCommand(token, 'c')) {
                        continue;
                    }
                    if (isCommand(token, 's')) {
                        out.println("Выход.");
                        return;
                    }
                    calculator.setResult(parseNumber(token));
                    hasResult = true;
                    continue;
                }

                String opToken = read(in, out, "Введите операцию (+, -, *, /), C - сброс, S - выход: ");
                if (isCommand(opToken, 'c')) {
                    calculator.reset();
                    hasResult = false;
                    continue;
                }
                if (isCommand(opToken, 's')) {
                    out.println("Выход.");
                    return;
                }
                if (opToken.length() != 1 || !Calculator.isSupported(opToken.charAt(0))) {
                    out.println("Ошибка: неподдерживаемая операция: " + opToken);
                    continue;
                }

                String operandToken = readNumberOrCommand(in, out, "Введите второе число: ");
                if (isCommand(operandToken, 'c')) {
                    calculator.reset();
                    hasResult = false;
                    continue;
                }
                if (isCommand(operandToken, 's')) {
                    out.println("Выход.");
                    return;
                }

                try {
                    double newResult = calculator.apply(opToken.charAt(0), parseNumber(operandToken));
                    out.println("Результат: " + format(newResult));
                } catch (ArithmeticException e) {
                    out.println("Ошибка: деление на ноль. Результат не изменён: " + format(calculator.getResult()));
                }
            }
        } catch (NoSuchElementException e) {
            out.println("Ввод завершён.");
        }
    }

    private static String read(Scanner in, PrintStream out, String prompt) {
        out.print(prompt);
        return in.next();
    }

    private static String readNumberOrCommand(Scanner in, PrintStream out, String prompt) {
        while (true) {
            String token = read(in, out, prompt);
            if (isCommand(token, 'c') || isCommand(token, 's') || parseNumber(token) != null) {
                return token;
            }
            out.println("Ошибка: «" + token + "» не является числом.");
        }
    }

    private static boolean isCommand(String token, char command) {
        return token.length() == 1 && Character.toLowerCase(token.charAt(0)) == command;
    }

    private static Double parseNumber(String token) {
        try {
            double value = Double.parseDouble(token);
            return Double.isNaN(value) || Double.isInfinite(value) ? null : value;
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private static String format(double value) {
        if (value == Math.rint(value) && Math.abs(value) < 1e15) {
            return String.valueOf((long) value);
        }
        return String.valueOf(value);
    }
}
