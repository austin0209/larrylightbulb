package game.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import game.Game;
import game.assets.Playfield;
import game.assets.Segment;
import game.entities.Barrier;
import game.entities.Block;
import game.entities.Component;
import game.entities.Entity;
import game.entities.SolarPanel;
import game.entities.Spike;
import game.entities.Walker;

public class LevelManager {

	static ArrayList<Segment> fillerSegments;
	static ArrayList<Segment> componentSegments;
	static int fillersToLoad = 4; // Must always be even.
	static int comSegmentsToLoad = 3; // Must always be fillersToLoad - 1.
	static int offset;
	static Segment endSegment;

	public static void loadSegments() throws IOException {
		fillerSegments = new ArrayList<Segment>();
		Scanner fillerScanner = new Scanner(new File("lvls/fillers.dat"));
		fillerSegments = new ArrayList<Segment>();
		readFile(fillerScanner, fillerSegments);

		Scanner componentScanner = new Scanner(new File("lvls/componentsegments.dat"));
		componentSegments = new ArrayList<Segment>();
		readFile(componentScanner, componentSegments);

		endSegment = new Segment();
		endSegment.addEntity(new SolarPanel(36, 600 - 72));
		endSegment.addEntity(new SolarPanel(332, 600 - 72));
		endSegment.addEntity(new SolarPanel(628, 600 - 72));
		endSegment.setWidth(768 + 48 * 4);
	}

	private static void readFile(Scanner sc, ArrayList<Segment> list) {
		while (sc.hasNextLine()) {
			sc.nextLine();
			Segment next = new Segment();
			int width = sc.nextInt();
			sc.nextLine();
			next.setWidth(width);
			int totBlocks = sc.nextInt();
			sc.nextLine();
			while (totBlocks-- > 0) {
				String type = sc.next();
				switch (type) {
				case "block":
					next.addEntity(new Block(sc.nextInt(), sc.nextInt(), sc.next()));
					sc.nextLine();
					break;
				case "spike":
					next.addEntity(new Spike(sc.nextInt(), sc.nextInt()));
					sc.nextLine();
					break;
				case "walker":
					next.addEntity(new Walker(sc.nextInt(), sc.nextInt(), sc.next()));
					sc.nextLine();
					break;
				case "component":
					next.addEntity(new Component(sc.nextInt(), sc.nextInt(), sc.next()));
					sc.nextLine();
					break;
				case "barrier":
					next.addEntity(new Barrier(sc.nextInt(), sc.nextInt()));
					sc.nextLine();
					break;
				}
			}
			list.add(next);
		}
		sc.close();
	}

	public static ArrayList<Entity> createLevel(byte difficulty) {
		ArrayList<Entity> entities = new ArrayList<Entity>();
		ArrayList<Segment> tempFillers = copyLevelSegments(fillerSegments);
		ArrayList<Segment> tempComponents = copyLevelSegments(componentSegments);
		offset = 0;
		for (int i = 1; i <= fillersToLoad + comSegmentsToLoad; i++) {
			if (i % 2 == 1)
				insertSegmentRandomly(entities, tempFillers, difficulty);
			else
				insertSegmentRandomly(entities, tempComponents, difficulty);
		}
		for (Entity e : endSegment.getEntities()) {
			e.move(e.x + offset, e.y);
			entities.add(e);
		}
		offset += endSegment.getWidth();
		Playfield.levelWidth = offset;
		return entities;
	}

	public static ArrayList<Entity> buildTutorial() throws IOException {
		Scanner sc = new Scanner(new File("lvls/tutorial.dat"));
		ArrayList<Entity> entities = new ArrayList<Entity>();
		ArrayList<Segment> segment = new ArrayList<Segment>();
		System.out.println("SEGMENT" + segment.size());
		readFile(sc, segment);
		for (Entity e : segment.get(0).getEntities()) {
			entities.add(e);
		}
		entities.add(new SolarPanel(Game.SCREEN_WIDTH - 300, 600 - 72));
		return entities;
	}

	private static void insertSegmentRandomly(ArrayList<Entity> entities, ArrayList<Segment> temp, byte difficulty) {
		int random = (int) (Math.random() * (temp.size())) + 10 * difficulty;
		System.out.println(random);
		Segment tempSegment = temp.get(random);
		temp.remove(tempSegment);
		for (Entity e : tempSegment.getEntities()) {
			e.move(e.x + offset, e.y);
			entities.add(e);
		}
		offset += tempSegment.getWidth();

	}

	public static ArrayList<Segment> copyLevelSegments(ArrayList<Segment> list) {
		ArrayList<Segment> segments = new ArrayList<Segment>();
		for (int i = 0; i < list.size(); i++) {
			segments.add(list.get(i));
		}
		return segments;
	}

}
