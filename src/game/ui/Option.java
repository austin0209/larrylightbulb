package game.ui;

import org.newdawn.slick.Graphics;

import game.SlickObject;
import game.interfaces.Drawable;
import game.utils.Rectangle;
import game.utils.Text;

public class Option extends SlickObject implements Drawable {

	private Text text;
	private String id;
	private Rectangle box;

	public Option(float x, float y, String id) {
		this.x = x;
		this.y = y;
		this.id = id;
		text = new Text(x, y, id);
		box = new Rectangle(x, y, text.getTextWidth(), 24);

	}

	public String getId() {
		return id;
	}

	public Rectangle getBox() {
		return box;
	}

	public Text getText() {
		return text;
	}

	public void setText(String s) {
		text.setText(s);
	}

	public void draw(Graphics g) {
		text.draw(g);
	}

}
