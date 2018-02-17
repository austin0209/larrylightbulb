package game.assets;

import java.util.ArrayList;

import org.newdawn.slick.Graphics;

import game.Game;
import game.Game.GameState;
import game.entities.Component;
import game.entities.Entity;
import game.entities.FogOfPollution;
import game.entities.KineticEntity;
import game.entities.Platform;
import game.entities.Player;
import game.entities.SolarPanel;
import game.interfaces.Drawable;
import game.interfaces.Updatable;
import game.ui.HUD;
import game.ui.Inventory;
import game.ui.LifeBar;
import game.ui.Scoreboard;
import game.utils.Camera;
import game.utils.Controller;
import game.utils.LevelManager;

/**
 * The level the player plays on.
 * 
 * @author Wagner SET
 *
 */

public class Playfield implements Drawable, Updatable {
	ArrayList<Segment> segments;
	public static ArrayList<Entity> entities;
	public static ArrayList<Entity> garbage;
	public static int levelWidth;
	Player larry;
	Camera cam;
	FogOfPollution fog;
	public static HUD hud;

	/**
	 * Initializes the camera, entities list, garbage list, player, HUD, and fog.
	 */
	public Playfield() {
		cam = new Camera();
		entities = new ArrayList<Entity>();
		garbage = new ArrayList<Entity>();
		larry = new Player(0, 0);
		fog = new FogOfPollution(-50000 - 48 * 5, -1);
		hud = new HUD(new Inventory(0, 10, larry), new LifeBar(Game.SCREEN_WIDTH - 252, 34, larry),
				new Scoreboard(Game.SCREEN_WIDTH / 2, 44));
	}

	/**
	 * Updates the player, hud, camera, and all entities. Also checks if player has
	 * paused game.
	 */
	public void update() {
		larry.update();
		fog.update(larry);
		hud.update();
		cam.update(larry.getBoundingBox().getLeft(), 0);
		pause();

		// Updates all entities.
		for (int i = entities.size() - 1; i >= 0; i--) {
			if (entities.get(i) instanceof KineticEntity) {
				// Removes all entities that are supposed to be dead, and puts them in the
				// garbage.
				if (!((KineticEntity) entities.get(i)).isAlive()) {
					garbage.add(entities.get(i));
					entities.remove(entities.get(i));
				} else {
					((KineticEntity) entities.get(i)).update();
				}
			}
			if (entities.get(i) instanceof SolarPanel) {
				((SolarPanel) entities.get(i)).update();
			}

			if (entities.get(i) instanceof Component) {
				((Component) entities.get(i)).update();
			}
		}

		// Resets the game when larry falls of stage.
		if (larry.y > Game.SCREEN_HEIGHT * 2) {
			if (Game.state == Game.GameState.INGAME)
				createStage();
			else if (Game.state == Game.GameState.TUTORIAL) {
				larry.reset();
			}
		}

		// Resets the game when larry walks off the right edge of the screen.
		if (larry.x > levelWidth) {
			if (Game.state == Game.GameState.INGAME)
				createStage();
			else if (Game.state == Game.GameState.TUTORIAL) {
				larry.reset();
				fog.reset();
			}
		}
	}

	/**
	 * Draws all entities including larry, fog, and HUD.
	 */
	public void draw(Graphics g) {
		for (Entity e : entities) {
			e.draw(g);
		}
		larry.draw(g);
		fog.draw(g);
		hud.draw(g);
	}

	/**
	 * Creates a new level.
	 * 
	 */
	public void createStage() {
		levelWidth = 0;
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
		// Gets the Array List of entities from the LevelManager class.
		entities = LevelManager.createLevel((byte) 0);
		fog.reset();
		// Adds all required platforms to the level.
		for (int i = 0; i < Math.ceil((float) levelWidth / Platform.width); i++) {
			entities.add(new Platform(i * Platform.width, Game.SCREEN_HEIGHT - Platform.height));
		}
	}

	/**
	 * Checks if player has paused the game.
	 */
	protected void pause() {
		if (Game.controller.hasPressed(Controller.Button.PAUSE) && Game.state != GameState.PAUSED) {
			if (Game.state == GameState.INGAME) {
				Game.state = Game.GameState.PAUSED;
				larry.getAnimation().stop();
				larry.setVisiblity(true);
				Assets.walkerWalk.stop();
			} else if (Game.state == GameState.TUTORIAL) {
				Game.state = Game.GameState.MAINMENU;
			}
		}
	}

	/**
	 * 
	 * @return The current HUD of the level.
	 */
	public HUD getHUD() {
		return hud;
	}

	/**
	 * 
	 * @return The player.
	 */
	public Player getPlayer() {
		return larry;
	}

}
