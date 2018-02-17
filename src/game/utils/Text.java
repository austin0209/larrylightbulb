package game.utils;

import java.util.ArrayList;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import game.SlickObject;
import game.assets.Assets;
import game.interfaces.Drawable;

public class Text extends SlickObject implements Drawable {

	char[] chars;
	ArrayList<Image> images;

	public Text(float x, float y, String str) {
		this.x = x;
		this.y = y;
		chars = str.toCharArray();
		images = new ArrayList<Image>();
	}

	public void draw(Graphics g) {
		for (int i = 0; i < chars.length; i++) {
			g.drawImage(Assets.textSheet.getSprite(findX(chars[i]), findY(chars[i])), x + 24 * i, y);
		}
	}

	public int findX(char c) {
		return c % Assets.textSheet.getHorizontalCount();
	}

	public int findY(char c) {
		return c / Assets.textSheet.getHorizontalCount();
	}

	public void setText(String newText) {
		chars = newText.toCharArray();
	}

	public int getTextWidth() {
		return 24 * chars.length;
	}

}
