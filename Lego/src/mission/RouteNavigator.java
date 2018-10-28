package mission;

import lejos.hardware.lcd.LCD;
import robot.Robot;
import lejos.hardware.Button;
import lejos.hardware.Sound;

public class RouteNavigator implements Mission {

	private final Robot robot;
	
	private final float BLACK = 0.0f;
	private final float WHITE = 0.0f;
	private final float BLUE= 0.0f;

	public RouteNavigator(Robot robot) {
		this.robot = robot;
	}

	@Override
	public void executeMission() {
		Sound.beep();
		
		boolean end = false;
		while (Button.LEFT.isUp() && !end) {
			if (robot.getColorSensor().getColor()[0] == BLUE) {
				end = true;
			} else if (robot.getPressureSensorLeft().equals(null)) { //touchsensordata
				driveAroundObstacle();
			} else if (robot.getColorSensor().getColor()[0] > BLACK) {
				robot.RotateLeft(10);
			} else if (robot.getColorSensor().getColor()[0] < WHITE) {
				robot.RotateRight(10);
			}
		}
		Sound.beep();
		
	}
	
	public void driveAroundObstacle() {
		
	}
	
	public void findLineAfterGap() {
		
	}
}
