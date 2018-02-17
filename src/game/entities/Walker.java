package game.entities;

import org.newdawn.slick.Graphics;

import game.assets.Assets;
import game.assets.Playfield;
import game.utils.Camera;

public class Walker extends KineticEntity {

	public Walker(float x, float y, String texture) {
		super(x, y, 48, 48, 3, texture);
		reset();
		life = 2;
		initLife = life;
		collWidth = 5;
	}

	public void update() {
		applyForce();
		moveInput();
		updateRectangles();
		collision();
		if (isDead()) {
			kill();
		}

	}

	public void draw(Graphics g) {
		Assets.walkerWalk.draw(x - Camera.xOffset, y);

	}

	protected void collision() {
		if (alive) {
			ground = false;
			for (Entity e : Playfield.entities) {
				if (!e.equals(this) && !(e instanceof Component)) {
					// Bottom
					if (rects.get(1).intersects(e.getBoundingBox())) {
						velocity.y = 0;
						ground = true;
						y = e.getBoundingBox().getTop() - getBoundingBox().getHeight();
					}
					// Left
					else if (rects.get(2).intersects(e.getBoundingBox()) && facing == Direction.LEFT) {
						velocity.x *= -1;
					}
					// Right
					else if (rects.get(3).intersects(e.getBoundingBox()) && facing == Direction.RIGHT) {
						velocity.x *= -1;
					}

				}
			}

		}

	}

	protected void moveInput() {
		x += velocity.x;
		getBoundingBox().setLocation(x + spriteOffset, y);
		if (velocity.x > 0) {
			facing = Direction.RIGHT;
		} else if (velocity.x < 0) {
			facing = Direction.LEFT;
		}
	}

}
