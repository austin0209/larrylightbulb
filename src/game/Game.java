package game;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import game.assets.Assets;
import game.assets.Playfield;
import game.assets.SplashScreen;
import game.ui.MainMenu;
import game.ui.PauseMenu;
import game.utils.Controller;
import game.utils.LevelManager;
import game.utils.Rectangle;

/**
 * The main class of the game.
 * 
 * @author Wagner SET
 *
 */

public class Game extends BasicGame {
	static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	public static final int SCREEN_WIDTH = 1280;
	public static final int SCREEN_HEIGHT = 720;
	public static Controller controller;
	public static Input input;
	public static Playfield currentLevel;
	public static Playfield level;
	// public static Tutorial tutorial;
	private boolean isFullscreen = false;
	private MainMenu mainMenu;
	public static Rectangle cursor;
	SplashScreen splashScreen;
	PauseMenu pauseMenu;
	public static boolean exit;

	public static enum GameState {
		MAINMENU, INGAME, PAUSED, SPLASHSCREEN, TUTORIAL
	}

	public static GameState state;

	/**
	 * Sets the title of the game.
	 * 
	 * @param title
	 *            The title of the game.
	 */

	public Game(String title) {
		super(title);
	}

	/**
	 * Slick2D's implementation of drawing to the screen. Draws menus, splash
	 * screens, or levels based on the game state.
	 * 
	 * @param g
	 *            Slick's graphics object.
	 * @param gc
	 *            Slick's Game Container object.
	 */
	public void render(GameContainer gc, Graphics g) throws SlickException {
		if (state != GameState.TUTORIAL) {
			g.setColor(Color.pink);
			g.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
		}
		if (state == GameState.MAINMENU) {
			mainMenu.draw(g);
		} else if (state == GameState.INGAME || state == GameState.TUTORIAL) {
			if (state == GameState.TUTORIAL) {
				Assets.tutorialBackground.draw();
			}
			currentLevel.draw(g);
		} else if (state == GameState.SPLASHSCREEN) {
			splashScreen.draw(g);
		} else if (state == GameState.PAUSED) {
			currentLevel.draw(g);
			pauseMenu.draw(g);
		}

	}

	/**
	 * Slick2D's init method. The first thing that runs in the game. Gets the input
	 * of the Game Container, initializes Controller object, menus, and starts the
	 * splash screen.
	 * 
	 * @param gc
	 *            Slick's Game Container Object.
	 */
	public void init(GameContainer gc) throws SlickException {
		input = gc.getInput();
		controller = new Controller();
		cursor = new Rectangle(0, 0, 1, 1);
		state = GameState.SPLASHSCREEN;
		Assets.loadAssets();
		try {
			LevelManager.loadSegments();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		mainMenu = new MainMenu(575, 439, cursor);
		splashScreen = new SplashScreen();
		pauseMenu = new PauseMenu();
		level = new Playfield();
		level.createStage();
		/*
		 * tutorial = new Tutorial(); try { /tutorial.createTutorialLevel(); } catch
		 * (IOException e) { // TODO Auto-generated catch block e.printStackTrace(); }
		 */
	}

	/**
	 * Slick2D's update method. Updates various objects depending on the game state.
	 * 
	 * @param gc
	 *            Slick's GameContainer object.
	 * @param arg1
	 */
	public void update(GameContainer gc, int arg1) throws SlickException {
		cursor.setLocation(input.getMouseX(), input.getMouseY());
		if (state == GameState.MAINMENU)
			mainMenu.update();
		else if (state == GameState.INGAME || state == GameState.TUTORIAL)
			currentLevel.update();
		else if (state == GameState.SPLASHSCREEN)
			splashScreen.update();
		else if (state == GameState.PAUSED)
			pauseMenu.update();
		if (exit) {
			gc.exit();
		}
		setFullscreen(gc);

	}

	/**
	 * Checks if player has toggled fullscreen, and toggles fullscreen if so.
	 * 
	 * @param gc
	 *            Slick's Game Container object.
	 * @throws SlickException
	 */
	public void setFullscreen(GameContainer gc) throws SlickException {
		if (input.isKeyDown(Input.KEY_F) && isFullscreen == false) {
			gc.setFullscreen(true);
			isFullscreen = true;
			System.out.println("fullscreen on");
		}
		if (input.isKeyDown(Input.KEY_F) && isFullscreen == true) {
			gc.setFullscreen(false);
			isFullscreen = false;
			System.out.println("fullscreen off");
		}
	}

	/**
	 * Initializes the GameContainer.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			AppGameContainer appgc;
			appgc = new AppGameContainer(new Game("Larry the Lightbulb: The Green Mission"));
			appgc.setDisplayMode(SCREEN_WIDTH, SCREEN_HEIGHT, false);
			appgc.setTargetFrameRate(59);
			appgc.setShowFPS(false);
			// appgc.setFullscreen(true);
			appgc.start();
		} catch (SlickException ex) {
			Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

}
