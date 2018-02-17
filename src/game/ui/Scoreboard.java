package game.ui;

import org.newdawn.slick.Graphics;

import game.Game;
import game.SlickObject;
import game.interfaces.Drawable;
import game.interfaces.Updatable;
import game.utils.Text;

public class Scoreboard extends SlickObject implements Drawable, Updatable {

	Text score;

	public Scoreboard(float x, float y) {
		this.x = x;
		this.y = y;
		score = new Text(x, y, "");
		setScore("0");

	}

	public void update() {
		score.x = x;
		score.y = y;

	}

	public void draw(Graphics g) {
		score.draw(g);
	}

	public void setScore(String s) {
		score.setText(s);
		x = Game.SCREEN_WIDTH / 2 - score.getTextWidth() / 2;
	}

}
