package Utility;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import world.materials.Texture;

public class TextureUtil {
	public static BufferedImage compileImage(ArrayList<Image> images, int size) {
		BufferedImage compiledImage = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
		Graphics g = compiledImage.createGraphics();
		for (int i = 0; i < images.size(); i++) {
			g.drawImage(images.get(i), 0, 0, null);
		}
		
		return compiledImage;
	}
	
	
	
	public static BufferedImage loadImage(String pathFromTextures, double width, double height) {
		Image i;
		try {
			String name = "/Resources/Textures/" + pathFromTextures;
			URL url = TextureUtil.class.getResource(name);
			i = ImageIO.read(url);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		i = i.getScaledInstance((int) width, (int) height, Image.SCALE_SMOOTH);
		BufferedImage newI = new BufferedImage((int) width, (int) height, BufferedImage.TYPE_INT_ARGB);
		Graphics g = newI.getGraphics();
		g.drawImage(i, 0, 0, null);
		return newI;
	}
	
	public static boolean drawImage(Graphics g, BufferedImage image, int rotationDegrees, int x, int y) {
		int a = (int) Math.pow(Math.max(image.getWidth(), image.getHeight()), 2);
		int size = (int) (Math.sqrt(2 * a));
		int difference = (int) ((size - image.getWidth()) / 2);
		image = rotate(image, rotationDegrees, image.getWidth(), image.getHeight());
		g.drawImage(image, x - difference, y - difference, null);
		return true;
	}
	
	public static BufferedImage rotate(Image image, int degrees, double width, double height) {
		int a = (int) Math.pow(Math.max(width, height), 2);
		int size = (int) (Math.sqrt(2 * a));
		
		ImageIcon icon = new ImageIcon(image);
		int difference = (int) ((size - icon.getIconWidth()) / 2);
		BufferedImage newImage = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = (Graphics2D) newImage.getGraphics();
		g2.rotate(Math.toRadians(degrees), size / 2, size / 2);
		g2.drawImage(image, difference, difference, null);
		return newImage;
	}
	
	public static boolean[][] getVisiblePixels(BufferedImage image) {
		boolean[][] arr = new boolean[image.getWidth()][image.getHeight()];
		for (int y = 0; y < image.getHeight(); y++) {
			for (int x = 0; x < image.getWidth(); x++) {
				int pixel = image.getRGB(x, y);
				int alpha = (pixel >> 24) & 0xff;
				arr[x][y] = (alpha != 0);
			}
		}
		Arrays.deepToString(arr);
		return arr;
	}
	
	public static BufferedImage fillWhiteWithTexture(BufferedImage image, Texture m) {
		Graphics g = image.getGraphics();
		for (int y = 0; y < image.getHeight(); y++) {
			for (int x = 0; x < image.getWidth(); x++) {
				int pixel = image.getRGB(x, y);
				if (pixel == -1) {
					image.setRGB(x, y, m.getColor().getRGB());
					//g.setColor(m.getColor());
					//g.fillRect(x, y, 1, 1);
					//System.out.println("replacing " + pixel + " with " + m.getColor().getRGB());
				}
			}
		}
		return image;
	}
}
