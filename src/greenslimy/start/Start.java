package greenslimy.start;

import java.awt.Dimension;
import java.awt.Toolkit;

import greenslimy.start.engine.Engine;

public class Start {
	
	public static Engine e = null;
	private static Dimension screenSize = null;

	public static void main(String[] args) {
		screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int preferedWidth = 800;
		int preferedHeight = 600;
		e = new Engine(preferedWidth, preferedHeight, "Mine\u00B3");
		(new Thread(e)).start();
	}

}
