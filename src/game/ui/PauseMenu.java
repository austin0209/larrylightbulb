package game.ui;

import org.newdawn.slick.Graphics;

import game.Game;
import game.assets.Assets;
import game.utils.Camera;

public class PauseMenu extends Menu {

	static String[] strings = new String[] { "RESUME", "RESET", "MAIN MENU", "EXIT" };

	public PauseMenu() {
		super(Game.SCREEN_WIDTH / 2 - 250, 120, Assets.pauseMenu, Game.cursor, strings);

	}

	public void draw(Graphics g) {
		g.fillRect(x - 10, y - 10, 500, strings.length * 30 + 10);
		g.drawImage(background, x - 25, y - 25);
		super.draw(g);
	}

	public void runOption(Option o) {
		switch (o.getId()) {
		case "RESUME":
			/*
			 * if (Game.state == Game.GameState.TUTORIAL) { try {
			 * Game.tutorial.createTutorialLevel(); Game.state = Game.GameState.INGAME; }
			 * catch (IOException e) { // TODO Auto-generated catch block
			 * e.printStackTrace(); } }
			 */
			Game.state = Game.GameState.INGAME;
			Game.level.getPlayer().getAnimation().start();
			Assets.walkerWalk.start();

			break;
		case "RESET":
			if (Game.state == Game.GameState.TUTORIAL) {
				/*
				 * try { Game.tutorial.createTutorialLevel(); } catch (IOException e) { // TODO
				 * Auto-generated catch block e.printStackTrace(); }
				 */
			} else {
				Game.level.createStage();
				Game.state = Game.GameState.INGAME;
				Assets.walkerWalk.start();
			}
			break;
		case "MAIN MENU":
			Game.state = Game.GameState.MAINMENU;
			Assets.rightWalk.start();
			Assets.leftWalk.start();
			Camera.xOffset = 0;
			Assets.song1.stop();
			Assets.introMusic.loop();
			break;
		case "EXIT":
			Game.exit = true;
		}

	}

}
