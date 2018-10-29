package mission;

import lejos.hardware.lcd.LCD;
import lejos.utility.Delay;
import robot.Robot;
import lejos.hardware.Button;
import lejos.hardware.Sound;

public class RouteNavigator implements Mission {

	private final Robot robot;
	
	//TODO: color values
	private final float BLACK = 0.08f;
	private final float WHITE = 0.48f;
	private final float BLUE = 0.0f; //TODO: add value
	private final float OFFSET = 0.1f; 

	public RouteNavigator(Robot robot) {
		this.robot = robot;
	}

	@Override
	public void executeMission() {
		Sound.beep();
		
		robot.getColorSensor().setRedMode();
		
		robot.forward();
		
		boolean nextDirection = true;
		
		while (Button.LEFT.isUp()) {
			
			if (robot.getColorSensor().getColor()[0] < 0.3) {
				nextDirection = this.align(nextDirection, 100);
			}
		}
		robot.stop();
	}
	
	public void driveAroundObstacle() {
		Sound.beepSequence();
	}
	
	public void findLineAfterGap() {
		Sound.beepSequence();
	}
	
	/**
	 * align the robot and returns the turned direction from the robot
	 * 
	 * @param directionLeft if the last direction was left
	 * @return the last direction
	 */
	public boolean align(boolean directionLeft, int degree) {
		robot.stop();
		boolean newDirection = !directionLeft;
		if (directionLeft) {
			robot.RotateLeft(degree);
		} else {
			robot.RotateRight(degree * 2);
		}
		if (robot.getColorSensor().getColor()[0] > 0.3) {
			robot.forward();
			return newDirection;
		}
		return newDirection;
	} 
		
	
}
