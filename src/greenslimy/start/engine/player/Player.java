package greenslimy.start.engine.player;

import greenslimy.start.engine.Engine;
import greenslimy.start.images.ImageLoader;
import greenslimy.start.misc.Dimension;
import greenslimy.start.tile.AirTile;

import java.awt.Image;

public class Player {
	
	private int x, y, z = 0;
	private int viewableWidth, viewableLength, viewableDepth = 0;
	private Image playerImage = ImageLoader.getSubImage(ImageLoader.loadImage("./images/player.png"), 0, 0, 64, 64);
	/**What to do when the player moves. "walk", "mine"*/
	private String moveAction = "walk";
	
	public Player(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.viewableWidth = 5;
		this.viewableLength = 5;
		this.viewableDepth = 5;
	}
	
	public Dimension getPlayerPos() {
		return new Dimension(this.x, this.y, this.z);
	}
	
	public int getSelectedTileId() {
		return Engine.l.getTileMap()[z][y][x].getTileId();
	}
	
	public void setPlayerPos(Dimension d) {
		this.x = d.getX();
		this.y = d.getY();
		this.z = d.getZ();
	}
	
	public Image getPlayerImage() {
		return this.playerImage;
	}
	
	public String getMoveAction() {
		return this.moveAction;
	}

	public void setMoveAction(String action) {
		this.moveAction = action;
	}
	
	public Dimension getViewable() {
		return new Dimension(viewableWidth, viewableLength, viewableDepth);
	}
	
	/**
	 * Does the player movement logic
	 * @param dir 0=Y-, 1=X+, 2=Y+, 3=X-, 4=Z+, 5=Z-
	 */
	public void move(int dir) {
		if(dir == 0) {
			if(y > 0) {
				if(moveAction == "walk") {
					if(z != 0) {
						if(Engine.l.getTileMap()[z-1][y-1][x] instanceof AirTile) {
							setPlayerPos(new Dimension(x, y-1, z));
						}
					}else{
						setPlayerPos(new Dimension(x, y-1, z));
					}
				}else{
					if(z != 0) {
						if(!(Engine.l.getTileMap()[z-1][y-1][x] instanceof AirTile)) {
							Engine.l.deleteTile(new Dimension(x, y-1, z-1));
						}
						setPlayerPos(new Dimension(x, y-1, z));
					}
				}
			}
		}
		if(dir == 1) {
			if(x < Engine.l.getLevelDimens().getX()-1) {
				if(moveAction == "walk") {
					if(z != 0) {
						if(Engine.l.getTileMap()[z-1][y][x+1] instanceof AirTile) {
							setPlayerPos(new Dimension(x+1, y, z));
						}
					}else{
						setPlayerPos(new Dimension(x+1, y, z));
					}
				}else{
					if(z != 0) {
						if(!(Engine.l.getTileMap()[z-1][y][x+1] instanceof AirTile)) {
							Engine.l.deleteTile(new Dimension(x+1, y, z-1));
						}
						setPlayerPos(new Dimension(x+1, y, z));
					}
				}
			}
		}
		if(dir == 2) {
			if(y < Engine.l.getLevelDimens().getY()-1) {
				if(moveAction == "walk") {
					if(z != 0) {
						if(Engine.l.getTileMap()[z-1][y+1][x] instanceof AirTile) {
							setPlayerPos(new Dimension(x, y+1, z));
						}
					}else{
						setPlayerPos(new Dimension(x, y+1, z));
					}
				}else{
					if(z != 0) {
						if(!(Engine.l.getTileMap()[z-1][y+1][x] instanceof AirTile)) {
							Engine.l.deleteTile(new Dimension(x, y+1, z-1));
						}
						setPlayerPos(new Dimension(x, y+1, z));
					}
				}
			}
		}
		if(dir == 3) {
			if(x > 0) {
				if(moveAction == "walk") {
					if(z != 0) {
						if(Engine.l.getTileMap()[z-1][y][x-1] instanceof AirTile) {
							setPlayerPos(new Dimension(x-1, y, z));
						}
					}else{
						Engine.p.setPlayerPos(new Dimension(x-1, y, z));
					}
				}else{
					if(z != 0) {
						if(!(Engine.l.getTileMap()[z-1][y][x-1] instanceof AirTile)) {
							Engine.l.deleteTile(new Dimension(x-1, y, z-1));
						}
						Engine.p.setPlayerPos(new Dimension(x-1, y, z));
					}
				}
			}
		}
		if(dir == 4) {
			if(z < Engine.l.getLevelDimens().getZ()-1) {
				Engine.l.deleteTile(getPlayerPos());
				setPlayerPos(new Dimension(x, y, z+1));
			}
		}
		if(dir == 5) {
			if(z!=0) {
				if(Engine.l.getTileMap()[z-1][y][x] instanceof AirTile) {
					setPlayerPos(new Dimension(x, y, z-1));
				}
			}
		}
	}

}
