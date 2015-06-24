package greenslimy.start.screen;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class Display extends JFrame {
	
	private int width, height = 0;
	private Canvas c = null;
	
	public Display(int width, int height, String gameName) {
		this.width = width;
		this.height = height;
		this.setSize(width, height);
		this.setVisible(true);
		this.c = new Canvas();
		c.setBackground(Color.black);
		this.add(c);
		this.setTitle(gameName);
		c.setVisible(true);
		this.addKeyListener(c);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public Dimension getDimensions() {
		return new Dimension(this.width, this.height);
	}

}
