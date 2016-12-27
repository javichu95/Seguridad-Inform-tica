
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class bruteforce_caesar {
	
	private static Scanner fic;
	private static boolean correcto = false;
	private static Scanner entrada = new Scanner(System.in);
	private static String nombre = null;		// Nombre del fichero de entrada.
	
	/*
	 * Método princpial que realiza el descifrado de César por fuerza bruta.
	 */
	public static void main(String[] args) {
		if (args.length != 1) {	// Se comprueban el número de argumentos.
			System.err.println("Error en los argumentos");
			System.exit(1);
		}
		try {
			nombre = args[0];	// Se guarda el nombre del fichero.
			fic = new Scanner(new File(args[0]));	// Se crea el scanner de entrada.
			String texto = "";		// Texto total leido del fichero.
			while(fic.hasNextLine()) {		// Se leen todas las líneas del fichero.
				texto = texto + fic.nextLine();
			}
			// Se descifra probando todos los desplazamientos.
			for(int i = 0; i<=25 && !correcto;i++) {
				descifrar(texto,i);
			}
		} catch(FileNotFoundException e) {		// Se muestra la posible excepción.
			System.err.println("Fichero no encontrado");
			System.exit(1);
		}
	}
	
	/*
	 * Método que descifra el mensaje con un cierto desplazamiento dado.
	 */
	private static void descifrar(String texto, int caracter) {
		caracter = caracter +'A';		// Se asigna el caracter inicial.
		int desplazamiento;
		if(texto.charAt(0) > caracter) {		// Se calcula el desplazamiento.
			desplazamiento = texto.charAt(0) - caracter;
		} else {
			desplazamiento = caracter - texto.charAt(0);
		}
		String descifrado = "";		// String del texto descifrado.
		for(int i = 0; i < texto.length(); i++) {	// Se recorren los distintos caracteres.
			char leido = texto.charAt(i);	// Se lee el caracter.
			if(leido - 'A' <= 25 && leido - 'A' >= 0) {	// Se comprueba si es una letra.
				int conversion = leido - desplazamiento;
				if(conversion < 65) {	// Se realiza la conversión si se sale del rango.
					conversion = 91 - (65 - conversion);
				} else if(conversion > 90) {
					conversion = 64 + (conversion - 90);
				}
				descifrado = descifrado + ((char)(conversion));
			} else {		// Si no es una letra lo escribe tal cual.
				descifrado = descifrado + leido;
			}
		}
		// Se muestra por pantalla el desplazamiento utilizado y el texto descifrado.
		System.out.println("Desplazamiento: " + desplazamiento);
		System.out.println("Si el descifrado es correcto introduzca 'Ok' (si no introduzca cualquier cosa):\n"+descifrado);
		String respuesta = entrada.nextLine();	// Se lee la respuesta del usuario.
		respuesta = respuesta.toLowerCase();
		if(respuesta.equals("ok")) {
			correcto = true;		// Se asigna el valor a la variable según la respuesta.
			try{
				// Se asigna al objeto el nombre del fichero.
				PrintWriter salida = new PrintWriter(new FileWriter(
						nombre.substring(0,nombre.lastIndexOf("."))+"Dec.txt"));
				// Se escriben los datos en el fichero de salida.
				salida.println(nombre);
				salida.println("Desplazamiento = " + desplazamiento);
				salida.println(descifrado);
				salida.close();		// Se cierra la salida.
			} catch(IOException e){
				System.err.println("Error con el fichero de salida.");
				System.exit(1);
			}
		}
	}
}
