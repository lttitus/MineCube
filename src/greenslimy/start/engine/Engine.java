package greenslimy.start.engine;

import java.util.Random;

import greenslimy.start.engine.level.Level;
import greenslimy.start.engine.player.Player;
import greenslimy.start.misc.Dimension;
import greenslimy.start.screen.Display;

public class Engine extends Thread implements Runnable {
	
	public static final Random r = new Random();
	
	public static Display d = null;
	public static Player p = null;
	public static Level l = null;
	public double scale = 0.0;
	
	public Engine(int width, int height, String gameName) {
		d = new Display(width, height, gameName);
		l = new Level(9, 9, 9);
		p = new Player(0, 0, 0);
	}
	
	@Override
	public void run() {
		
	}

}