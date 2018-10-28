package mission;

import lejos.hardware.lcd.LCD;
import robot.Robot;
import lejos.hardware.Button;
import lejos.hardware.Sound;

public class RouteNavigator implements Mission {

	private final Robot robot;
	
	private final double BLACK = 0.0;
	private final double WHITE = 0.0;
	private final double BLUE= 0.0;

	public RouteNavigator(Robot robot) {
		this.robot = robot;
	}

	public void executeMission() {
		Sound.beep();
		
		boolean end = false;
		while (Button.LEFT.isUp() && !end) {
			//algorithm for the line follower
				
		}
		
	}
	
	public void driveAroundObstacle() {
		
	}
	
	public void findLineAfterGap() {
		
	}
}
