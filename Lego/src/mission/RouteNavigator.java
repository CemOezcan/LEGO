package mission;

import lejos.hardware.lcd.LCD;

public class RouteNavigator implements Mission {

	@Override
	public void executeMission() {
			LCD.drawString("Route Navigator", 10, 10);
	}
}
