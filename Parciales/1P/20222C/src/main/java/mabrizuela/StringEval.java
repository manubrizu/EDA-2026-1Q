package mabrizuela;

import java.util.Scanner;
import java.util.Stack;

public class StringEval {

    public StringEval() {
        // Constructor vacío para poder instanciar la clase
    }

    public String evaluate(String expression){

        Stack<String> auxi = new Stack<>();
        Scanner scannerLine = new Scanner(expression).useDelimiter("\\s+");

        while (scannerLine.hasNext()) {
            String token = scannerLine.next();

            if (token.matches("[a-zA-Z]+")) {
                auxi.push(token);
            }
            else if (token.matches("[+\\-*/\\^]")) {
                if (auxi.size() < 2) {
                    throw new IllegalArgumentException("Expresión postfija inválida");
                }

                String b = auxi.pop();
                String a = auxi.pop();

                switch (token) {
                    case "+":
                        auxi.push(a + b);
                        break;
                    case "-":
                        auxi.push(a.replaceFirst(b, ""));
                        break;
                    case "*":
                        StringBuilder sb = new StringBuilder();
                        int i = 0;
                        int j = 0;

                        while (i < a.length() && j < b.length()) {
                            sb.append(a.charAt(i));
                            sb.append(b.charAt(j));
                            i++;
                            j++;
                        }

                        // agregar lo que sobra
                        while (i < a.length()) {
                            sb.append(a.charAt(i));
                            i++;
                        }

                        while (j < b.length()) {
                            sb.append(b.charAt(j));
                            j++;
                        }

                        auxi.push(sb.toString());
                        break;
                    case "/":
                        StringBuilder sb2 = new StringBuilder();

                        for (i = 0; i < a.length(); i++) {
                            char c = a.charAt(i);

                            if (b.indexOf(c) == -1) {
                                sb2.append(c);
                            }
                        }

                        auxi.push(sb2.toString());
                        break;
                    case "^":
                        StringBuilder sb3 = new StringBuilder();

                        for (i = 1; i <= b.length(); i++) {
                            sb3.append(a);
                            sb3.append(b.substring(0, i));
                        }

                        auxi.push(sb3.toString());
                        break;
                }
            }
            else {
                throw new IllegalArgumentException("Token inválido: " + token);
            }
        }

        if (auxi.size() != 1) {
            throw new IllegalArgumentException("Expresión postfija inválida");
        }

        scannerLine.close();
        return auxi.pop();

    }
}
