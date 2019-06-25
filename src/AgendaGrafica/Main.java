package AgendaGrafica;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;


public class Main {
	static String resultado;
	public static void main(String[] args) {
		
		JFrame frame = new JFrame("Mi Agenda");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(600, 600);
		frame.setLocationRelativeTo(null);
		
		JToolBar menuHorizontal = new JToolBar();
		menuHorizontal.setBackground(Color.LIGHT_GRAY);
		frame.getContentPane().add(BorderLayout.NORTH, menuHorizontal);	
		JButton abrir = new JButton("Abrir");
		menuHorizontal.add(abrir);
		abrir.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser abrirFichero = new JFileChooser();
				int seleccion = abrirFichero.showOpenDialog(abrir);
				if (seleccion == JFileChooser.APPROVE_OPTION) {
					File fichero = abrirFichero.getSelectedFile();
					FileReader fr = null;
					try {
						fr = new FileReader(fichero);
					} catch (FileNotFoundException e1) {
						e1.printStackTrace();
					}
					BufferedReader br = new BufferedReader(fr);
					String linea;
					try {
						while ((linea = br.readLine()) != null) {	
							String[] contactos = linea.split("-");
							Agenda.agenda.put(contactos[0], contactos[1]);
						}
						br.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
					resultado = "Se ha cargado";
				}
			}	
		});
		JButton guardar = new JButton("Guardar");		
		menuHorizontal.add(guardar);
		guardar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser guardarFichero = new JFileChooser();
				int seleccion = guardarFichero.showOpenDialog(guardar);
				if (seleccion == JFileChooser.APPROVE_OPTION) {
					File fichero = guardarFichero.getSelectedFile();
					FileWriter fw = null;
					try {
						fw = new FileWriter(fichero);
					} catch (IOException e) {
						e.printStackTrace();
					}
					BufferedWriter bw = new BufferedWriter(fw);
					PrintWriter pw = new PrintWriter(bw);
					Iterator<Entry<String, String>> contactos = Agenda.agenda.entrySet().iterator();
					while (contactos.hasNext()) {
						Map.Entry<String,String> entrada = contactos.next();
						pw.println(entrada.getKey() + "-" + entrada.getValue());	
					}
					pw.close();
				}
			}	
		});
		
		JTextArea ta = new JTextArea();
		ta.setEditable(false);
		ta.setBackground(Color.white);
		frame.getContentPane().add(BorderLayout.CENTER, ta);	
		
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.setBackground(Color.LIGHT_GRAY);		
		frame.getContentPane().add(BorderLayout.SOUTH, panel);
		JTextField tf = new JTextField();
		tf.setFocusable(true);		
		tf.setColumns(20);
		panel.add(BorderLayout.CENTER, tf);
		JButton ejecutar = new JButton("Ejecutar");	
		panel.add(ejecutar, BorderLayout.EAST);
		
		ejecutar.addActionListener(new ActionListener () {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				resultado = Agenda.Ejecutar(tf.getText());
				if (resultado != null) {
					ta.append(resultado + "\n");
				}
				tf.setText("");			
			}			
		});
		
		frame.setVisible(true);
	}
}
