package game.entities;

public class Platform extends Entity {

	public static int width = 384;
	public static int height = 72;

	public Platform(float x, float y) {
		super(x, y, width, height, "platform");
	}

}
