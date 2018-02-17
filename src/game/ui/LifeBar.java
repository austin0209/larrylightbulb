package game.ui;

import org.newdawn.slick.Graphics;

import game.SlickObject;
import game.assets.Assets;
import game.entities.Player;
import game.interfaces.Drawable;
import game.interfaces.Updatable;

public class LifeBar extends SlickObject implements Drawable, Updatable {

	private int heartCount;
	Player larry;

	public LifeBar(float x, float y, Player player) {
		this.x = x;
		this.y = y;
		larry = player;
		heartCount = 4;
	}

	public void draw(Graphics g) {
		g.drawImage(Assets.heartHud, x, y);
		for (int i = 0; i < heartCount; i++) {
			g.drawImage(Assets.heart, x + 66 + 48 * i, y + 11);
		}

	}

	public void update() {
		if (larry.getHealth() >= 100) {
			heartCount = 4;
		} else if (larry.getHealth() >= 75) {
			heartCount = 3;
		} else if (larry.getHealth() >= 50) {
			heartCount = 2;
		} else if (larry.getHealth() <= 25 && larry.getHealth() > 0) {
			heartCount = 1;
		} else if (larry.getHealth() <= 0) {
			heartCount = 0;
		}
	}

}
