package calculator;

public class Calculator {
    private double result;

    public static boolean isSupported(char op) {
        return op == '+' || op == '-' || op == '*' || op == '/';
    }

    public double apply(char op, double operand) {
        switch (op) {
            case '+':
                result += operand;
                break;
            case '-':
                result -= operand;
                break;
            case '*':
                result *= operand;
                break;
            case '/':
                if (operand == 0) {
                    throw new ArithmeticException("Деление на ноль");
                }
                result /= operand;
                break;
            default:
                throw new IllegalArgumentException("Неподдерживаемая операция: " + op);
        }
        return result;
    }

    public double getResult() {
        return result;
    }

    public void setResult(double result) {
        this.result = result;
    }

    public void reset() {
        result = 0;
    }
}
