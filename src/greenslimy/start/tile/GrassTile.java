package greenslimy.start.tile;

import java.awt.Image;
import java.awt.image.BufferedImage;

import greenslimy.start.engine.Engine;
import greenslimy.start.images.ImageLoader;

public class GrassTile extends Tile {
	
	protected final static BufferedImage[][] faces = {{ImageLoader.getSubImage(tiles, 64, 0, 64, 64)}, {ImageLoader.getSubImage(tiles, 128, 0, 64, 64)}, {ImageLoader.getSubImage(altTiles, 64, 32, 64, 64), ImageLoader.getSubImage(altTiles, 2*64, 32, 64, 64), ImageLoader.getSubImage(altTiles, 3*64, 32, 64, 64), ImageLoader.getSubImage(altTiles, 4*64, 32, 64, 64)}};
	public final static BufferedImage tallGrass = ImageLoader.getSubImage(altTiles, 4*64, 11*64, 64, 64);
	
	public GrassTile(int x, int y, int z, boolean hasTallGrass, int tileId) {
		super(x, y, z, hasTallGrass, faces, tileId, 2);
	}
	
	/**
	 * Creates a GrassTile with randomly generated tallGrass
	 * @param x X position
	 * @param y Y position
	 * @param z Z position
	 * @param tileId The ID of the tile in the map
	 */
	public GrassTile(int x, int y, int z, int tileId) {
		super(x, y, z, Engine.r.nextInt(2)==1, faces, tileId, 2);
	}

}
