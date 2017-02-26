import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Stack;
import java.util.StringTokenizer;


public class StringCalculator {

    public static void main(String[] args) {
            try( BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
                System.out.println("Введите выражение: ");
                String input = br.readLine();
                System.out.println("Исходное выражение в инфиксной нотации: " + input);
                input = parseInputString(input);
                System.out.println("Исходное выражение в обратной польской нотации: " + input);
                System.out.println("Результат: " + calculate(input));
            } catch (Exception e) {
                System.out.print("Ошибка ввода: ");
                System.out.println(e.getMessage());
            }
    }

    private static String parseInputString(String s) throws Exception {
        Stack<Character> stack = new Stack<>();
        StringBuilder result = new StringBuilder("");

        char nextChar;
        char previousOperator;

        for (int i = 0; i < s.length(); i++) {

            nextChar = s.charAt(i);

            if (isOperator(nextChar)) {
                while (!stack.empty()) {
                    previousOperator = stack.peek();
                    if ((checkPriority(nextChar) <= checkPriority(previousOperator))) {
                        result.append(" ").append(previousOperator).append(" ");
                        stack.setSize(stack.size()-1);
                    } else {
                        result.append(" ");
                        break;
                    }
                }
                result.append(" ");
                stack.push(nextChar);
            } else {
                result.append(nextChar);
            }
        }

        while (!stack.empty()) {
            result.append(" ").append(stack.pop());
        }

        return  result.toString();
    }

    private static boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }


    private static int checkPriority(char op) {
        if (op == '*' || op == '/') return 1;
        return 0;
    }


    private static double calculate(String s) throws Exception {
        double operand1 = 0;
        double operand2 = 0;
        String next;
        Stack<Double> stack = new Stack<Double>();
        StringTokenizer st = new StringTokenizer(s);

        while(st.hasMoreTokens()) {
                next = st.nextToken().trim();
                if (1 == next.length() && isOperator(next.charAt(0))) {
                    if (stack.size() < 2) {
                        throw new Exception("Неверное количество элементов в стеке!");
                    }
                    operand2 = stack.pop();
                    operand1 = stack.pop();
                    switch (next.charAt(0)) {
                        case '+':
                            operand1 += operand2;
                            break;
                        case '-':
                            operand1 -= operand2;
                            break;
                        case '/':
                            operand1 /= operand2;
                            break;
                        case '*':
                            operand1 *= operand2;
                            break;
                    }
                    stack.push(operand1);
                } else {
                    if(next.startsWith(".")) throw new Exception("Неверная форма записи числа с плавающей точкой!");
                    operand1 = Double.parseDouble(next);
                    stack.push(operand1);
                }
        }

        if (stack.size() > 1) {
            throw new Exception("Неверное количество операндов!");
        }

        return stack.pop();
    }
}