package game.assets;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Sprite {
	
	private Image image;
	private String path = "/res/";
	
	public Sprite(String name) {
		path += name + ".png";
		try {
			image = new Image(path);
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setPath(String path) {
		this.path = path;
		try {
			image = new Image(path);
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("COULD NOT SET IMAGE");
		}
	}
	
	public String getPath() {
		return path;
	}

	public Image getImage() {
		return image;
		
	}
	
	
	
	
	
}
