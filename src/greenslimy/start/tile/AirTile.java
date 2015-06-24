package greenslimy.start.tile;

import greenslimy.start.images.ImageLoader;

import java.awt.Image;
import java.awt.image.BufferedImage;


public class AirTile extends Tile {
	
	protected final static BufferedImage[] faces = {ImageLoader.getSubImage(tiles, 64, 576, 64, 64), ImageLoader.getSubImage(tiles, 128, 576, 64, 64), ImageLoader.getSubImage(tiles, 0, 576, 64, 64)};

	public AirTile(int x, int y, int z, int tileId) {
		super(x, y, z, faces, tileId, 0);
	}
	
}
