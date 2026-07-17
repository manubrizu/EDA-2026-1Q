import java.util.Scanner;
import java.util.Stack;

public class EvaluatorPostija {

	
 	private Scanner scannerLine;
	

	public EvaluatorPostija()
	{
	    Scanner inputScanner = new Scanner(System.in).useDelimiter("\\n");
	    System.out.print("Introduzca la expresion en notacion postfija: ");
	    inputScanner.hasNextLine();

	    String line = inputScanner.nextLine();

	    scannerLine = new Scanner(line).useDelimiter("\\s+");

	}
	
	public Double evaluate()
	{
        Stack<Double> auxi = new Stack<Double>();

        while (scannerLine.hasNext()) {
            String token = scannerLine.next();

            if (token.matches("-?\\d+(\\.\\d+)?")) {
                auxi.push(Double.parseDouble(token));
            }
            else if (token.matches("[+\\-*/]")) {
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
                }
            }
            else {
                throw new IllegalArgumentException("Token inválido: " + token);
            }
        }

        if (auxi.size() != 1) {
            throw new IllegalArgumentException("Expresión postfija inválida");
        }

        return auxi.pop();
    }

    public static void main(String[] args) {
        Double rta = new EvaluatorPostija().evaluate();
        System.out.println(rta);
    }
}
