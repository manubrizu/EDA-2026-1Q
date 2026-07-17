import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Stack;

public class EvaluatorSeguimientodePila {

    // Opción 1:
    // Mapa que asocia cada operador/paréntesis con un índice numérico.
    // Ese índice después se usa para consultar la matriz de precedencia.
    private static Map<String, Integer> mapping = new HashMap<String, Integer>() {
        { put("+", 0); put("-", 1); put("*", 2); put("/", 3); put("^", 4); put("(", 5); put(")", 6); }
    };

    // Opción 2:
    // Otra manera alternativa de modelar precedencia:
    // usar un HashMap cuyo key sea "tope_actual" y cuyo valor sea true/false
    // según si hay que desapilar o no.
    // Esta opción está comentada y no se usa en el código actual.
	/*
	private static Map<String, Boolean> precendeceMap= new HashMap<String, Boolean>()
	{	{
			put("+_+", true); put("+_-", true); put("+_*", false); put("+_/", false);
			put("-_+", true); put("-_-", true); put("-_*", false); put("-_/", false);
			put("*_+", true); put("*_-", true); put("*_*", true); put("*_/", true);
			put("/_+", true); put("/_-", true); put("/_*", true); put("/_/", true);
			put("^_+", true); put("^_-", true); put("^_*", true); put("^_/", true);
		}  };

	private final static String extraSymbol= "_";

	// Consulta la precedencia en el mapa alternativo.
	// Devuelve true si el operador del tope debe salir antes que el actual.
	private boolean getPrecedence2(String tope, String current)
	{
		Boolean rta= precendeceMap.get(String.format("%s%s%s", tope, extraSymbol, current));
		if (rta == null)
			throw new RuntimeException(String.format("operator %s or %s not found", tope, current));

		return rta;
	}
	*/

    // Matriz de precedencia.
    // Filas = operador que está en el tope de la pila
    // Columnas = operador actual leído
    // true  => hay que desapilar el tope
    // false => no hay que desapilar todavía
    private static boolean[][] precedenceMatriz= {
            { true,  true,  false, false, false,  false,  true },
            { true,  true,  false, false, false,  false,  true },
            { true,  true,  true,  true,  false,  false,  true  },
            { true,  true,  true,  true,  false,  false,  true },
            { true,  true,  true,  true,  false,  false,  true },
            { false, false, false, false, false,  false,  false },
    };

    // Scanner que se usa para recorrer la expresión token por token.
    private Scanner scannerLine;

    // Dado el operador del tope y el operador actual,
    // busca sus índices en el mapa y devuelve el valor de precedencia
    // consultando la matriz.
    private boolean getPrecedence(String tope, String current) {
        Integer topeIndex;
        Integer currentIndex;

        // Si el operador del tope no existe en el mapa, lanza error.
        if ((topeIndex= mapping.get(tope))== null)
            throw new RuntimeException(String.format("tope operator %s not found", tope));

        // Si el operador actual no existe en el mapa, lanza error.
        if ((currentIndex= mapping.get(current)) == null)
            throw new RuntimeException(String.format("current operator %s not found", current));

        // Devuelve si corresponde desapilar según la matriz.
        return precedenceMatriz[topeIndex][currentIndex];
    }

    // Constructor:
    // lee desde teclado una línea completa en notación infija
    // y prepara un Scanner para recorrerla separando por espacios.
    public EvaluatorSeguimientodePila()  {
        Scanner input = new Scanner(System.in).useDelimiter("\\n");
        System.out.print("Introduzca la expresión en notación infija: ");
        String line= input.nextLine();
        input.close();

        // Cada token debe estar separado por espacios.
        scannerLine = new Scanner(line).useDelimiter("\\s+");
    }



    // ----------- SOLO AGREGADO PARA SEGUIMIENTO POSTFIJA -----------

    private void printHeaderPostfija() {
        System.out.println("\nSeguimiento evaluación postfija");
        System.out.printf("%-10s %-30s%n", "TOKEN", "STACK");
        System.out.println("---------------------------------------------");
    }

    private void printStatePostfija(String token, Stack<Double> stack) {
        System.out.printf("%-10s %-30s%n", token, stack);
    }

// -------------------------------------------------------------




    // Método principal de evaluación.
    // 1) Convierte la expresión infija a postfija.
    // 2) Recorre la postfija y la evalúa usando una pila.
    public Double evaluate() {
        Stack<Double> auxi= new Stack<Double>();

        // Convierte a postfija.
        String exp = infijaToPostfija();

        // -------- agregado --------
        printHeaderPostfija();
        // -------------------------

        // Reutiliza scannerLine para recorrer ahora la expresión postfija.
        scannerLine = new Scanner(exp).useDelimiter("\\s+");

        while(scannerLine.hasNext()){
            String s = scannerLine.next();

            // Si el token es un operando, lo apila.
            if (isOperand(s)){
                auxi.push(Double.valueOf(s));
                printStatePostfija(s, auxi); // agregado
            }
            else{	// operador o error

                // Si no es operando pero tampoco operador válido, error.
                if (!isOperator(s))
                    throw new RuntimeException("Invalid operator " + s);

                // Saca el segundo operando.
                Double operand2;
                if (auxi.empty())
                    throw new RuntimeException("Operand missing");
                else
                    operand2= auxi.pop();

                // Saca el primer operando.
                Double operand1;
                if (auxi.empty())
                    throw new RuntimeException("Operand missing");
                else
                    operand1= auxi.pop();

                // Evalúa la operación y apila el resultado.
                Double result = eval(s, operand1 , operand2);
                auxi.push(result);

                printStatePostfija(s, auxi); // agregado
            }
        }

        // Al final debería quedar un único valor en la pila.
        double rta= auxi.pop();
        if (auxi.empty())
            return rta;

        // Si quedan más valores, faltó algún operador.
        throw new RuntimeException("Operator missing");
    }

    // Devuelve true si el string representa un número válido.
    private boolean isOperand(String s) {
        try
        {
            Double.valueOf(s);
        }
        catch(NumberFormatException e)
        {
            return false;
        }
        return true;
    }

    // Devuelve true si el string es uno de los operadores soportados.
    private boolean isOperator(String s) {
        return s.matches("\\+|-|\\*|/|\\^");
    }

    // Evalúa una operación binaria entre val1 y val2.
    private double eval(String op, double val1, double val2){
        switch (op){
            case "+": return val1 + val2;
            case "-": return val1 - val2;
            case "*": return val1 * val2;
            case "/": return val1 / val2;
            case "^": return Math.pow(val1, val2);
        };

        // Si el operador no es válido, lanza error.
        throw new RuntimeException("invalid operator" +  op);
    }

    // ----------- SOLO AGREGADO PARA SEGUIMIENTO -----------

    private void printHeader() {
        System.out.println("\nSeguimiento infija -> postfija");
        System.out.printf("%-10s %-20s %-20s%n", "TOKEN", "STACK", "OUTPUT");
        System.out.println("------------------------------------------------------------");
    }

    private void printState(String token, Stack<String> stack, String output) {
        System.out.printf("%-10s %-20s %-20s%n", token, stack, output);
    }

    // -----------------------------------------------------

    // Convierte una expresión infija a postfija usando una pila de operadores.
    private String infijaToPostfija(){
        String postfija= "";
        Stack<String> theStack= new Stack<String>();

        printHeader(); // agregado

        // Recorre todos los tokens de la expresión infija.
        while(scannerLine.hasNext()) {
            String current = scannerLine.next();

            // Si es operando, va directo a la salida postfija.
            if (isOperand(current)) {
                postfija += String.format("%s ", current);
                printState(current, theStack, postfija); // agregado
            } else {

                // Mientras la pila no esté vacía y el operador del tope
                // tenga precedencia sobre el operador actual, se desapila.
                while (!theStack.empty() && getPrecedence(theStack.peek(), current)) {
                    postfija += String.format("%s ", theStack.pop());
                    printState(current, theStack, postfija); // agregado
                }

                // Si el token actual es ")", intenta cerrar un paréntesis.
                if(current.equals(")"))
                    if(!theStack.empty() && theStack.peek().equals("(")) {
                        theStack.pop();
                        printState(current, theStack, postfija); // agregado
                    }
                    else
                        throw new RuntimeException("( operator missing");

                    // Si no es ")", se apila normalmente.
                else {
                    theStack.push(current);
                    printState(current, theStack, postfija); // agregado
                }

            }
        }

        // Al terminar de leer la expresión,
        // se desapilan los operadores restantes.
        while (!theStack.empty())
            if(theStack.peek().equals("("))
                throw new RuntimeException(") operator missing");
            else {
                postfija += String.format("%s ", theStack.pop());
                printState("fin", theStack, postfija); // agregado
            }

        // Muestra la expresión convertida.
        System.out.println("Postfija= " + postfija);
        return postfija;
    }

    // Método main para probar el evaluador.
    public static void main(String[] args) {
        EvaluatorSeguimientodePila e = new EvaluatorSeguimientodePila();
        System.out.println(e.evaluate());
    }
}