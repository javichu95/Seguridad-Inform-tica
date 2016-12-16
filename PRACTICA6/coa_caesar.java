import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;


public class coa_caesar {
	
	private static int alfFrec [][] = new int [26][2];	// Tabla de letras y frecuencia.
	private static String nombre = null;		// Nombre del fichero de entrada.
	private static int maxFrec = 'E'-'A';	// Letra con la máxima frecuencia.
	private static String textoLeido = "";	// String del texto del fichero.

	/*
	 * Método principal que lanza el descifrado de César.
	 */
	public static void main(String [] args){
		
		//nombre = args[1];
		
		nombre = "cryptogram1.txt";
		Scanner texto = null; 	// Scanner para leer la entrada.
		try{
			texto = new Scanner(new File(nombre));	// Se asocia al fichero.
		} catch(IOException e){		// Se capturan las posibles excepciones.
			System.err.println("Error con el fichero de entrada.");
		}
		// Se rellena la tabla con los valores iniciales.
		for(int i=0; i<alfFrec.length; i++){
			alfFrec[i][0] = i;
		}
		while(texto.hasNextLine()){		// Se leen todas las líneas del fichero.
			String linea = texto.nextLine();
			textoLeido += linea;	// Se acumula en el string del texto total.
			for(int i=0; i<linea.length(); i++){	// Se recorre cada caracter de la línea.
				char caracter = linea.charAt(i);
				if(caracter-'A'<=25 && caracter-'A'>=0){
					alfFrec[caracter-'A'][1] += 1; 	// Se actualiza frecuencia.
				}
			}
		}
		texto.close();		// Se cierra el scanner.
		ordenar(0, alfFrec.length-1);	// Se ordenan las letras por frecuencia.
		descifrar();		// Se descifra el mensaje.
	}
	
	/*
	 * Método que descifra el mensaje.
	 */
	private static void descifrar(){
		
		PrintWriter descifrado;		// Objeto para escribir en el fichero.
		try{
			// Se asigna al objeto el nombre del fichero.
			descifrado = new PrintWriter(new FileWriter(
					nombre.substring(0,nombre.lastIndexOf("."))+"Dec.txt"));
			Scanner entrada = new Scanner(System.in);	// Scanner para leer del teclado.
			String salida = "";		// Texto descifrado.
			int indice = alfFrec.length-1;	// Índice para recorrer las frecuencias.
			String leido = "";	// String leido por teclado.
			int despReal = 0;	// Desplazamiento real que puede ser negativo.
			int despMostrar = 0;	// Desplazamiento que se muestra que siempre es positivo.
			while(!leido.toLowerCase().equals("ok")){
				salida = "";	// Se inicializa la salida.
				despReal = alfFrec[indice][0]-maxFrec;	// Se calcula el desplazamiento.
				if(despReal < 0){		// Si es negativo se pasa a positivo.
					despMostrar = despReal+26;
				} else{
					despMostrar = despReal;
				}
				// Se muestra el desplazamiento por pantalla.
				System.out.println("Desplazamiento: " + despMostrar);
				// Se recorren los caracteres a descifrar.
				for(int i=0; i<textoLeido.length(); i++){
					char caracter = textoLeido.charAt(i);
					// Si es una letra se decodifica.
					if(caracter-'A'<=25 && caracter-'A'>=0){
						// Se mira si se sale del rango para realizar el desplazamiento.
						if(caracter-despReal < 65){
							salida += (char)(caracter-despReal+26);
						} else if(caracter-despReal > 90){
							salida += (char)(caracter-despReal-26);
						} else{
							salida += (char)(caracter-despReal);
						}
					} else{		// Si no es letra se añade tal cual.
						salida += caracter;
					}
				}
				System.out.println(salida);	// Se escribe el texto decodificado.
				indice--;	// Se actualiza el índice de frecuencias.
				leido = entrada.nextLine();	// Se lee la entrada del teclado.
			}
			entrada.close();		// Se cierra la entrada.
			// Se escriben los datos en el fichero de salida.
			descifrado.println("Desplazamiento = " + despMostrar);
			descifrado.println(salida);
			descifrado.close();		// Se cierra la salida.
		} catch(IOException e){		// Se muestra la posible excepción.
			System.err.println("Error con el fichero de salida.");
		}
				
	}
	
	/*
	 * Método que realiza una ordenación de la tabla.
	 */
	private static void ordenar(int lowerIndex, int higherIndex){
			         
		int i = lowerIndex;		// Índice bajo.
		int j = higherIndex;	// Índice alto.
		// Se escoge el pivote.
	 	int pivot = alfFrec[lowerIndex+(higherIndex-lowerIndex)/2][1];
		// Se divide la tabla en dos partes.
	 	while (i <= j) {
	 		while (alfFrec[i][1] < pivot) {
	 			i++;
	 		}
	 		while (alfFrec[j][1] > pivot) {
	 			j--;
	 		}
	 		if (i <= j) {	// Si están desordenados se intercambian.
	 			intercambiar(i, j);
	 			i++;
	 			j--;
	 		}
	 	}
	 	// Se realiza la llamada recursiva.
	 	if (lowerIndex < j)
	 		ordenar(lowerIndex, j);
	   	if (i < higherIndex)
	   		ordenar(i, higherIndex);
	}
	
	/*
	 * Método que realiza la permutación de dos elementos de la tabla.
	 */
	private static void intercambiar(int i, int j) {
		int tempFrec = alfFrec[i][1];
		int tempLetra = alfFrec[i][0];
		alfFrec[i][0] = alfFrec[j][0];
		alfFrec[i][1] = alfFrec[j][1];
		alfFrec[j][0] = tempLetra;
		alfFrec[j][1] = tempFrec;
	}
}
