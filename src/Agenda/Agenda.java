package Agenda;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;
import java.util.Map.Entry;

public class Agenda {

	public static void main(String[] args) throws IOException {
		Map<String, String> agenda = new TreeMap<String, String>();
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		boolean fin = false;
		FileWriter fw;
		FileReader fr;
		
		do {
			System.out.print("> ");
			Scanner teclado = new Scanner(br.readLine());
			int estado = 0;
			String ruta = null, token = null, nombre = null, telefono = null, estado2 = null;
			while (estado != 10) {
				switch (estado) {
					case 0:
						try {
							token = teclado.skip("terminar|buscar|borrar|cargar|guardar|\\p{L}+(\\s+\\p{L}+)*").match().group();
							if (token.equals("terminar")) {
								estado = 10;
								fin = true;
							}
							else if (token.equals("buscar")) {
								estado2 = "buscar";
								estado = 3;
							}
							else if (token.equals("borrar")) {
								estado2 = "borrar";
								estado = 3;											
							}
							else if (token.equals("cargar")) {
								estado2 = "cargar";
								estado = 6;
							}
							else if (token.equals("guardar")) {
								estado2 = "guardar";
								estado = 3;
							}
							else {
								nombre = token;
								estado = 1;
							} 			
						}catch (NoSuchElementException e) {
							System.out.println("Se esperaba terminar o buscar o borrar o cargar o guardar o un nombre");
							estado = 10;
						}			
						break;
					case 1:
						try {
							teclado.skip("-");
							estado = 2;		
						}catch (NoSuchElementException e) {
							System.out.println("Se esperaba un '-'");
							estado = 10;
						}
						break;
					case 2:
						try {
							token = teclado.skip("\\d{9}").match().group();
							if (agenda.containsKey(nombre)) {	
								agenda.put(nombre, token);
								System.out.println("Se ha actualizado el telefono de " + nombre);
							}
							else {
								agenda.put(nombre, token);
							}
							estado = 10;		
						}catch (NoSuchElementException e) {
							System.out.println("Se esperaba un número de telefono");
							estado = 10;
						}
						break;
					case 3:
						try {
							teclado.skip(":");
							if (estado2.equals("buscar")||estado2.equals("borrar"))
								estado = 4;
							else if (estado2.equals("guardar")||estado2.equals("cargar"))
								estado = 5;
						}catch (NoSuchElementException e) {
							System.out.println("Se esperaba ':'");
							estado = 10;
						}
						break;
					case 4:
						try {
							token = teclado.skip("[a-zA-ZáéíóúÁÉÍÓÚ]+\\s+([a-zA-ZáéíóúÁÉÍÓÚ]+\\s+)*[a-zA-ZáéíóúÁÉÍÓÚ]+|[a-zA-ZáéíóúÁÉÍÓÚ]+").match().group();
							telefono = agenda.get(token);
							if (telefono != null) {
								if (estado2.equals("buscar")) {
									System.out.println(token + " -> " + telefono);
								}
								else if (estado2.equals("borrar")) {
									agenda.remove(token);
									System.out.println("Contacto eliminado: " + token + " -> " + telefono);
								}
							}
							else {
								System.out.println(token + " no se encuentra en la agenda");
							}
							estado = 10;		
						}catch (NoSuchElementException e) {
							System.out.println("Se esperaba un nombre");
							estado = 10;
						}
						break;
					case 5:
						try {
							ruta = teclado.skip("[a-zA-Z]:/(\\w+/)*\\w*\\.*\\w+").match().group();
							try {
								fw = new FileWriter(ruta);
								BufferedWriter bw = new BufferedWriter(fw);
								PrintWriter pw = new PrintWriter(bw);
								Iterator<Entry<String, String>> contactos = agenda.entrySet().iterator();
								while (contactos.hasNext()) {
									Map.Entry<String,String> entrada = contactos.next();
									pw.println(entrada.getKey() + "-" + entrada.getValue());	
								}
								pw.close();
							}catch (IOException e) {
								System.out.println("No se ha podido guardar el fichero");
							}
							System.out.println("Se ha guardado");
							estado = 10;
						}catch (NoSuchElementException e) {
							System.out.println("La ruta no es valida");
							estado = 10;
						}
						break;
					case 6:
						try {
							ruta = teclado.skip("[a-zA-Z]:/(\\w+/)*\\w*\\.*\\w+").match().group();
							try {
								fr = new FileReader(ruta);
								BufferedReader b = new BufferedReader(fr);
								String linea;
								while ((linea = br.readLine()) != null) {	
									String[] contactos = linea.split("-");
									agenda.put(contactos[0], contactos[1]);
								}
								System.out.println("Se ha cargado");
								estado = 10;
							}catch(FileNotFoundException e) {
								System.out.println("No se ha podido leer el archivo");
							}
							estado = 10;
						}catch (NoSuchElementException e) {
							System.out.println("No se ha encontrado el archivo");
							estado = 10;
						}
						break;
				}
			}
		}while (!fin);
	}
}
