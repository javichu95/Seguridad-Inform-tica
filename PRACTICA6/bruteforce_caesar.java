package practica6;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class bruteforce_caesar {
	
	private static Scanner fic;
	private static boolean correcto = false;
	private static Scanner entrada = new Scanner(System.in);
	
	public static void main(String[] args) {
		if (args.length < 0 || args.length > 1) {
			System.err.println("Error en los argumentos");
			System.exit(1);
		}
		try {
			fic = new Scanner(new File(args[0]));
			String texto = "";
			while(fic.hasNextLine()) {
				texto = texto + fic.nextLine();
			}
			for(int i = 0; i<=25 && !correcto;i++) {
				descifrar(texto,i);
			}
		} catch(FileNotFoundException e) {
			System.err.println("Fichero no encontrado");
			System.exit(1);
		}
	}
	
	private static void descifrar(String texto, int caracter) {
		caracter = caracter +'A';
		int desplazamiento;
		if(texto.charAt(0) > caracter) {
			desplazamiento = texto.charAt(0) - caracter;
		} else {
			desplazamiento = caracter - texto.charAt(0);
		}
		String descifrado = "";
		for(int i = 0; i < texto.length(); i++) {
			char leido = texto.charAt(i);
			if(leido - 'A' <= 25 && leido - 'A' >= 0) {
				int conversion = leido - desplazamiento;
				if(conversion < 65) {
					conversion = 91 - (65 - conversion);
				} else if(conversion > 90) {
					conversion = 64 + (conversion - 90);
				}
				descifrado = descifrado + ((char)(conversion));
			} else {
				descifrado = descifrado + leido;
			}
		}
		System.out.println("Desplazamiento utilizado: " + desplazamiento);
		System.out.println("Si el descifrado es correcto introduzca 'Ok' (si no introduzca cualquier cosa):\n"+descifrado);
		String respuesta = entrada.nextLine();
		respuesta = respuesta.toLowerCase();
		if(respuesta.equals("ok")) {
			correcto = true;
			
		}
	}
}
