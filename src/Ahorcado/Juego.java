package Ahorcado;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

public class Juego extends JPanel {
	
	Lienzo lienzo;
	String letras = "abcdefghijklmnñopqrstuvwxyz";
	private Font font;
	
	public Juego(Lienzo lienzo) throws FontFormatException, IOException {
		this.lienzo = lienzo;
		InputStream in = getClass().getResourceAsStream("/heorot.ttf");
		font = Font.createFont(Font.PLAIN, in).deriveFont(30f);
		in.close();
		
		setLayout(new BorderLayout());
		
		JPanel sup = new JPanel(new GridLayout(1, 1));
		JLabel lblPalabra = new JLabel(new ImageIcon(getClass().getResource("/IA.jpg")));
		lblPalabra.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createEmptyBorder(30, 30, 30, 30),
				BorderFactory.createCompoundBorder(
						BorderFactory.createLineBorder(Color.DARK_GRAY),
						BorderFactory.createEmptyBorder(20, 20, 20, 20))));
		lblPalabra.setHorizontalAlignment(JLabel.CENTER);
		lblPalabra.setFont(font);
		sup.add(lblPalabra);

		JPanel inf = new JPanel(new GridLayout(4, 7)); //para poner el teclado en el layout
		inf.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createEmptyBorder(30, 30, 30, 30),
				BorderFactory.createBevelBorder(BevelBorder.RAISED)));
		for (int i=0; i<letras.length(); i++) {
			JButton b = new JButton("" + letras.charAt(i)); //o usar (letras.subString(i, i + 1))
			b.setFont(font);
			inf.add(b);
			b.setEnabled(false);//Todas la letras estan deshabilitadas
		}
		inf.add(new JButton("Jugar"));

		add(sup, BorderLayout.CENTER);
		add(inf, BorderLayout.SOUTH);
	}

	public void actionPerformed(ActionEvent e) {
		String accion = e.getActionCommand();
		
		
	}
}
