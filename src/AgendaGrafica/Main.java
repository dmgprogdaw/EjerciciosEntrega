package AgendaGrafica;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;


public class Main extends JFrame implements ActionListener, KeyListener, WindowListener{
	
	private static JTextArea ta;
	private static JTextField tf;
	
	public Main () throws IOException {
		super("Mi Agenda");
		setIconImage(ImageIO.read(getClass().getResource("/SinCara.png")));
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setSize(500, 500);
		setLocationRelativeTo(null);
		addWindowListener(this);
		
		JToolBar barraSuperior = new JToolBar();		
		JButton abrir = new JButton(new ImageIcon(getClass().getResource("/Open file.png")));
		abrir.setActionCommand("ABRIR");
		abrir.addActionListener(this);
		JButton guardar = new JButton(new ImageIcon(getClass().getResource("/Save.png")));
		guardar.setActionCommand("GUARDAR");
		guardar.addActionListener(this);
		barraSuperior.add(abrir);
		barraSuperior.add(guardar);
		add(BorderLayout.NORTH, barraSuperior);
		
		ta = new JTextArea();
		ta.setFocusable(false);
		ta.setBackground(Color.white);
		add(BorderLayout.CENTER, ta);
		
		JPanel barraInferior = new JPanel();
		barraInferior.setLayout(new BorderLayout());
		tf = new JTextField();
		tf.addKeyListener(this);
		JButton ejecutar = new JButton(new ImageIcon(getClass().getResource("/Play.png")));
		ejecutar.setActionCommand("EJECUTAR");
		ejecutar.addActionListener(this);
		barraInferior.add(BorderLayout.CENTER, tf);
		barraInferior.add(BorderLayout.EAST, ejecutar);
		add(BorderLayout.SOUTH, barraInferior);
	}
	
	public static void main(String[] args)  {		
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					new Main().setVisible(true);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}		
		});
	}		
	
	public void EjecutarComando() {
		String resultado = Agenda.miAgenda(tf.getText());
		if (resultado != null) {
			ta.append(resultado + "\n");
		}
		tf.setText("");
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("EJECUTAR")) {
			EjecutarComando();
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

	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		
		if (key == KeyEvent.VK_ENTER) {
			EjecutarComando();
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Apéndice de método generado automáticamente
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Apéndice de método generado automáticamente
	}

	@Override
	public void windowActivated(WindowEvent arg0) {
		tf.requestFocus();		
	}

	@Override
	public void windowClosed(WindowEvent arg0) {
		// TODO Apéndice de método generado automáticamente
	}

	@Override
	public void windowClosing(WindowEvent arg0) {
		int cerrar = JOptionPane.showConfirmDialog(Main.this, "¿Estas seguro?", "Salir de la aplicacion?", JOptionPane.YES_NO_OPTION);
		if(cerrar == JOptionPane.YES_OPTION)
			System.exit(0);
	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {
		// TODO Apéndice de método generado automáticamente
		
	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {
		// TODO Apéndice de método generado automáticamente
		
	}

	@Override
	public void windowIconified(WindowEvent arg0) {
		// TODO Apéndice de método generado automáticamente
		
	}

	@Override
	public void windowOpened(WindowEvent arg0) {
		// TODO Apéndice de método generado automáticamente
		
	}
}
