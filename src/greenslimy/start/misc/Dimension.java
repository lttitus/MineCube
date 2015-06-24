package greenslimy.start.misc;

/**
 * This holds a 3-dimensional value
 * @author Greenslimy
 */
public class Dimension {
	
	private int x, y, z = 0;
	
	public Dimension(int width, int length, int depth) {
		this.x = width;
		this.y = length;
		this.z = depth;
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	public int getZ() {
		return this.z;
	}

}
