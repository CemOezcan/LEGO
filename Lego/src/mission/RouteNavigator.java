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
		int degree = 0;
		while (Button.LEFT.isUp()) {
			
			if (robot.getColorSensor().getColor()[0] < 0.2) {
				this.align();
			}
		}
		robot.stop();
	}
	
	public void driveAroundObstacle() {
		Sound.beepSequence();
	}
	
	public void findLineAfterGap() {
		
	}
	
	public void align() {
		robot.stop();
		
		//TODO: implement align method
		
		robot.forward();
	}
}
