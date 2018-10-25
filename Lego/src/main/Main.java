package main;

import robot.Robot;
import robot.GUI;

public class Main {

	public static void main(String[] Args) {
		
		Robot robot = new Robot();
		GUI menu = new GUI();
		menu.showMenu();
	}
}
