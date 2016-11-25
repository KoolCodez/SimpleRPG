package pillars;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

import Utility.Images;
import Utility.PointMap;
import Utility.TextureUtil;
import world.World;
import world.Things.Thing;
import world.materials.Material;

public class Core {
	public static BufferedImage image;
	public static int rotate = 0;
	public static World world;
	public static Thing myThing;
	
	public static void main(String[] args) {
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		image = Images.getImage("Orange.png", 100, 100);
		World world = new World();
		myThing = new Thing(0, 0, 100, 100, "Orange.png", new Material(), 100, 1);
		world.addThing(0, 0, myThing, 0);
		//t.enactImpulse(10, 0);
		JPanel panel = new JPanel() {
			@Override
			public void paintComponent(Graphics g) {
				List<Thing> things = world.getAllThings();
				for (Thing t : things) {
					t.draw(g);
					//t.enactForce(5, 90);
				}
			}
		};
		panel.setVisible(true);
		panel.setBounds(0, 0, d.width, d.height);
		Display display = new Display(d.width, d.height, "SimpleRPG", panel);
		Control c = new Control(display);
	}
}
