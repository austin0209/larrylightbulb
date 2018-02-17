package game.ui;

import game.assets.Assets;
import game.utils.Rectangle;

public class Settings extends Menu {

	static String[] strings = { "SET UP", "SET DOWN", "SET LEFT", "SET RIGHT", "SET JUMP", "SET" };

	public Settings(float x, float y, Rectangle cursor) {
		super(x, y, Assets.settings, cursor, strings);

	}

	public void collision() {

	}

	public void runOption(Option o) {

	}

}
