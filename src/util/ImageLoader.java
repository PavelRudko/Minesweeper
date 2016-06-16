package util;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageLoader {
	
	private static final int MAGENTA = -65281;
	
	public static BufferedImage loadImage(String filename) {
		return loadImage(filename, false);
	}
	
	public static BufferedImage loadImage(String filename, boolean transparentBackground) {
		BufferedImage image = null;
		try {
			image = ImageIO.read(ImageLoader.class.getResource("/resources/" + filename));
			if(transparentBackground) {
				makeTransparentBackground(image);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return image;
	}
	
	private static void makeTransparentBackground(BufferedImage image) {
		for(int x = 0; x < image.getWidth(); x++) {
			for(int y = 0; y < image.getHeight(); y++) {
				int argb = image.getRGB(x, y);
				if(argb == MAGENTA) {
					image.setRGB(x, y, 0);
				}
			}
		}
	}
	
}
