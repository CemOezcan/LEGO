package main;

import robot.Robot;
import mission.TaskMenu;

public class Main {

	public static void main(String[] Args) {
		
		Robot robot = new Robot();
		TaskMenu menu = new TaskMenu();
		menu.showMenu();
	}
}
