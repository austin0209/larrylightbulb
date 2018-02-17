package game.entities;

import game.utils.Timer;

public class Component extends Entity {

	Timer moveTimer;
	int numMovements;
	int speed;

	public Component(float x, float y, String texture) {
		super(x, y, 84, 75, "component");
		speed = 2;
		numMovements = 3;
		moveTimer = new Timer(500);
		moveTimer.start();
	}

	public void remove() {
		y = 4000;
		getBoundingBox().setLocation(x, 4000);
	}

	public void update() {
		moveTimer.update();
		move();
	}

	public void move() {
		if (moveTimer.isFinished()) {
			y += speed;
			numMovements--;
			if (numMovements == 0) {
				numMovements = 3;
				speed *= -1;
			}
			moveTimer.reset();
			moveTimer.start();
		}
	}

}
