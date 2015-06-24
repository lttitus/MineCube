package greenslimy.start.engine.level;

import greenslimy.start.engine.Engine;
import greenslimy.start.misc.Dimension;
import greenslimy.start.tile.AirTile;
import greenslimy.start.tile.BedrockTile;
import greenslimy.start.tile.DirtTile;
import greenslimy.start.tile.GrassTile;
import greenslimy.start.tile.RockTile;
import greenslimy.start.tile.Tile;

import java.awt.Color;
import java.util.Random;

public class Level {
	
	public static final Color[] minimapColors = {Color.BLACK, Color.RED, Color.GREEN, Color.GRAY, Color.BLACK};
	
	private int tileWidth = 0;	//Level width in tiles - x
	private int tileLength = 0;	//Level length in tiles - y
	private int tileDepth = 0;	//Level height in tiles - z
	private Tile[][][] tileMap = null;
	private Minimap minimap = null;
	private Dimension mapScale = null;
	
	/**
	 * Instantiates a new Level obj
	 * @param tileDepth - max depth (z)
	 * @param tileLength - max length (y)
	 * @param tileWidth - max width (x)
	 */
	public Level(int tileDepth, int tileLength, int tileWidth) {
		this.tileWidth = tileWidth;
		this.tileLength = tileLength;
		this.tileDepth = tileDepth;
		this.tileMap = new Tile[tileDepth][tileLength][tileWidth];
		this.tileMap = createTileMap();
		this.minimap = new Minimap(this);
	}
	
	public Tile[][][] createTileMap() {
		Tile[][][] t = new Tile[tileDepth][tileLength][tileWidth];
		System.out.println("Creating a new level tilemap");
		int i=0;
		for(int z=0;z<tileDepth;z++) {	//Z increments last, thus starting in top-left corner, working right, then down
			for(int y=0;y<tileLength;y++) {
				for(int x=0;x<tileWidth;x++) {
					i++;	//TileId
					if(z != tileDepth-1) {	//Only place bedrock blocks at bottom of the cube
						if(z == 0) {	//Only place grass blocks at the top of the cube
							//boolean hasTallGrass = r.nextBoolean();
							t[z][y][x] = new GrassTile(x*32+(y*32), y*32-(x*16)-(y*16)+(z*32), z, i);
						}else{
							if(z>=1&&z<=4) {	//From layer 1 to 4 place dirt tiles
								t[z][y][x] = new DirtTile(x*32+(y*32), y*32-(x*16)-(y*16)+(z*32), z, i);
							}else if(z>=5&&z<tileDepth-1){
								t[z][y][x] = new RockTile(x*32+(y*32), y*32-(x*16)-(y*16)+(z*32), z, i);
							}
						}
					}else{
						t[z][y][x] = new BedrockTile(x*32+(y*32), y*32-(x*16)-(y*16)+(z*32), z, i);
					}
				}
			}
		}
		//System.out.println("Created a new level tilemap");
		return t;
	}
	
	public void doTileLogic() {
		int i=0;
		for(int z=0;z<tileDepth;z++) {
			for(int y=0;y<tileLength;y++) {
				for(int x=0;x<tileWidth;x++) {
					Tile t = tileMap[z][y][x];
					if(z==0) {
						t.setFaceRenderable(2, true);
					}
					if(z>0) {
						if((tileMap[z-1][y][x] instanceof AirTile)) {
							t.setFaceRenderable(2, true);
						}
					}else{
						t.setFaceRenderable(2, true);
					}
					if(x>0) {
						if((tileMap[z][y][x-1] instanceof AirTile)) {
							t.setFaceRenderable(0, true);
						}
					}else{
						t.setFaceRenderable(0, true);
					}
					if(y<Engine.l.tileLength-1) {
						if((tileMap[z][y+1][x] instanceof AirTile)) {
							t.setFaceRenderable(1, true);
						}
					}else{
						t.setFaceRenderable(1, true);
					}
					if(t instanceof AirTile) {
						t.setFaceRenderable(0, false);
						t.setFaceRenderable(1, false);
						t.setFaceRenderable(2, false);
					}
				}
			}
		}
	}
	
	public void deleteTile(Dimension tilePos) {
		int x=tilePos.getX();
		int y=tilePos.getY();
		int z=tilePos.getZ();
		Tile oldTile = tileMap[z][y][x];
		tileMap[z][y][x] = new AirTile(oldTile.getDimensions().getX(), oldTile.getDimensions().getY(), oldTile.getDimensions().getZ(), oldTile.getTileId());
		System.out.println("Dimensions: ("+x+", "+y+", "+z+")");
	}
	
	public void deleteTile(int oldTileId) {	//Doesnt actually delete the tile, just replaces with an air tile
		for(int z=0;z<tileDepth;z++) {
			for(int y=0;y<tileLength;y++) {
				for(int x=0;x<tileWidth;x++) {
					Tile oldTile = tileMap[z][y][x];
					if(oldTile.getTileId() == oldTileId) {
						tileMap[z][y][x] = new AirTile(x, y, z, oldTile.getTileId());
					}
				}
			}
		}
	}
	
	public Dimension getLevelDimens() {
		return new Dimension(tileWidth, tileLength, tileDepth);
	}
	
	public Dimension getMinimapScale() {
		if(mapScale == null) {
			int scale = 4;	//Minimap is 8 times smaller than real map
			int tilesX = (tileWidth*64)/scale;
			int tilesY = (tileLength*64)/scale;
			mapScale = new Dimension(tilesX, tilesY, scale);	//Z is never seen on minimap, so pack the scale
		}
		return mapScale;
	}
	
	/**
	 * Returns the full tile map
	 * @return z, y, x
	 */
	public Tile[][][] getTileMap() {
		return this.tileMap;
	}
	
	public Minimap getMinimap() {
		return this.minimap;
	}

}
