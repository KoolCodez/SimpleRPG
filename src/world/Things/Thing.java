package world.Things;

import java.awt.Image;
import java.awt.Point;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import Utility.Images;
import Utility.TextureUtil;
import pillars.Core;
import world.materials.Material;

public class Thing {
	private Rectangle2D outline;
	public Material material;
	private double imageBuffer;
	private String imagePath;
	private double angle;
	private double xVel;
	private double yVel;
	private double mass;
	private int layer;

	public Thing(double x, double y, double w, double h, String path, Material m) {
		outline = new Rectangle2D.Double(x, y, w, h);
		this.imagePath = path;
		material = m;
		xVel = 0;
		yVel = 0;
	}

	public Point getPoint() {
		Point p = new Point((int) outline.getX(), (int) outline.getY());
		return p;
	}

	public Point2D getLoc() {
		return new Point2D.Double(outline.getX(), outline.getY());
	}

	public double getWidth() {
		return outline.getWidth();
	}

	public double getHeight() {
		return outline.getHeight();
	}

	public void move(double deltaX, double deltaY) {
		outline = new Rectangle2D.Double(outline.getX() + deltaX, outline.getY() + deltaY, outline.getWidth(),
				outline.getHeight());
	}

	public Rectangle2D getRect() {
		return outline;
	}

	public BufferedImage getImage() {
		Image image = Images.getImage(imagePath, outline.getWidth(), outline.getHeight());
		return TextureUtil.rotate(image, (int) angle, getWidth(), getHeight());
	}

	public void enactForce(double newtons, double direction) {
		double xF = newtons * Math.cos(Math.toRadians(direction));
		double yF = newtons * Math.sin(Math.toRadians(direction));
		xVel += xF / mass;
		yVel += yF / mass;
	}

	public void physUpdate() {
		if (xVel != 0 || yVel != 0) {
			outline.setRect(outline.getY() + xVel, outline.getX() + yVel, getWidth(), getHeight());
			boolean move = Core.world.checkAllCollisions(this, layer);
			if (!move) {
				outline.setRect(outline.getY() - xVel, outline.getX() - yVel, getWidth(), getHeight());
			}
		}
	}
}
