package game.entities;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import game.assets.Assets;
import game.utils.Vector;

public class Firework extends KineticEntity {

	boolean start;
	Color[] fireworkColors;
	Vector velocity;

	public Firework(float x, float y) {
		super(x, y, 50, 50, 1, "none");
		fireworkColors = new Color[] { Color.red, Color.blue, Color.magenta, Color.pink, Color.yellow, Color.magenta };
		velocity = new Vector(0, moveSpeed);
	}

	public void launch() {
		Assets.firework.play();
		start = true;
	}

	public void reset() {
		start = false;
		velocity.reset();
		velocity.y = moveSpeed;
		resetParticles();
	}

	@Override
	protected void collision() {
		// TODO Auto-generated method stub

	}

	@Override
	public void draw(Graphics g) {
		drawParticles(g);
	}

	@Override
	public void update() {
		if (start) {
			y -= velocity.y;
			velocity.y += moveSpeed;
			spawnParticles(100, 3, fireworkColors);
		}
		updateParticles();

	}
}
