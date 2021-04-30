package model;

import java.util.Random;

public class Hitbox {
	private Point point;
	private int width;
	private int height;
	public static final int MAXWIDTH = 1200;
	public static final int MAXHEIGHT = 1000;
	private static Random rand = new Random();

	public Hitbox() {
		this.point = new Point();
		this.width = 0;
		this.height = 0;
	}

	public Hitbox(int x, int y, int breite, int hoehe) {
		super();
		this.point = new Point(x, y);
		this.setWidth(breite);
		this.setHeight(hoehe);
	}

	public int getX() {
		return this.point.getX();
	}

	public void setX(int x) {
		this.point.setX(x);
	}

	public int getY() {
		return this.point.getY();
	}

	public void setY(int y) {
		this.point.setY(y);
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = Math.abs(width);
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = Math.abs(height);
	}

	public boolean contains(Point p) {
		return contains(p.getX(), p.getY());
	}

	public boolean contains(int x, int y) {
		return this.point.getX() <= x && x <= this.point.getX() + this.width && this.point.getY() <= y
				&& y <= this.point.getY() + this.height;
	}

	public boolean contains(Hitbox hitbox) {
		Point upperleft = new Point(hitbox.getX(), hitbox.getY());
		Point lowerright = new Point(hitbox.getX() + hitbox.getWidth(), hitbox.getY() + hitbox.getHeight());
		return contains(upperleft) && contains(lowerright);
	}

	public static Hitbox generateRandomHitbox () {
		int x = rand.nextInt(MAXWIDTH + 1);
		int y = rand.nextInt(MAXHEIGHT + 1);
		int width = rand.nextInt(MAXWIDTH - x + 1);
		int height = rand.nextInt(MAXHEIGHT - y + 1);
		return new Hitbox(x, y, width, height);
	}

	@Override
	public String toString() {
		return "Hitbox [x=" + this.point.getX() + ", y=" + this.point.getY() + ", width=" + width + ", height=" + height
				+ "]";
	}
}
