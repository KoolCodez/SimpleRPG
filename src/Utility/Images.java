package Utility;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class Images {
	private static Map<String, BufferedImage> imageMap = new HashMap<String, BufferedImage>();
	public Images() {
		imageMap = new HashMap<String, BufferedImage>();
		
	}
	
	private void loadAllImages() {
		
	}
	
	public static BufferedImage getImage(String path, double width, double height) {
		if (imageMap.containsKey(path)) {
			return imageMap.get(path);
		} else if (loadImage(path, width, height)) {
			return getImage(path, width, height);
		} else {
			return null;
		}
	}
	
	public static boolean loadImage(String path, double width, double height) {
		BufferedImage image = TextureUtil.loadImage(path, width, height);
		if (image != null) {
			imageMap.put(path, image);
			return true;
		}
		return false;
	}
}
