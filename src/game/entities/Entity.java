package game.entities;

import org.newdawn.slick.Graphics;

import game.SlickObject;
import game.assets.Sprite;
import game.interfaces.Drawable;
import game.utils.Camera;
import game.utils.Rectangle;

public abstract class Entity extends SlickObject implements Drawable {
	public final float initX;
	public final float initY;
	private Rectangle boundingBox;
	private Sprite sprite;
	private int width;
	private int height;
	private boolean isVisible;

	public Entity(float x, float y, int width, int height, String texture) {
		this.x = x;
		this.y = y;
		this.initX = x;
		this.initY = y;
		this.setWidth(width);
		this.height = height;
		boundingBox = new Rectangle(x, y, width, height);
		sprite = new Sprite(texture);
		isVisible = true;
		// System.out.println(x + " " + y + " " + texture);
	}

	public void move(float x, float y) {
		boundingBox.setLocation(x - Camera.xOffset, y);
		setLocation(x - Camera.xOffset, y);
	}

	public void setTheme(String theme) {
		sprite.setPath(sprite.getPath().substring(0, sprite.getPath().length() - 4) + theme + ".png");
	}

	public void draw(Graphics g) {
		if (getVisible()) {
			g.drawImage(sprite.getImage(), x - Camera.xOffset, y);
		}
		// boundingBox.draw(g);
	}

	public void reset() {
		x = initX;
		y = initY;
	}

	public Rectangle getBoundingBox() {
		return boundingBox;
	}

	public Sprite getSprite() {
		return sprite;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public void setVisiblity(boolean b) {
		isVisible = b;
	}

	public boolean getVisible() {
		return isVisible;
	}

}
