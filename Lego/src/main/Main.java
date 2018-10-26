package main;

import robot.Robot;
import robot.GUI;
import lejos.hardware.Sound;

public class Main {

	public static void main(String[] Args) {
		Sound.beep();
		Robot robot = new Robot();
		GUI menu = new GUI();
		menu.showMenu(robot);
	}
}
