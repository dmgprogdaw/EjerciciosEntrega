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

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;


public class Main implements ActionListener{
	
	private static JTextArea ta;
	private static JTextField tf;
	
	public static void main(String[] args) throws IOException {		
		JFrame frame = new JFrame("Mi Agenda");
		frame.setIconImage(ImageIO.read(Main.class.getResource("/SinCara.png")));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(500, 500);
		frame.setLocationRelativeTo(null);
		
		JToolBar menuHorizontal = new JToolBar();
		frame.getContentPane().add(BorderLayout.NORTH, menuHorizontal);	
		
		JButton abrir = new JButton(new ImageIcon(Main.class.getResource("/Open file.png")));
		menuHorizontal.add(abrir);
		Main listener = new Main();
		abrir.setActionCommand("ABRIR");
		abrir.addActionListener(listener);
		JButton guardar = new JButton(new ImageIcon(Main.class.getResource("/Save.png")));		
		menuHorizontal.add(guardar);
		guardar.setActionCommand("GUARDAR");		
		guardar.addActionListener(listener);
		
		ta = new JTextArea();
		ta.setFocusable(false);
		ta.setBackground(Color.white);
		frame.getContentPane().add(BorderLayout.CENTER, ta);	
		
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.setBackground(Color.LIGHT_GRAY);		
		frame.getContentPane().add(BorderLayout.SOUTH, panel);
		tf = new JTextField();
		tf.setColumns(20);
		panel.add(BorderLayout.CENTER, tf);
		JButton ejecutar = new JButton(new ImageIcon(Main.class.getResource("/Play.png")));	
		panel.add(ejecutar, BorderLayout.EAST);		
		ejecutar.setActionCommand("EJECUTAR");
		ejecutar.addActionListener(listener);
		
		frame.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("EJECUTAR")) {
			String resultado = Agenda.Ejecutar(tf.getText());
			if (resultado != null) {
				ta.append(resultado + "\n");
			}
			tf.setText("");
		}
		else if(e.getActionCommand().equals("GUARDAR")) {
			JFileChooser guardarFichero = new JFileChooser();
			int seleccion = guardarFichero.showOpenDialog(guardarFichero);
			if (seleccion == JFileChooser.APPROVE_OPTION) {
				File fichero = guardarFichero.getSelectedFile();
				FileWriter fw = null;
				try {
					fw = new FileWriter(fichero);
				} catch (IOException e2) {
					e2.printStackTrace();
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
			String resultado = "La agenda se ha guardado en un fichero";
			ta.append(resultado + "\n");
			tf.setText("");
		}
		else if (e.getActionCommand().equals("ABRIR")) {
			JFileChooser abrirFichero = new JFileChooser();
			int seleccion = abrirFichero.showOpenDialog(abrirFichero);
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
				} catch (IOException e2) {
					e2.printStackTrace();
				}
				String resultado = "Se ha cargado la agenda desde el fichero";
				ta.append(resultado + "\n");
				tf.setText("");
			}
		}
	}
}
