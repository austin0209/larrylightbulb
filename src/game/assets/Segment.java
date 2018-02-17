package game.assets;

import java.util.ArrayList;

import game.entities.Entity;

/**
 * A segment that is put together randomly to form a level.
 * 
 * @author Wagner SET
 *
 */

public class Segment {

	ArrayList<Entity> allEntities;
	int width;

	/**
	 * Initializes the Array List containing all entities.
	 */
	public Segment() {
		allEntities = new ArrayList<Entity>();
	}

	/**
	 * Adds an entity to the segment.
	 * 
	 * @param e
	 *            Entity to add.
	 */
	public void addEntity(Entity e) {
		allEntities.add(e);
	}

	/**
	 * 
	 * @return ArrayList of all entities in the segment.
	 */
	public ArrayList<Entity> getEntities() {
		return allEntities;
	}

	/**
	 * Sets the width of the level.
	 * 
	 * @param w
	 *            Integer representing the width of the level.
	 */
	public void setWidth(int w) {
		width = w;
	}

	/**
	 * 
	 * @return The width of the level.
	 */
	public int getWidth() {
		return width;
	}

}
