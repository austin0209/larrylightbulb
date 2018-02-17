package game.entities;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import game.SlickObject;
import game.interfaces.Drawable;
import game.interfaces.Updatable;
import game.utils.Camera;
import game.utils.Rectangle;
import game.utils.Vector;

public class Particle extends SlickObject implements Updatable, Drawable {

	Rectangle boundingBox;
	Vector velocity;
	int selectedColor;
	Color[] colors; // = { Color.blue, Color.cyan, Color.red, Color.yellow };

	public Particle(float x, float y, int sizeFactor, Color[] colors) {
		this.x = x;
		this.y = y;
		this.colors = colors;
		int size = (int) (Math.random() * sizeFactor) + 3;
		boundingBox = new Rectangle(x, y, size, size);
		velocity = new Vector((float) (Math.random() * 11 - 5), (float) (Math.random() * -20));
		selectedColor = (int) (Math.random() * colors.length);
	}

	public void update() {
		applyForce();
		boundingBox.setLocation(x + Camera.xOffset, y);
		x += velocity.x;
		y += velocity.y;

	}

	protected void applyForce() {
		velocity.y += KineticEntity.gravity;
		y += velocity.y;
	}

	public void draw(Graphics g) {
		boundingBox.setColor(colors[selectedColor]);
		boundingBox.draw(g);
	}

}
