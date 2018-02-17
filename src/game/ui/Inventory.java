package game.ui;

import org.newdawn.slick.Graphics;

import game.SlickObject;
import game.assets.Assets;
import game.entities.Player;
import game.interfaces.Drawable;

public class Inventory extends SlickObject implements Drawable {

	Player larry;

	public Inventory(float x, float y, Player player) {
		this.x = x;
		this.y = y;
		larry = player;
	}

	public void draw(Graphics g) {
		g.drawImage(Assets.componentHud, x, y);
		for (int i = 0; i < larry.getComponents(); i++) {
			g.drawImage(Assets.component, x + 9 + 93 * i, y);
		}
	}
}