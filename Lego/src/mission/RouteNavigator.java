package mission;

import lejos.hardware.lcd.LCD;
import robot.Robot;
import lejos.hardware.Button;
import lejos.hardware.Sound;

public class RouteNavigator implements Mission {

	private final Robot robot;
	
	//TODO: color values
	private final float BLACK = 0.0f;
	private final float WHITE = 48.0f;
	private final float BLUE = 25.0f;
	private final float OFFSET = 10f; 

	public RouteNavigator(Robot robot) {
		this.robot = robot;
	}

	@Override
	public void executeMission() {
		Sound.beep();
		
		robot.getColorSensor().setColorIDMode();
		boolean end = false;
		int degree = 0;
		while (Button.LEFT.isUp() && !end) {
			if (robot.getColorSensor().getColor()[0] == BLUE) {
				end = true;
			} else if (robot.getPressureSensorLeft().isTouched() || robot.getPressureSensorRight().isTouched()) {
				this.driveAroundObstacle();
			} else if (robot.getColorSensor().getColor()[0] > BLACK + OFFSET) {
				robot.RotateLeft(10);
				degree += 10;
				if (degree > 200) { //90 for 90 degreeö lklöö 09.
					robot.RotateRight(100);
					this.findLineAfterGap();
				}
			} else if (robot.getColorSensor().getColor()[0] < WHITE - OFFSET ) {
				robot.RotateRight(10);
				degree = 0;
			}
		}
		Sound.beep();
	}
	
	public void driveAroundObstacle() {
		Sound.beepSequence();
	}
	
	public void findLineAfterGap() {
		
	}
}
