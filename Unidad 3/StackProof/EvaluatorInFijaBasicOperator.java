import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Stack;

public class EvaluatorInFijaBasicOperator {

    private static Map<String, Boolean> precedenceMap = new HashMap<String, Boolean>() {{
        put("+_+", true);   put("+_-", true);   put("+_*", false);  put("+_/", false);  put("+_^", false);  put("+_(", false);  put("+_)", true);
        put("-_+", true);   put("-_-", true);   put("-_*", false);  put("-_/", false);  put("-_^", false);  put("-_(", false);  put("-_)", true);
        put("*_+", true);   put("*_-", true);   put("*_*", true);   put("*_/", true);   put("*_^", false);  put("*_(", false);  put("*_)", true);
        put("/_+", true);   put("/_-", true);   put("/_*", true);   put("/_/", true);   put("/_^", false);  put("/_(", false);  put("/_)", true);
        put("^_+", true);   put("^_-", true);   put("^_*", true);   put("^_/", true);   put("^_^", false);  put("^_(", false);  put("^_)", true);
        put("(_+", false);  put("(_-", false);  put("(_*", false);  put("(_/", false);  put("(_^", false);  put("(_(", false);  put("(_)", false);
    }};

    private static final String extraSymbol = "_";
    private Scanner scannerLine;

    public EvaluatorInFijaBasicOperator() {
        Scanner input = new Scanner(System.in).useDelimiter("\\n");
        System.out.print("Introduzca la expresión en notación infija: ");
        String line = input.nextLine();

        scannerLine = new Scanner(line).useDelimiter("\\s+");
    }

    private boolean getPrecedence(String tope, String current) {
        Boolean rta = precedenceMap.get(tope + extraSymbol + current);
        if (rta == null) {
            throw new RuntimeException(
                    String.format("operator %s or %s not found", tope, current)
            );
        }
        return rta;
    }

    public Double evaluate() {
        String exp = infijaToPostfija();
        scannerLine = new Scanner(exp).useDelimiter("\\s+");

        Stack<Double> auxi = new Stack<>();

        while (scannerLine.hasNext()) {
            String token = scannerLine.next();

            if (isNumber(token)) {
                auxi.push(Double.parseDouble(token));
            } else if (isOperator(token)) {
                if (auxi.size() < 2) {
                    throw new IllegalArgumentException("Expresión postfija inválida");
                }

                double b = auxi.pop();
                double a = auxi.pop();

                switch (token) {
                    case "+":
                        auxi.push(a + b);
                        break;
                    case "-":
                        auxi.push(a - b);
                        break;
                    case "*":
                        auxi.push(a * b);
                        break;
                    case "/":
                        auxi.push(a / b);
                        break;
                    case "^":
                        auxi.push(Math.pow(a, b));
                        break;
                    default:
                        throw new IllegalArgumentException("Operador inválido: " + token);
                }
            } else {
                throw new IllegalArgumentException("Token inválido en postfija: " + token);
            }
        }

        if (auxi.size() != 1) {
            throw new IllegalArgumentException("Expresión postfija inválida");
        }

        return auxi.pop();
    }

    private String infijaToPostfija() {
        StringBuilder postfija = new StringBuilder();
        Stack<String> theStack = new Stack<>();

        while (scannerLine.hasNext()) {
            String currentToken = scannerLine.next();

            if (isNumber(currentToken)) {
                postfija.append(currentToken).append(" ");
            }
            else if (currentToken.equals("(")) {
                theStack.push(currentToken);
            }
            else if (currentToken.equals(")")) {
                // ")" desapila hasta encontrar "("
                boolean foundLeftParenthesis = false;

                while (!theStack.isEmpty()) {
                    String top = theStack.pop();

                    if (top.equals("(")) {
                        foundLeftParenthesis = true;
                        break;
                    }

                    postfija.append(top).append(" ");
                }

                if (!foundLeftParenthesis) {
                    throw new IllegalArgumentException("Error: falta (");
                }
            }
            else if (isOperator(currentToken)) {
                while (!theStack.isEmpty() && getPrecedence(theStack.peek(), currentToken)) {
                    postfija.append(theStack.pop()).append(" ");
                }
                theStack.push(currentToken);
            }
            else {
                throw new IllegalArgumentException("Token inválido: " + currentToken);
            }
        }

        while (!theStack.isEmpty()) {
            String top = theStack.pop();

            if (top.equals("(")) {
                throw new IllegalArgumentException("Error: falta )");
            }

            postfija.append(top).append(" ");
        }

        System.out.println("Postfija = " + postfija.toString().trim());
        return postfija.toString().trim();
    }

    private boolean isNumber(String token) {
        return token.matches("-?\\d+(\\.\\d+)?");
    }

    private boolean isOperator(String token) {
        return token.matches("[+\\-*/^]");
    }

    public static void main(String[] args) {
        EvaluatorInFijaBasicOperator e = new EvaluatorInFijaBasicOperator();
        System.out.println(e.evaluate());
    }
}