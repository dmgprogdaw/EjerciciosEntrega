package Ahorcado;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Arc2D;
import java.awt.geom.Line2D;
import javax.swing.JPanel;

public class Lienzo extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private static final float [] patron = {5, 3, 1, 3};
	private static final BasicStroke solido = new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER);
	private static final BasicStroke discontinuo = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, patron, 0);	
	
	private Shape [] shapes;
	int fallos = 0;
	
	public Lienzo(int width, int height) {
		setPreferredSize(new Dimension(width, height));
		shapes = new Shape[11];
		shapes[0] = new Line2D.Float(470, 470, 30, 470);
		shapes[1] = new Line2D.Float(30, 470, 30, 30);
		shapes[2] = new Line2D.Float(30, 30, 350, 30);
		shapes[3] = new Line2D.Float(150, 30, 30, 150);
		shapes[4] = new Line2D.Float(350, 30, 350, 120);
		shapes[5] = new Arc2D.Float(320, 120, 61, 61, 0, 360, Arc2D.OPEN);
		shapes[6] = new Line2D.Float(350, 180, 350, 290);
		shapes[7] = new Line2D.Float(350, 200, 300, 250);
		shapes[8] = new Line2D.Float(350, 200, 400, 250);
		shapes[9] = new Line2D.Float(350, 290, 300, 390);
		shapes[10] = new Line2D.Float(350, 290, 400, 390);
	}
	
	public void incFallos() {
		fallos++;
		repaint();
	}
	
	public void reset() {
		fallos++;
		repaint();
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		for (int i=0; i<shapes.length; i++) {
			if (i < fallos) {
				//Trazo continuo y color negro.
				g2d.setColor(Color.BLACK);
				g2d.setStroke(solido);
			}
			else {
				g2d.setColor(Color.LIGHT_GRAY);
				g2d.setStroke(discontinuo);
			}
			g2d.draw(shapes[i]);
		}
	}
	
}