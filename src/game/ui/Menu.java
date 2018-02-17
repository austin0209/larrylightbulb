package game.ui;

import java.util.ArrayList;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;

import game.Game;
import game.SlickObject;
import game.interfaces.Drawable;
import game.utils.Rectangle;

public abstract class Menu extends SlickObject implements Drawable {

	ArrayList<Option> options;
	Rectangle cursor;
	String[] strings;
	Image background;

	public Menu(float x, float y, Image image, Rectangle cursor, String[] strings) {
		options = new ArrayList<Option>();
		this.x = x;
		this.y = y;
		this.strings = strings;
		background = image;
		createOptions();
		this.cursor = cursor;
	}

	public void createOptions() {
		options = new ArrayList<Option>();
		for (int i = 0; i < strings.length; i++) {
			options.add(new Option(x, y + 30 * i, strings[i]));
		}

	}

	public void update() {
		collision();
	}

	public void draw(Graphics g) {
		for (Option o : options) {
			o.draw(g);
		}
	}

	public void collision() {
		for (Option o : options) {
			if (cursor.intersects(o.getBox()) && Game.input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
				runOption(o);
			}
		}
	}

	public abstract void runOption(Option o);
}
