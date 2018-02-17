package game.assets;

import java.io.IOException;

import org.newdawn.slick.Image;

import game.Game;
import game.entities.Entity;
import game.entities.Platform;
import game.utils.LevelManager;

public class Tutorial extends Playfield {

	Image background;

	public Tutorial() {
		super();

		try {
			createTutorialLevel();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void createTutorialLevel() throws IOException {
		levelWidth = Game.SCREEN_WIDTH;
		larry.reset();
		// Takes deleted entities out of the garbage
		for (Entity e : garbage) {
			entities.add(e);
		}
		garbage.clear();
		// Resets all entities to original positions.
		for (Entity e : entities) {
			e.reset();
		}
		// Constructs tutorial level in LevelManager class.
		entities = LevelManager.buildTutorial();
		fog.reset();
		// Adds all required platforms to the level.
		for (int i = 0; i < Math.ceil((float) levelWidth / Platform.width); i++) {
			entities.add(new Platform(i * Platform.width, Game.SCREEN_HEIGHT - Platform.height));
		}
	}

}
