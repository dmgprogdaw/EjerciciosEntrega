package Ips;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Ips {

	public static void main(String[] args) throws IOException{
		Map<String, Map<String, Integer>> usuariosIp = new HashMap<>();
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int mensajes = 0;
		String ip = null , usuario = null;
		boolean fin = false;
		do {
			System.out.print("> ");
			Scanner teclado = new Scanner(br.readLine());
			int estado = 0;
			String token = null;
			while(estado != 7) {
				switch(estado) {
					case 0:
						try {
							token = teclado.skip("terminar|Ip=\\(").match().group();
							if (token.equals("terminar")) {
								estado = 7;
								fin = true;
							}
							else if (token.equals("Ip=(")) {
								estado = 1;
							}
						} catch (NoSuchElementException e) {
							System.out.println("Se esperaba terminar o Ip=(");
							estado = 7;
						}
						break;
					case 1:
						try {
							ip = teclado.skip("(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)").match().group();
							estado = 2;					
						} catch (NoSuchElementException e) {
								System.out.println("Se esperaba una dirección ip válida");
							estado = 7;
						}
						break;
					case 2:
						try {
							token = teclado.skip("\\)\\s*mensaje=\\(").match().group();					
							estado = 3;
						} catch (NoSuchElementException e) {
							System.out.println("Se esperaba ')' o 'mensaje=('");
							estado = 7;
						}
						break;
					case 3:
						try {
							estado = 4;
						} catch (NoSuchElementException e) {
							System.out.println("Se esperaba un mensaje o ')'");
							estado = 7;
						}
						break;
					case 4:
						try {
							token = teclado.skip("\\s*usuario=\\(").match().group();					
							estado = 5;
						} catch (NoSuchElementException e) {
							System.out.println("Se esperaba 'usuario=('");
							estado = 7;
						}
						break;
					case 5:
						try {
							usuario = teclado.skip("\\p{L}+").match().group();
							estado = 6;
						} catch (NoSuchElementException e) {
							System.out.println("Se esperaba un usuario");
							estado = 7;
						}
						break;
					case 6:
						try {
							teclado.skip("\\)");
							if (usuariosIp.containsKey(usuario)) {
								if(usuariosIp.get(usuario).containsKey(ip)) {
									mensajes++;
									usuariosIp.get(usuario).put(ip, mensajes);
								}
								else {
									mensajes = 1;
									usuariosIp.get(usuario).put(ip, mensajes);
								}
							}
							else {
								mensajes = 1;
								usuariosIp.put(usuario, new HashMap<>());
								usuariosIp.get(usuario).put(ip, mensajes);																	
							}
							estado = 7;
						} catch (NoSuchElementException e) {
							System.out.println("Se esperaba un ')'");
							estado = 7;
						}
						break;
				}
			}
			teclado.close();
		}while (!fin);
		
		
		Iterator<Map.Entry<String, Map<String, Integer>>> mapa = usuariosIp.entrySet().iterator();
		while (mapa.hasNext()) {
			int numIp = 0; 
			int totalMensajes = 0;
			Map.Entry<String, Map<String, Integer>> entrada1 = mapa.next();		
			String nombreUsuario = entrada1.getKey();
			System.out.println(nombreUsuario + ":");
			
			Iterator<Entry<String, Integer>> mapa2 = usuariosIp.get(nombreUsuario).entrySet().iterator();
			while (mapa2.hasNext()) {
				Map.Entry<String, Integer> ips = mapa2.next();
				if (mapa2.hasNext()) 
					System.out.print(ips.getKey() + " => " +ips.getValue() + ", ");
				else
					System.out.print(ips.getKey() + " => " +ips.getValue() + " ");
				numIp++;
				totalMensajes = totalMensajes + ips.getValue();		
			}
			System.out.println("\n" + "Número de IPs: " + numIp);
			System.out.println("Total de mensajes: " + totalMensajes);
			System.out.println();
		}
	}	
}