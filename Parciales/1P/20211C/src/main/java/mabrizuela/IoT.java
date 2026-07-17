package mabrizuela;

//Implementar el método generate(int windowSize) que imprime según lo pedido,
//hasta que se acaban los tokens. Ese método puede usar variables o estructuras
//auxiliares locales que no tengan mayor complejidad espacial que O(1).
//Si se usa alguna estructura auxiliar que no venga en java,
//proporcionar también el código y tener en cuenta que tiene que estar limitada por
//el espacio (no deben depender de N donde N son los valores sensados que se leen,
//sino que solo puede depender del tamaño de la ventana que pide el usuario).
import java.util.Scanner;

public class IoT {
 	private Scanner scannerLine;
	public IoT()
	{
	    Scanner inputScanner = new Scanner(System.in).useDelimiter("\\n");
	    System.out.print("Valores sensados (separados por blancos): ");
	    inputScanner.hasNextLine();
	    String line = inputScanner.nextLine();
	    scannerLine = new Scanner(line).useDelimiter("\\s+");
	}
    
	// mientras haya tokens procesa e imprime en ventanas de tamano "windowSize"
	public void generate(int windowSize)
	{
		/// TODO
	}

	public static void main(String[] args) {
		new IoT().generate(2);
		new IoT().generate(4);
	}
}
