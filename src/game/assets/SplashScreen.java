package game.assets;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import game.Game;
import game.SlickObject;
import game.interfaces.Drawable;
import game.interfaces.Updatable;
import game.utils.Text;
import game.utils.Timer;

/**
 * An intro screen displaying our names.
 * 
 * @author Wagner SET
 *
 */
public class SplashScreen extends SlickObject implements Drawable, Updatable {

	ArrayList<Text> lines;
	Timer timer;
	static int yOffset = 0;

	/**
	 * Initializes the timer to set the duration of the splash screen, initializes
	 * the ArrayList of lines to be displayed, adds all lines to Array List and
	 * starts the timer.
	 */
	public SplashScreen() {
		timer = new Timer(3000);
		lines = new ArrayList<Text>();
		addLine("");
		addLine("");
		addLine("");
		addLine("");
		addLine("");
		addLine("");
		addLine("");
		addLine("");
		addLine("");
		addLine("   public class Developed_By {");
		addLine("     public static void main(WAGNER SET) {");
		addLine("       person Austin_Aurelio = Lead_Programmer;");
		addLine("       martian Marvin_Valenzuela = Game_Designer;");
		addLine("       person Evan_Barrera = Creative_Director;");
		addLine("     }");
		addLine("   }");
		timer.start();
	}

	/**
	 * Draws the splash screen.
	 * 
	 * @param g
	 *            Slick's graphics object.
	 */
	public void draw(Graphics g) {
		g.setColor(Color.black);
		g.fillRect(0, 0, Game.SCREEN_WIDTH, Game.SCREEN_HEIGHT);
		for (Text t : lines) {
			t.draw(g);
		}
	}

	/**
	 * Updates the timer and checks if it is finished, then switches to Main Menu.
	 */
	public void update() {
		timer.update();
		if (timer.isFinished()) {
			Assets.introMusic.loop();
			Game.state = Game.GameState.MAINMENU;
		}

	}

	/**
	 * Adds a line to the splash screen.
	 * 
	 * @param text
	 *            String of line to add.
	 */
	public void addLine(String text) {
		lines.add(new Text(10, 10 + yOffset, text));
		yOffset += 30;

	}
}
