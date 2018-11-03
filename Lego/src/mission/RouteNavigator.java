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
	private final float OFFSET = 0.01f; 
	private final float OPTIMALVALUE = (WHITE + BLACK) / 2;
	
	private final float tempo = 220f;
	
	private final float kp = (tempo / WHITE - OPTIMALVALUE);

	public RouteNavigator(Robot robot) {
		this.robot = robot;
	}

	@Override
	public void executeMission() {
		Sound.beep();
		
		robot.getColorSensor().setRedMode();
		
		robot.forward();
		
		boolean nextDirection = true;
		boolean end = false;
		
		float lastDifference = 0f;
		
		while (Button.LEFT.isUp() && !end) {
			
			float leftMotorSpeed = 0;
			float rightMotorSpeed = 0;
			
			float actualValue = robot.getColorSensor().getColor()[0];
			
			//the touchsensors are touched and the robot has to drive around the obstacle
			if (robot.getPressureSensorLeft().isTouched() || robot.getPressureSensorRight().isTouched()) {
				driveAroundObstacle();
				
			} else if (actualValue > WHITE - 2 * OFFSET) { //90 degree turn
				
			} else if (actualValue < BLACK + OFFSET) { //LineGap
				findLineAfterGap();
			} else { //normal case
				lastDifference = actualValue - OPTIMALVALUE;
				float y = kp * lastDifference;
				
				leftMotorSpeed = tempo - y;;
				rightMotorSpeed = tempo + y;
				
				//RobotMotorGEschwindigkeit anpassen mit den übergebenen Werten left right Motor speed
				//robot.setSpeed(leftMotorSpeed, rightMotorSpeed);
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
