package game.entities;


public class Block extends Entity {
	
	static int size = 48;
	String texture;
	
	public Block(float x, float y, String texture) {
		super(x, y, size, size, texture);
	}
}
