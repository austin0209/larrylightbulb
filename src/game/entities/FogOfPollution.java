package game.entities;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import game.Game;
import game.assets.Playfield;
import game.utils.Camera;

/**
 * Fog that chases the player.
 * 
 * @author Wagner SET
 *
 */
public class FogOfPollution extends Entity {

	float speed = 2.8f;
	final float initSpeed;
	private boolean startMoving;
	int verticalSpeed;

	/**
	 * Sets the fog's x, y, vertical speed, and horizontal speed.
	 * 
	 * @param x
	 *            The fog's x location.
	 * @param y
	 *            The fog's y location.
	 */
	public FogOfPollution(float x, float y) {
		super(x, y, 50000, Game.SCREEN_HEIGHT + 48 * 2, "fog");
		getBoundingBox().setColor(Color.black);
		verticalSpeed = 1;
		initSpeed = speed;
	}

	public void update(Player larry) {
		checkGracePeriod(larry);
		if (startMoving) {
			move(x + speed + Camera.xOffset, y);
			if (x + 50000 > Playfield.levelWidth - (768 + 48 * 7) && larry.hasPickedUpComponentBefore()) {
				speed = 0;
			}

			checkCollision(larry);
			getBoundingBox().setLocation(x, y);
			if (y + verticalSpeed <= 0 && y + getHeight() + verticalSpeed > Game.SCREEN_HEIGHT) {
				y += verticalSpeed;
			} else {
				verticalSpeed *= -1;
			}

		}
	}

	public void reset() {
		super.reset();
		startMoving = false;
		speed = initSpeed;
		getBoundingBox().setLocation(x, y);
	}

	private void checkGracePeriod(Player larry) {
		if (!startMoving) {
			startMoving = larry.x > 48 * 10 ? true : false;
		}
	}

	private void checkCollision(Player larry) {
		if (this.getBoundingBox().intersects(larry.getBoundingBox())) {
			larry.decreaseHealth(1);
		}
	}

	public void draw(Graphics g) {
		g.drawImage(getSprite().getImage(), x + getWidth() - Camera.xOffset, y);
		getBoundingBox().draw(g);
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public void manuallyMove(float x) {
		move(x, this.y);
		getBoundingBox().setLocation(x, y);
		if (y + verticalSpeed <= 0 && y + getHeight() + verticalSpeed > Game.SCREEN_HEIGHT) {
			y += verticalSpeed;
		} else {
			verticalSpeed *= -1;
		}
	}
}
