package game.ui;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;

import game.Game;
import game.assets.Assets;
import game.entities.FogOfPollution;
import game.utils.Rectangle;

public class MainMenu extends Menu {

	static String[] strings = { "START" };
	Animation currentAnimation;
	FogOfPollution fog;
	int larryX = 0;
	int larryY = Game.SCREEN_HEIGHT / 2 - 65;
	int larrySpeed = 2;

	public MainMenu(float x, float y, Rectangle cursor) {
		super(x, y, Assets.mainMenu, cursor, strings);
		currentAnimation = Assets.rightWalk;
		currentAnimation.start();
		fog = new FogOfPollution(-50000, 0);
		fog.setSpeed(0);

	}

	public void draw(Graphics g) {
		g.drawImage(background, 0, 0);
		currentAnimation.draw(larryX, larryY);
		for (Option o : options) {
			o.draw(g);
		}
		larryX += larrySpeed;
		if (larryX > 561 - 100) {
			currentAnimation = Assets.leftWalkOn;
			larrySpeed *= -1;
		}
		if (larryX < -100) {
			currentAnimation = Assets.rightWalk;
			larrySpeed *= -1;
		}
		fog.manuallyMove(larryX - 200 - 50000);
		fog.draw(g);
	}

	public void runOption(Option o) {
		switch (o.getId()) {
		case "START":
			Game.level.createStage();
			Game.state = Game.GameState.INGAME;
			Game.currentLevel = Game.level;
			Assets.walkerWalk.start();
			Assets.introMusic.stop();
			Assets.song1.loop();
			break;
		case "HOW TO PLAY":
			Game.state = Game.GameState.TUTORIAL;
			// Game.currentLevel = Game.tutorial;
			/*
			 * try { Game.tutorial.createTutorialLevel(); } catch (IOException e) { // TODO
			 * Auto-generated catch block e.printStackTrace(); }
			 */
			Assets.walkerWalk.start();
			break;
		}
	}

}
