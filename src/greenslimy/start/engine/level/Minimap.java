package greenslimy.start.engine.level;

import greenslimy.start.engine.Engine;
import greenslimy.start.tile.Tile;

public class Minimap {
	
	public int mapX = 0;
	public int mapY = 0;
	public int mapW = 0;
	public int mapH = 0;
	public int mapTileW = 0;
	public int mapTileH = 0;
	
	public Minimap(Level l) {
		int scale = l.getMinimapScale().getZ();
		this.mapTileW = Tile.TILE_WIDTH/scale;
		this.mapTileH = Tile.TILE_HEIGHT/scale;
		this.mapW = l.getMinimapScale().getX()+1;
		this.mapH = l.getMinimapScale().getY()+1;
		this.mapX = Engine.d.getWidth()-(mapW+18);
		this.mapY = 0;
	}

}
