import javax.swing.*;
import java.awt.*;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

public class Stone extends JPanel {
	
	Color color;
	Ellipse2D.Double circle; 
	public Stone(){
		color =Color.black;
		circle = new Ellipse2D.Double(0,0,10,10);
	}
	
	@Override
	protected void paintComponent(Graphics g) {

		super.paintComponents(g);

		Graphics2D g2 = (Graphics2D) g;

		g2.setColor(color);
		g2.fill(circle);
	}
}
