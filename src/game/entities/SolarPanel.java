package game.entities;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

public class SolarPanel extends Entity {

	boolean powered;
	Firework marvinIsCool;

	public SolarPanel(float x, float y) {
		super(x, y, 264, 120, "brokenpanel");
		getBoundingBox().setColor(Color.black);
		marvinIsCool = new Firework(x, y);
	}

	public boolean addParts(int componentCount) {
		if (componentCount > 0) {
			powered = true;
			marvinIsCool = new Firework(x + 120, y);
			marvinIsCool.launch();
			getSprite().setPath("/res/panel.png");
			return true;
		}
		return false;
	}

	public boolean getPowered() {
		return powered;
	}

	public void reset() {
		super.reset();
		powered = false;
		marvinIsCool.reset();
		getSprite().setPath("/res/brokenpanel.png");
	}

	public void update() {
		marvinIsCool.update();
	}

	public void draw(Graphics g) {
		super.draw(g);
		marvinIsCool.draw(g);
	}

}
