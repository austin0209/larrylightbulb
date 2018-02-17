package game.assets;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.SpriteSheet;

/**
 * This class loads in and manages all assets such as sprites, animations, and
 * sounds.
 * 
 * @author Wagner SET
 *
 */

public class Assets {

	public static Image[] playerFrames;
	public static SpriteSheet playerSheet;
	public static Image componentHud;
	public static Image heartHud;
	public static Image heart;
	public static Image component;
	public static Image whip;
	public static Image reverseWhip;
	public static Image none;
	public static Image mainMenu;
	public static Image settings;
	public static Animation leftWalk;
	public static Animation rightWalk;
	public static Animation walkerWalk;
	public static SpriteSheet textSheet;
	public static SpriteSheet walkerSheet;
	public static Image[] walkerFrames;
	public static Image[] attackFrames;
	public static SpriteSheet attackSheet;
	public static Animation attackRightAnimation;
	public static Animation attackLeftAnimation;
	public static Image[] playerOnFrames;
	public static Animation leftWalkOn;
	public static Animation rightWalkOn;
	public static SpriteSheet playerOnSheet;
	public static Sound introMusic;
	public static Sound song1;
	public static Image pauseMenu;
	public static Sound explosion;
	public static Sound respawn;
	public static Sound firework;
	public static Sound hit;
	public static Sound jump;
	public static Sound enemyHit;
	public static Sound pickUpComponent;
	public static Image tutorialBackground;

	/**
	 * Initializes all assets.
	 * 
	 * @throws SlickException
	 */

	public static void loadAssets() throws SlickException {
		// Load graphics.
		rightWalk = createAnimation(playerFrames, playerSheet, 96, 120, 200, "/res/playerwalk.png");
		leftWalk = getFlippedAnimation(playerFrames, playerSheet, 96, 120, 200, "/res/playerwalk.png");
		rightWalkOn = createAnimation(playerFrames, playerSheet, 96, 120, 200, "/res/playeronwalk.png");
		leftWalkOn = getFlippedAnimation(playerFrames, playerSheet, 96, 120, 200, "/res/playeronwalk.png");
		componentHud = new Image("/res/componenthud.png");
		heartHud = new Image("/res/hearthud.png");
		heart = new Image("/res/heart.png");
		component = new Image("/res/component.png");
		whip = new Image("/res/whip.png");
		reverseWhip = whip.getFlippedCopy(true, false);
		none = new Image("/res/none.png");
		mainMenu = new Image("/res/mainmenu.png");
		settings = new Image("/res/settings.png");
		walkerWalk = createAnimation(walkerFrames, walkerSheet, 48, 48, 200, "/res/enemyidle.png");
		textSheet = new SpriteSheet(new Image("/res/text.png"), 24, 24);
		attackRightAnimation = createAnimation(attackFrames, attackSheet, 192, 120, 75, "/res/playerattack.png");
		attackLeftAnimation = getFlippedAnimation(attackFrames, attackSheet, 192, 120, 75, "/res/playerattack.png");
		pauseMenu = new Image("/res/Pause.png");
		tutorialBackground = new Image("/res/goal.png");

		// Load sounds.
		introMusic = new Sound("/res/introsong.wav");
		song1 = new Sound("/res/firstsong.wav");
		explosion = new Sound("/res/Explosion.wav");
		firework = new Sound("/res/Firework.wav");
		hit = new Sound("/res/Hit.wav");
		jump = new Sound("/res/Jump.wav");
		respawn = new Sound("/res/Respawn.wav");
		enemyHit = new Sound("/res/EnemyHit.wav");
		pickUpComponent = new Sound("/res/Solar.wav");

	}

	/**
	 * Creates an animation out of an array of frames taken from a sprite sheet.
	 * 
	 * @param frames
	 *            The frames for the animation.
	 * @param spriteSheet
	 *            The sprite sheet to get the frames from.
	 * @param width
	 *            The width of each tile on the sprite sheet.
	 * @param height
	 *            The height of each tile on the sprite sheet.
	 * @param duration
	 *            Time before frame is switched.
	 * @param path
	 *            The path of the sprite sheet file.
	 * @return The finished animation.
	 * @throws SlickException
	 */

	public static Animation createAnimation(Image[] frames, SpriteSheet spriteSheet, int width, int height,
			int duration, String path) throws SlickException {
		spriteSheet = new SpriteSheet(new Image(path), width, height);
		frames = new Image[spriteSheet.getHorizontalCount()];
		for (int i = 0; i < frames.length; i++) {
			frames[i] = spriteSheet.getSprite(i, 0);
		}
		return new Animation(frames, duration, true);
	}

	/**
	 * Creates an animation out of an array of frames taken from a sprite sheet, but
	 * the frames are flipped along the y axis.
	 * 
	 * @param frames
	 *            The frames for the animation.
	 * @param spriteSheet
	 *            The sprite sheet to get the frames from.
	 * @param width
	 *            The width of each tile on the sprite sheet.
	 * @param height
	 *            The height of each tile on the sprite sheet.
	 * @param duration
	 *            Time before frame is switched.
	 * @param path
	 *            The path of the sprite sheet file.
	 * @return The finished animation.
	 * @throws SlickException
	 */

	public static Animation getFlippedAnimation(Image[] frames, SpriteSheet spriteSheet, int width, int height,
			int duration, String path) throws SlickException {
		spriteSheet = new SpriteSheet(new Image(path), width, height);
		frames = new Image[spriteSheet.getHorizontalCount()];
		for (int i = 0; i < frames.length; i++) {
			frames[i] = spriteSheet.getSprite(i, 0).getFlippedCopy(true, false);
		}
		return new Animation(frames, duration, true);
	}
}
