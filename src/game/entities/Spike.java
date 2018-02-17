package game.entities;

public class Spike extends Entity {

	int spriteOffset;

	public Spike(float x, float y) {
		super(x, y, 48, 15, "spike");
		spriteOffset = 3;
		getBoundingBox().setLocation(x + spriteOffset, y);
	}

}
