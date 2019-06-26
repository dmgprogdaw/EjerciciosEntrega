package AgendaGrafica;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.TreeMap;

public class Agenda {
	static Map<String, String> agenda = new TreeMap<String, String>();
	
	public static String miAgenda (String comando) {
		String resultado = null;
		
		Scanner s = new Scanner(comando);
		String nombre = null, token = null, estado2 = null;
		int estado = 0;
		
		while (estado != 5) {
			switch(estado) {
				case 0:
					try {
						token = s.skip("buscar|borrar|\\p{L}+(\\s+\\p{L}+)*").match().group();
						if (token.equals("buscar")) {
							estado2 = "buscar";
							estado = 3;
						}
						else if (token.equals("borrar")) {
							estado2 = "borrar";
							estado = 3;											
						}
						else {
							nombre = token;
							estado = 1;
						} 			
					}catch (NoSuchElementException e) {
						resultado = "Se esperaba buscar o borrar o un nombre";
						estado = 5;
					}		
					break;
				case 1:
					try {
						s.skip("-");
						estado = 2;		
					}catch (NoSuchElementException e) {
						resultado = "Se esperaba un '-'";
						estado = 5;
					}
					break;
				case 2:
					try {
						token = s.skip("\\d{9}").match().group();
						if (agenda.containsKey(nombre)) {	
							agenda.put(nombre, token);
							resultado = "Se ha actualizado el telefono de " + nombre;
						}
						else {
							agenda.put(nombre, token);
							resultado = "Se ha registrado a " + nombre + " en la agenda";
						}
						estado = 5;		
					}catch (NoSuchElementException e) {
						resultado = "Se esperaba un número de telefono";
						estado = 5;
					}
					break;
				case 3:
					try {
						s.skip(":");
						estado = 4;
					}catch (NoSuchElementException e) {
						resultado = "Se esperaba ':'";
						estado = 5;
					}
					break;
				case 4:
					try {
						token = s.skip("[a-zA-ZáéíóúÁÉÍÓÚ]+\\s+([a-zA-ZáéíóúÁÉÍÓÚ]+\\s+)*[a-zA-ZáéíóúÁÉÍÓÚ]+|[a-zA-ZáéíóúÁÉÍÓÚ]+").match().group();
						String telefono = agenda.get(token);
						if (telefono != null) {
							if (estado2.equals("buscar")) {
								resultado = token + " -> " + telefono;
							}
							else if (estado2.equals("borrar")) {
								agenda.remove(token);
								resultado = "Contacto eliminado: " + token + " -> " + telefono;
							}
						}
						else {
							resultado = token + " no se encuentra en la agenda";
						}
						estado = 5;		
					}catch (NoSuchElementException e) {
						resultado = "Se esperaba un nombre";
						estado = 5;
					}
					break;
			}
		}
		
		return resultado;
	}
	
}
