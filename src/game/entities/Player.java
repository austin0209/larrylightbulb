package game.entities;

import java.util.ArrayList;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import game.Game;
import game.assets.Assets;
import game.assets.Playfield;
import game.ui.Inventory;
import game.ui.LifeBar;
import game.utils.Camera;
import game.utils.Controller;
import game.utils.Rectangle;
import game.utils.Timer;

public class Player extends KineticEntity {

	private Rectangle weapon;
	private Timer attackTimer;
	private Timer cooldown;
	private Timer invincibleTimer;
	private Timer flashTimer;
	private boolean isStomping;
	private boolean isInvincible;
	private int weaponWidth;
	private int weaponDamage;
	private int componentCount;
	private boolean attacking;
	private Animation currentAnimation;
	private boolean pickedUpComponentBefore;
	private LifeBar lifebar;
	private Inventory inventory;
	private int attackOffset;
	private int score;

	// Color arrays for particle effects.
	Color[] groundStompColors = { Color.white, Color.black, Color.gray, Color.orange };
	Color[] killLarryColors = { Color.red, Color.pink, Color.magenta, Color.orange };
	Color[] plugSparkColors = { Color.yellow, Color.white };

	public Player(float x, float y) {
		super(x, y, 41, 108, 7, "player");
		rects = new ArrayList<Rectangle>();
		updateRectangles();
		weapon = new Rectangle(x + getWidth(), y, 0, 45);
		spriteOffset = 15;
		attackTimer = new Timer(300);
		cooldown = new Timer(1000);
		invincibleTimer = new Timer(1500);
		flashTimer = new Timer(150);
		weaponWidth = 61;
		weaponDamage = 2;
		life = 100;
		score = 0;
		initLife = life;
		currentAnimation = Assets.rightWalk;
		currentAnimation.start();
		particles = new ArrayList<Particle>();
		pickedUpComponentBefore = false;
		Assets.respawn.play();
	}

	public void update() {
		input();
		applyForce();
		updateRectangles();
		collision();
		attack();
		updateParticles();
		checkDeath();
		checkInvis();
		Game.level.getHUD().getScoreboard().setScore(Integer.toString(score));
	}

	private void input() {
		attackInput();
		stomp();
		moveInput();
	}

	private void checkInvis() {
		if (isInvincible) {
			invincibleTimer.update();
			flashTimer.update();
			if (flashTimer.isFinished()) {
				setVisiblity((getVisible() ? false : true));
				flashTimer.reset();
				flashTimer.start();
			}
			if (invincibleTimer.isFinished()) {
				setVisiblity(true);
				isInvincible = false;
				invincibleTimer.reset();
			}
		}
	}

	private void checkDeath() {
		if (isDead() && alive) {
			die();
		}
	}

	public void draw(Graphics g) {
		if (getVisible()) {
			// g.drawImage(getSprite().getImage(), x - Camera.xOffset, y);
			// weapon.draw(g);
			if (facing == Direction.RIGHT) {
				if (!attacking) {
					currentAnimation = Assets.rightWalk;
					attackOffset = 0;
				} else {
					currentAnimation = Assets.attackRightAnimation;
					attackOffset = 0;
				}
			} else if (facing == Direction.LEFT) {
				if (!attacking) {
					currentAnimation = Assets.leftWalk;
					attackOffset = 0;
				} else {
					currentAnimation = Assets.attackLeftAnimation;
					attackOffset = 192 / 2;
				}
			}
			currentAnimation.draw(x - Camera.xOffset - attackOffset - spriteOffset, y - 10);

		}
		// getBoundingBox().draw(g);
		for (Rectangle r : rects) {
			// r.draw(g);
		}
		drawParticles(g);

	}

	protected void collision() {
		if (alive) {
			ground = false;
			for (Entity e : Playfield.entities) {

				if (e instanceof Component && this.getBoundingBox().intersects(e.getBoundingBox())) {
					((Component) e).remove();
					addComponent();
					// System.out.println(componentCount);
				}

				if (e instanceof SolarPanel && this.getBoundingBox().intersects(e.getBoundingBox())
						&& Game.controller.isPressing(Controller.Button.DOWN) && !((SolarPanel) e).getPowered()
						&& ((SolarPanel) e).addParts(componentCount)) {
					componentCount--;
					score += 1000;
				}

				if (e instanceof KineticEntity) {
					if (isEnemy(e) && !isInvincible) {
						if (this.getBoundingBox().intersects(((Walker) e).getRects().get(0))) {
							if (!isStomping) {
								y -= (collWidth + 1);
								velocity.y = -jumpHeight;
								life -= 25;
								hit();
							} else {
								Assets.enemyHit.play();
								((Walker) e).kill();
								score += 50;
							}
						} else if (this.sidesIntersects(e)) {
							life -= 25;
							hit();
						}
					}
				}

				if ((e instanceof Spike) && this.getBoundingBox().intersects(e.getBoundingBox())) {
					if (!isInvincible) {
						isStomping = false;
						y -= (collWidth + 1);
						velocity.y = -jumpHeight;
						life -= 50;
						hit();
						;
					} else {
						y -= (collWidth + 1);
						velocity.y = -jumpHeight;
					}
				}

				if ((e instanceof Block || e instanceof Platform) && !(e instanceof Barrier)) {
					// Bottom
					if (rects.get(1).intersects(e.getBoundingBox())) {
						velocity.y = 0;
						ground = true;
						y = e.getBoundingBox().getTop() - this.getBoundingBox().getHeight();

						// Particle effects for ground stomp.
						if (isStomping) {
							spawnParticles(200, 10, groundStompColors);
							Assets.explosion.play();
							isStomping = false;
						}
					}
					// Top
					else if (rects.get(0).intersects(e.getBoundingBox())) {
						velocity.y = 0;
						y = e.getBoundingBox().getBottom() + collWidth + 5;
					}
					// Left
					else if (rects.get(2).intersects(e.getBoundingBox()) && facing == Direction.LEFT && !isStomping) {
						x = e.getBoundingBox().getRight() - spriteOffset + collWidth;
					}
					// Right
					else if (rects.get(3).intersects(e.getBoundingBox()) && facing == Direction.RIGHT && !isStomping) {
						x = e.getBoundingBox().getLeft() - spriteOffset - this.getWidth() - collWidth;
					}
				}
			}

		}
	}

	protected void moveInput() {
		// Moves player based on input
		if (alive) {
			if (Game.controller.isPressing(Controller.Button.LEFT) && !isStomping) {
				x -= moveSpeed;
				facing = Direction.LEFT;
				currentAnimation.stop();
				if (!attacking)
					currentAnimation = Assets.leftWalk;
				currentAnimation.start();
			}
			if (Game.controller.isPressing(Controller.Button.RIGHT) && !isStomping) {
				x += moveSpeed;
				facing = Direction.RIGHT;
				currentAnimation.stop();
				if (!attacking)
					currentAnimation = Assets.rightWalk;
				currentAnimation.start();
			}
			// Jump
			if ((Game.controller.isPressing(Controller.Button.JUMP) || Game.controller.isPressing(Controller.Button.UP))
					&& ground) {
				y -= (collWidth + 1);
				velocity.y = -jumpHeight;
				Assets.jump.play();
			}
			// Moves collision rectangles
			getBoundingBox().setLocation(x + spriteOffset, y);

			// Moves weapon.
			if (facing == Direction.RIGHT) {
				weapon.setColor(Color.cyan);
				weapon.setLocation(x + 82, y + 65);
			} else if (facing == Direction.LEFT) {
				weapon.setColor(Color.green);
				weapon.setLocation(x - weapon.getWidth() - spriteOffset, y + 65);
			}
		}
	}

	private void attackInput() {
		if (alive && Game.controller.isPressing(Controller.Button.ATTACK) && !attackTimer.isStarted()) {
			attack();
			attacking = true;
			Assets.attackLeftAnimation.restart();
			Assets.attackRightAnimation.restart();
			attackTimer.start();
		}
	}

	private void die() {
		kill();
		y -= (collWidth + 1);
		velocity.y = -jumpHeight;
	}

	public void reset() {
		super.reset();
		Assets.respawn.play();
		componentCount = 0;
		attackTimer.reset();
		cooldown.reset();
		invincibleTimer.reset();
		flashTimer.reset();
		isStomping = false;
		isInvincible = false;
		this.setVisiblity(true);
		facing = Direction.RIGHT;
		Camera.xOffset = 0;
		velocity.y = 0;
		velocity.x = 0;
		alive = true;
		pickedUpComponentBefore = false;
	}

	private boolean isEnemy(Entity e) {
		return e instanceof Walker;
	}

	private void attack() {
		if (attackTimer.isStarted()) {
			attackTimer.update();
			if (!attackTimer.isFinished() && attackTimer.getTime() > 225) {
				// weapon.setColor(Color.red);
				weapon.setWidth(weaponWidth);
				for (int i = Playfield.entities.size() - 1; i >= 0; i--) {
					if (isEnemy(Playfield.entities.get(i))
							&& weapon.intersects(Playfield.entities.get(i).getBoundingBox())) {
						((KineticEntity) Playfield.entities.get(i)).decreaseHealth(weaponDamage);
						// weapon.setWidth(0);
						score += 25;
						if (facing == Direction.RIGHT) {
							spawnParticles(weapon.x + weapon.getWidth(), weapon.y, 3, 10, plugSparkColors);
						} else if (facing == Direction.LEFT) {
							spawnParticles(weapon.x, weapon.y, 3, 10, plugSparkColors);
						}
						Assets.enemyHit.play();
						Assets.attackLeftAnimation.stop();
						Assets.attackRightAnimation.stop();
						attacking = false;
						attackTimer.finish();
					}
				}
			} else if (attackTimer.isFinished()) {
				Assets.attackLeftAnimation.stop();
				Assets.attackRightAnimation.stop();
				attacking = false;
				attackTimer.reset();
			}
		}
	}

	public void addComponent() {
		componentCount++;
		Assets.pickUpComponent.play();
		if (life < 100)
			life += 25;
		pickedUpComponentBefore = true;
	}

	private void hit() {
		Assets.hit.play();
		invincibleTimer.start();
		isInvincible = true;
		spawnParticles(100, 5, killLarryColors);
	}

	private void stomp() {
		if (Game.controller.hasPressed(Controller.Button.DOWN) && !ground) {
			isStomping = true;
		}
		if (isStomping) {
			// Apply gravity two times over to achieve a stomping effect.
			applyForce();
		}
	}

	public int getComponents() {
		return componentCount;
	}

	public boolean hasPickedUpComponentBefore() {
		return pickedUpComponentBefore;
	}

	public Inventory getInventory() {
		return inventory;
	}

	public LifeBar getLifebar() {
		return lifebar;
	}

	public Animation getAnimation() {
		return currentAnimation;
	}

	public void setHealth(int health) {
		life = health;
	}
}
