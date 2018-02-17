package game.utils;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import game.SlickObject;
import game.interfaces.Drawable;

public class Rectangle extends SlickObject implements Drawable {

	private int width, height;
	private Color c;
	private float top, bottom, left, right;

	public Rectangle(float x, float y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		top = y;
		bottom = y + height;
		left = x;
		right = x + width;
		c = Color.white;
	}

	public boolean intersects(Rectangle r) {
		if (right > r.getLeft() && left < r.getRight() && bottom > r.getTop() && top < r.getBottom()) {
			return true;
		}
		return false;
	}

	public void draw(Graphics g) {
		g.setColor(c);
		g.fillRect(x - Camera.xOffset, y - Camera.yOffset, width, height);
	}

	public void setColor(Color c) {
		this.c = c;
	}

	public float getTop() {
		return top;
	}

	public float getBottom() {
		return bottom;
	}

	public float getLeft() {
		return left;
	}

	public float getRight() {
		return right;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public void setWidth(int w) {
		width = w;
	}

	public void setLocation(float x, float y) {
		super.setLocation(x, y);
		top = y;
		bottom = y + height;
		left = x;
		right = x + width;
	}

}
