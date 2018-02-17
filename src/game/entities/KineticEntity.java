package game.entities;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import game.Game;
import game.utils.Camera;
import game.utils.Rectangle;
import game.utils.Vector;

public abstract class KineticEntity extends Entity {

	int collWidth;
	static float gravity = 0.8f;
	Vector velocity;
	int jumpHeight = 20;
	float moveSpeed = 0;
	public boolean alive = true;
	boolean ground = false;
	int spriteOffset = 0;
	int life;
	int initLife;
	ArrayList<Particle> particles;
	ArrayList<Rectangle> rects;

	enum Direction {
		LEFT, RIGHT, NONE
	};

	Direction facing;

	public KineticEntity(float x, float y, int width, int height, float moveSpeed, String texture) {
		super(x, y, width, height, texture);
		this.moveSpeed = moveSpeed;
		collWidth = (int) moveSpeed;
		alive = true;
		rects = new ArrayList<Rectangle>();
		velocity = new Vector(0, 0);
		updateRectangles();
		facing = Direction.RIGHT;
		particles = new ArrayList<Particle>();
	}

	protected void kill() {
		alive = false;
	}

	public boolean isAlive() {
		return alive;
	}

	protected void updateRectangles() {
		rects.clear();
		// Top
		rects.add(new Rectangle(getBoundingBox().getLeft(), getBoundingBox().getTop() - collWidth,
				getBoundingBox().getWidth(), collWidth));
		// Bottom
		int bottomHeight = (int) (5 + (velocity.y + 2));
		rects.add(new Rectangle(getBoundingBox().getLeft(), getBoundingBox().getBottom(), getBoundingBox().getWidth(),
				bottomHeight > 40 ? 40 : bottomHeight));
		// Left
		rects.add(new Rectangle(getBoundingBox().getLeft() - collWidth, getBoundingBox().getTop() + collWidth,
				collWidth, getBoundingBox().getHeight() - collWidth * 2));
		// Right
		rects.add(new Rectangle(getBoundingBox().getRight(), getBoundingBox().getTop() + collWidth, collWidth,
				getBoundingBox().getHeight() - collWidth * 2));
	}

	protected void applyForce() {
		velocity.y += gravity;
		y += velocity.y;
	}

	public void decreaseHealth(int damage) {
		life -= damage;
	}

	public int getHealth() {
		return life;
	}

	public void resetHealth() {
		life = initLife;
		alive = true;
	}

	public boolean isDead() {
		return life <= 0;
	}

	public void reset() {
		super.reset();
		resetHealth();
		facing = Direction.LEFT;
		velocity.reset();
		velocity.x = -moveSpeed;
	}

	public boolean sidesIntersects(Entity e) {
		for (Rectangle r : rects) {
			if (r.intersects(e.getBoundingBox()) && !r.equals(rects.get(1))) {
				return true;
			}
		}
		return false;
	}

	public ArrayList<Rectangle> getRects() {
		return rects;
	}

	protected void spawnParticles(int max, int size, Color[] colors) {
		int random = (int) (Math.random() * max) + 5;
		for (int i = 0; i < random; i++) {
			particles.add(new Particle(x - Camera.xOffset + getWidth() / 2, y + getHeight() / 2, size, colors));
		}
	}

	protected void spawnParticles(float x, float y, int max, int size, Color[] colors) {
		int random = (int) (Math.random() * max) + 5;
		for (int i = 0; i < random; i++) {
			particles.add(new Particle(x - Camera.xOffset, y, size, colors));
		}
	}

	protected void updateParticles() {
		for (int i = particles.size() - 1; i >= 0; i--) {
			particles.get(i).update();
			if (particles.get(i).y > Game.SCREEN_HEIGHT + 50 || particles.get(i).y < 0 - 50) {
				particles.remove(i);
			}
		}
	}

	protected void drawParticles(Graphics g) {
		for (Particle p : particles) {
			p.draw(g);
		}
	}

	protected void resetParticles() {
		particles.clear();
	}

	protected abstract void collision();

	public abstract void update();
}
