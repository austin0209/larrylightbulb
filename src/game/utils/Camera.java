package game.utils;

import game.Game;
import game.assets.Playfield;

public class Camera {

	public static float xOffset, yOffset;
	Rectangle viewport;

	public Camera() {
		viewport = new Rectangle(xOffset, yOffset, Game.SCREEN_WIDTH, Game.SCREEN_HEIGHT);

	}

	public void update(float x, float y) {
		x -= Game.SCREEN_WIDTH / 2;
		viewport.setLocation(x, y);

		if (viewport.getRight() > Playfield.levelWidth) { // Test if player is at end of level
			viewport.setLocation(Playfield.levelWidth - viewport.getWidth(), y);
		} else if (viewport.getLeft() < 0) { // Test if player is at beginning of level
			viewport.setLocation(0, y);
		}

		xOffset = viewport.getLeft();
		yOffset = viewport.getTop();

		// System.out.println("xOff: " + xOffset + "\nyOff: " + yOffset);
	}

	public Rectangle getViewport() {
		return viewport;
	}

}
