package world.Things;

import java.awt.Graphics;
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

	public Thing(double x, double y, double w, double h, String path, Material m, double mass, int layer) {
		outline = new Rectangle2D.Double(x, y, w, h);
		this.imagePath = path;
		material = m;
		xVel = 0;
		yVel = 0;
		xForce = 0;
		yForce = 0;
		this.mass = mass;
		this.layer = layer;
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
	
	public void draw(Graphics g) {
		TextureUtil.drawImage(g, getImage(), (int) angle, (int) outline.getX(), (int) outline.getY());
	}

	public BufferedImage getImage() {
		Image image = Images.getImage(imagePath, outline.getWidth(), outline.getHeight());
		return (BufferedImage) image;
		//return TextureUtil.rotate(image, (int) angle, getWidth(), getHeight());
	}
	private double xForce;
	private double yForce;
	public void enactForce(double newtons, double direction) {
		xForce += newtons * Math.cos(Math.toRadians(direction));
		yForce += newtons * Math.sin(Math.toRadians(direction));
	}
	
	public void enactImpulse(double speed, double direction) {
		if (xVel / Math.cos(direction) < speed) {
			xVel = speed * Math.cos(Math.toRadians(direction));
			yVel = speed * Math.sin(Math.toRadians(direction));
		}
	}
	
	private static final double MU = .05;
	private static final double G = 9.8;
	
	public void enactFriction() {
		double friction = MU * (mass);
		double direction = Math.atan2(yVel, xVel);
		if (xVel / Math.cos(direction) > 0) {
			if (Math.abs(xVel) < .01 && Math.abs(yVel) < .01) {
				xVel = 0;
				yVel = 0;
			} else {
				direction = Math.toRadians(Math.toDegrees(direction) + 180);
				xVel += friction * Math.cos(direction) / mass;
				yVel += friction * Math.sin(direction) / mass;
			}
			
		}
	}

	public void physUpdate() {
		xVel += xForce / mass;
		yVel += yForce / mass;
		enactFriction();
		xForce = 0;
		yForce = 0;
		if (xVel != 0 || yVel != 0) {
			if (outline.getY() > 1000) {
				outline = new Rectangle2D.Double(outline.getX() + xVel, yVel, getWidth(), getHeight());
			}
			outline = new Rectangle2D.Double(outline.getX() + xVel, outline.getY() + yVel, getWidth(), getHeight());
			boolean move = true;//Core.world.checkAllCollisions(this, layer);
			if (!move) {
				outline.setRect(outline.getY() - xVel, outline.getX() - yVel, getWidth(), getHeight());
			}
		}
	}
}
