package greenslimy.start.tile;

import greenslimy.start.engine.Engine;
import greenslimy.start.images.ImageLoader;
import greenslimy.start.misc.Dimension;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;

public class Tile {
	
	public static final BufferedImage tiles = ImageLoader.loadImage("./images/terrain.png");
	public static final BufferedImage altTiles = ImageLoader.loadImage("./images/tileset.png");
	public static final int TILE_WIDTH = 64;
	public static final int TILE_HEIGHT = 64;
	
	/**The 3 faces that each tile has (0-left, 1-right, 2-top)*/
	private BufferedImage[] faces = {ImageLoader.getSubImage(tiles, 64, 0, 64, 64), ImageLoader.getSubImage(tiles, 0, 64, 64, 64), ImageLoader.getSubImage(tiles, 0, 0, 64, 64)};
	private int x, y, z;
	private boolean tallGrass, render = false;
	/**The faces that are renderable for this object (0-left, 1-right, 2-top)*/
	private boolean[] renderableFaces = {false, false, false};
	private int tileId = -1;
	private int tileType = -1;
	private boolean canMine = true;
	
	/**
	 * TODO: ADD A BETTER WAY TO IMPLEMENT FACES.
	 * - Programmatically add textures with faces = {face1:{tileset, x*xOffs, y*yOffs, xOffs, yOffs}, face2:{...}, face3:{...}}
	 * Use this one so I can reference the tileset by setId, and I can reference the tile by column and row, not by pixel.
	 */
	
	public Tile(int x, int y, int z, BufferedImage[][] faceTextures, int tileId, int tileType) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.tileId = tileId;
		this.tileType = tileType;
		int faceIndex = 0;
		for(BufferedImage[] bia:faceTextures) {
			if(bia.length > 1) {
				this.faces[faceIndex] = bia[Engine.r.nextInt(bia.length)];
			}else{
				this.faces[faceIndex] = bia[0];
			}
			faceIndex++;
		}
	}
	
	public Tile(int x, int y, int z, boolean hasTallGrass, BufferedImage[][] faceTextures, int tileId, int tileType) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.tallGrass = hasTallGrass;
		this.tileId = tileId;
		this.tileType = tileType;
		int faceIndex = 0;
		for(BufferedImage[] bia:faceTextures) {
			if(bia.length > 1) {
				this.faces[faceIndex] = bia[Engine.r.nextInt(bia.length)];
			}else{
				this.faces[faceIndex] = bia[0];
			}
			faceIndex++;
		}
	}
	
	public Tile(int x, int y, int z, BufferedImage[] faceTextures, int tileId, int tileType) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.tileId = tileId;
		this.tileType = tileType;
		this.faces = faceTextures;
	}
	
	public Tile(int x, int y, int z, BufferedImage[] faceTextures, int tileId, int tileType, boolean canMine) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.tileId = tileId;
		this.tileType = tileType;
		this.faces = faceTextures;
		this.canMine = canMine;
	}
	
	public Tile(int x, int y, int z, int tileId, int tileType) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.tileId = tileId;
		this.tileType = tileType;
	}
	
	public Tile(int x, int y, int z, int tileId, int tileType, boolean canMine) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.tileId = tileId;
		this.tileType = tileType;
		this.canMine = canMine;
	}
	
	public Tile(int x, int y, int z, boolean hasTallGrass, BufferedImage[] faceTextures, int tileId, int tileType) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.tallGrass = hasTallGrass;
		this.tileId = tileId;
		this.tileType = tileType;
		this.faces = faceTextures;
	}
	
	public Dimension getDimensions() {
		return new Dimension(this.x, this.y, this.z);
	}
	
	public int getTileHeight() {
		return this.z;
	}
	
	public boolean hasTallGrass() {
		return this.tallGrass;
	}
	
	public int getTileId() {
		return this.tileId;
	}
	
	public int getTileType() {
		return this.tileType;
	}
	
	public void setRenderable(boolean render) {
		this.render = render;
	}
	
	public boolean isRenderable() {
		return this.render;
	}
	
	public boolean[] getRenderableFaces() {
		return this.renderableFaces;
	}

	/**
	 * Sets a certain face to be renderable
	 * @param face 0-left, 1-right, 2-top
	 * @param renderable true or false
	 */
	public void setFaceRenderable(int face, boolean renderable) {
		this.renderableFaces[face] = renderable;
	}

	public BufferedImage[] getFaces() {
		return faces;
	}
	
	/**
	 * Gets a specific face for the tile
	 * @param index 0-left, 1-right, 2-top
	 * @return the face image
	 */
	public BufferedImage getFace(int index) {
		return faces[index];
	}
	
	public boolean isMinable() {
		return this.canMine;
	}
	
	public static BufferedImage highlight(BufferedImage img) {
		Color newColor = Color.yellow;
		int aMask = 0xFF000000;
		int rMask = 0xFF0000;
		int gMask = 0xFF00;
		int bMask = 0xFF;
		int newC = newColor.getRGB();
		int newR = (newC & rMask) >> 16;
		int newG = (newC & gMask) >> 8;
		int newB = (newC & bMask);
		for(int x=0;x<img.getWidth();x++) {
			for(int y=0;y<img.getHeight();y++) {
				int color = img.getRGB(x, y);
				int oldA = (color & aMask) >> 24;
				int oldR = (color & rMask) >> 16;
				int oldG = (color & gMask) >> 8;
				int oldB = (color & bMask);
				oldR = newR;
				oldG = newG;
				oldB = newB;
				int addedColors = (oldA << 24)|(oldR << 16)|(oldG << 8)|(oldB);
				img.setRGB(x, y, addedColors);
			}
		}
		return img;
	}

}
