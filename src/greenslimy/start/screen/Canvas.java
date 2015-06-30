package greenslimy.start.screen;

import greenslimy.start.engine.Engine;
import greenslimy.start.engine.level.Level;
import greenslimy.start.engine.level.Minimap;
import greenslimy.start.misc.Dimension;
import greenslimy.start.tile.AirTile;
import greenslimy.start.tile.Tile;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Canvas extends JPanel implements KeyListener {
	
	private static final Color[] DEPTH_COLORS = {Color.white, Color.blue, Color.cyan, Color.green, Color.magenta, Color.orange, Color.red};
	private boolean shiftPressed = false;
	
	public Canvas() {
		this.repaint();
		Engine.l.doTileLogic();
	}
	
	public void paintComponent(Graphics f) {
		super.paintComponent(f);
		//Engine.l.doTileLogic();
		Graphics2D g = (Graphics2D) f;
		g.setColor(Color.black);
		Tile[][][] map = Engine.l.getTileMap();
		Dimension ppos = Engine.p.getPlayerPos();
		int px = ppos.getX();
		int py = ppos.getY();
		int pz = ppos.getZ();
		g.setColor(Color.white);
		g.drawString("Move Action: "+Engine.p.getMoveAction(), 0, 10);
		g.drawString("On top of block type: "+map[pz][py][px].getClass().getSimpleName(), 0, 26);
		if(Engine.p.getPlayerPos().getZ() > 0) {
			g.drawString("Under block type: "+map[pz-1][py][px].getClass().getSimpleName(), 0, 42);
		}
		for(int z=Engine.l.getLevelDimens().getZ();z>0;z--) {	//Start from the bottom right corner tile and start working right to left in ascending z order
			for(int y=0;y<Engine.l.getLevelDimens().getY();y++) {
				for(int x=Engine.l.getLevelDimens().getX();x>0;x--) {
					Tile t = Engine.l.getTileMap()[z-1][y][x-1];
					//if(t.isRenderable()) {
					if(!(pz > 0)) {	//If you are not underground
						g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
						int it=0;
						for(boolean faceDraw:t.getRenderableFaces()) {
							if(faceDraw) {
								g.drawImage(t.getFaces()[it], 100+t.getDimensions().getX(), (Engine.d.getDimensions().height/4)+t.getDimensions().getY(), null);
							}
							it++;
						}
						if(t.getTileId() == Engine.p.getSelectedTileId()) {
							g.drawImage(Engine.p.getPlayerImage(), 100+t.getDimensions().getX(), (Engine.d.getDimensions().height/4)+t.getDimensions().getY(), null);
						}
					}else{
						Tile outsideXTile = map[pz-1][py][0];
						Tile outsideYTile = map[pz-1][Engine.l.getLevelDimens().getY()-1][px];
						g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
						int it=0;
						for(boolean faceDraw:t.getRenderableFaces()) {
							if(faceDraw) {
									g.drawImage(t.getFaces()[it], 100+t.getDimensions().getX(), (Engine.d.getDimensions().height/4)+t.getDimensions().getY(), null);
									g.drawImage(outsideXTile.getFace(0), 100+outsideXTile.getDimensions().getX(), (Engine.d.getDimensions().height/4)+outsideXTile.getDimensions().getY(), null);
									g.drawImage(outsideYTile.getFace(1), 100+outsideYTile.getDimensions().getX(), (Engine.d.getDimensions().height/4)+outsideYTile.getDimensions().getY(), null);
							}
							it++;
						}
						if(t.getTileId() == Engine.p.getSelectedTileId()) {
							g.drawImage(Engine.p.getPlayerImage(), 100+t.getDimensions().getX(), (Engine.d.getDimensions().height/4)+t.getDimensions().getY(), null);
						}
						
					}
						//if(!(t instanceof AirTile)) {
						//	g.setColor(Color.white);
						//	g.drawString(""+t.getTileId(), 100+t.getDimensions().getX()+24, (Engine.d.getDimensions().height/4)+t.getDimensions().getY()+16);
						//}
				}
			}
		}
		//TODO: Draw minimap
		Color tileColor = Color.WHITE;
		Minimap mm = Engine.l.getMinimap();
		g.setColor(Color.WHITE);
		g.drawRect(mm.mapX, mm.mapY, mm.mapW, mm.mapH);
		Tile tile = null;
		for(int x=0;x<Engine.l.getLevelDimens().getX();x++) {
			for(int y=0;y<Engine.l.getLevelDimens().getY();y++) {
				if(pz == 0) {
					tile = map[pz][y][x];
				}else{
					tile = map[pz-1][y][x];
				}
				if(x == px && y == py) {	//The player is on this tile
					tileColor = Color.WHITE;
				}else{
					tileColor = Level.minimapColors[tile.getTileType()];
					
					if(map[tile.getDimensions().getZ()+1][y][x] instanceof AirTile) {
						tileColor = Color.DARK_GRAY;
					}
					if(pz != 0 && pz != 1) {
						if(map[tile.getDimensions().getZ()-1][y][x] instanceof AirTile) {
							tileColor = Color.LIGHT_GRAY;
						}
					}
				}
				g.setColor(tileColor);
				g.fillRect(1+mm.mapX+(mm.mapTileW*x), 1+mm.mapY+(mm.mapTileH*y), mm.mapTileW, mm.mapTileH);
			}
		}
	}

	@Override
	public void keyPressed(KeyEvent k) {
		System.out.println("Key Pressed: "+KeyEvent.getKeyText(k.getKeyCode()));
		if(k.getKeyCode() == KeyEvent.VK_SHIFT) {
			shiftPressed = true;
		}
		if(k.getKeyCode() == KeyEvent.VK_C) {
			if(Engine.p.getMoveAction() == "walk") {
				Engine.p.setMoveAction("mine");
			}else{
				Engine.p.setMoveAction("walk");
			}
		}
		if(!shiftPressed) {
			if(k.getKeyCode() == KeyEvent.VK_UP) {
				Engine.p.move(0);
			}
			if(k.getKeyCode() == KeyEvent.VK_DOWN) {
				Engine.p.move(2);
			}
		}else{
			if(k.getKeyCode() == KeyEvent.VK_UP) {
				Engine.p.move(5);
			}
			if(k.getKeyCode() == KeyEvent.VK_DOWN) {
				Engine.p.move(4);
			}
		}
		if(k.getKeyCode() == KeyEvent.VK_RIGHT) {
			Engine.p.move(1);
		}
		
		if(k.getKeyCode() == KeyEvent.VK_LEFT) {
			Engine.p.move(3);
		}
		Engine.l.doTileLogic();
		this.repaint();
	}

	@Override
	public void keyReleased(KeyEvent k) {
		if(!(k.getKeyCode() == KeyEvent.VK_UP || k.getKeyCode() == KeyEvent.VK_DOWN))
		shiftPressed  = false;
	}

	@Override
	public void keyTyped(KeyEvent k) {
		
	}
	
	

}
