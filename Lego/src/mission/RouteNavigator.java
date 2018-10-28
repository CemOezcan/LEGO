package mission;

import lejos.hardware.lcd.LCD;
import robot.Robot;

public class RouteNavigator implements Mission {

	private final Robot robot;
	
	public RouteNavigator(Robot robot) {
		this.robot = robot;
	}
	
	@Override
	public void executeMission() {
			LCD.drawString("Route Navigator", 10, 10);
	}
}
