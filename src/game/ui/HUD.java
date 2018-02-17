package game.ui;

import org.newdawn.slick.Graphics;

import game.entities.Player;
import game.interfaces.Drawable;
import game.interfaces.Updatable;

public class HUD implements Drawable, Updatable {

	Inventory inventory;
	LifeBar lifebar;
	Scoreboard scoreboard;
	Player larry;

	public HUD(Inventory inventory, LifeBar lifebar, Scoreboard scoreboard) {
		this.inventory = inventory;
		this.lifebar = lifebar;
		this.scoreboard = scoreboard;
	}

	@Override
	public void update() {
		lifebar.update();
		scoreboard.update();
	}

	@Override
	public void draw(Graphics g) {
		inventory.draw(g);
		lifebar.draw(g);
		scoreboard.draw(g);

	}

	public Scoreboard getScoreboard() {
		return scoreboard;
	}

}
