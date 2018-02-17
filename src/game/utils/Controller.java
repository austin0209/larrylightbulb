package game.utils;

import java.util.ArrayList;

import org.newdawn.slick.Input;

import game.Game;

public class Controller {

	ArrayList<Integer> controls;

	public static enum Button {
		UP, DOWN, LEFT, RIGHT, ATTACK, JUMP, PAUSE
	}

	public Controller() {
		controls = new ArrayList<Integer>();
		controls.add(Input.KEY_W); // Default up key 0
		controls.add(Input.KEY_S); // Default down key 1
		controls.add(Input.KEY_A); // Default left key 2
		controls.add(Input.KEY_D); // Default right key 3
		controls.add(Input.KEY_LSHIFT); // Default attack key 4
		controls.add(Input.KEY_SPACE); // Default jump key 5
		controls.add(Input.KEY_ESCAPE); // Default pause key 6
		controls.add(Input.KEY_UP); // Alternative up key 7
		controls.add(Input.KEY_DOWN); // Alternative down key 8
		controls.add(Input.KEY_LEFT); // Alternative left key 9
		controls.add(Input.KEY_RIGHT); // Alternative right key 10
	}

	public boolean isPressing(Button button) {
		switch (button) {
		case UP:
			return Game.input.isKeyDown(controls.get(0)) || Game.input.isKeyDown(controls.get(7))
					|| Game.input.isControllerUp(Input.ANY_CONTROLLER);
		case DOWN:
			return Game.input.isKeyDown(controls.get(1)) || Game.input.isKeyDown(controls.get(8))
					|| Game.input.isControllerDown(Input.ANY_CONTROLLER);
		case LEFT:
			return Game.input.isKeyDown(controls.get(2)) || Game.input.isKeyDown(controls.get(9))
					|| Game.input.isControllerLeft(Input.ANY_CONTROLLER);
		case RIGHT:
			return Game.input.isKeyDown(controls.get(3)) || Game.input.isKeyDown(controls.get(10))
					|| Game.input.isControllerRight(Input.ANY_CONTROLLER);
		case ATTACK:
			return Game.input.isKeyDown(controls.get(4)) || Game.input.isButtonPressed(1, Input.ANY_CONTROLLER);
		case JUMP:
			return Game.input.isKeyDown(controls.get(5)) || Game.input.isButtonPressed(0, Input.ANY_CONTROLLER);
		case PAUSE:
			return Game.input.isKeyDown(controls.get(6)) || Game.input.isButtonPressed(2, Input.ANY_CONTROLLER);
		}
		return false;
	}

	// NEED TO FIX THIS, STOMP DOES NOT WORK WITH IT
	public boolean hasPressed(Button button) {
		switch (button) {
		case UP:
			return Game.input.isKeyPressed(controls.get(0)) || Game.input.isKeyPressed(controls.get(7))
					|| Game.input.isControllerUp(Input.ANY_CONTROLLER);
		case DOWN:
			return Game.input.isKeyPressed(controls.get(1)) || Game.input.isKeyPressed(controls.get(8))
					|| Game.input.isControllerDown(Input.ANY_CONTROLLER);
		case LEFT:
			return Game.input.isKeyPressed(controls.get(2)) || Game.input.isKeyPressed(controls.get(9))
					|| Game.input.isControllerLeft(Input.ANY_CONTROLLER);
		case RIGHT:
			return Game.input.isKeyPressed(controls.get(3)) || Game.input.isKeyPressed(controls.get(10))
					|| Game.input.isControllerRight(Input.ANY_CONTROLLER);
		case ATTACK:
			return Game.input.isKeyPressed(controls.get(4)) || Game.input.isButtonPressed(1, Input.ANY_CONTROLLER);
		case JUMP:
			return Game.input.isKeyPressed(controls.get(5)) || Game.input.isButtonPressed(0, Input.ANY_CONTROLLER);
		case PAUSE:
			return Game.input.isKeyPressed(controls.get(6)) || Game.input.isButtonPressed(2, Input.ANY_CONTROLLER);
		}
		return false;
	}

	public void remapKey(Button buttonToRemap, int newButton) {
		switch (buttonToRemap) {
		case UP:
			controls.set(0, newButton);
			break;
		case DOWN:
			controls.set(1, newButton);
			break;
		case LEFT:
			controls.set(2, newButton);
			break;
		case RIGHT:
			controls.set(3, newButton);
			break;
		case ATTACK:
			controls.set(4, newButton);
			break;
		case JUMP:
			controls.set(5, newButton);
			break;
		case PAUSE:
			controls.set(6, newButton);
			break;
		}
	}

}
