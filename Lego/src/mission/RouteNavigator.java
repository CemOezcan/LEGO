package mission;

import robot.Robot;
import lejos.hardware.Button;
import lejos.hardware.Sound;

public class RouteNavigator implements Mission {

	private Robot robot;
	
	private final double BLACK = 0.0;
	private final double WHITE = 0.0;
	private final double BLUE= 0.0;
	
	public RouteNavigator(Robot robot) {
		this.robot = robot;
	}
	
	public void start() {
		Sound.beep();
		
		boolean end = false;
		while (Button.LEFT.isUp() && !end) {
			while(true) { //while
				
			}
			
			
			
			
		}
		
	}
	
	public void driveAroundObstacle() {
		
	}
	
	public void findLineAfterGap() {
		
	}

	
}
