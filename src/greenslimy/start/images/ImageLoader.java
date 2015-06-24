package greenslimy.start.images;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class ImageLoader {
	
	public static BufferedImage loadImage(String path) {
		BufferedImage i = new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB);
		try {
			i = ImageIO.read(new File(path));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return i;
	}
	
	public static BufferedImage getSubImage(BufferedImage parent, int offX, int offY, int width, int height) {
		BufferedImage i = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		try {
			i = parent.getSubimage(offX, offY, width, height);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return i;
	}

}
