package game;

/**
 * The superclass of the game. Provides a coordinate system.
 * 
 * @author Wagner SET
 *
 */

public abstract class SlickObject {
	public float x, y;

	/**
	 * Sets the location of the object.
	 * 
	 * @param x
	 *            The x coordinate to move to.
	 * @param y
	 *            The y coordinate to move to.
	 */
	public void setLocation(float x, float y) {
		this.x = x;
		this.y = y;
	}

}
