package pillars;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

import Utility.Images;
import Utility.PointMap;
import Utility.TextureUtil;
import world.World;

public class Core {
	public static BufferedImage image;
	public static int rotate = 0;
	public static World world;
	
	public static void main(String[] args) {
		System.out.println("Hello");
		PointMap<String> p = new PointMap<String>();
		System.out.println("Goodbye");
		JFrame f = new JFrame("test");
		f.setVisible(true);
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		f.setBounds(0, 0, d.width, d.height);
		image = Images.getImage("Orange.png", 100, 100);
		JPanel panel = new JPanel() {
			@Override
			public void paintComponent(Graphics g) {
				//g.drawImage(image, 0, 0, null);
				TextureUtil.drawImage(g, image, rotate, 20, 20);
				rotate++;
				g.drawRect(20, 20, 100, 100);
			}
		};
		panel.setVisible(true);
		f.add(panel);
		while (true) {
			f.revalidate();
			f.repaint();
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
