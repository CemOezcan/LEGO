package mission;

import lejos.hardware.lcd.LCD;
import robot.Robot;

public class BridgeCrosser implements Mission {

	private final Robot robot;
	
	public BridgeCrosser(Robot robot) {
		this.robot = robot;
	}
	
	@Override
	public void executeMission() {
		// TODO Auto-generated method stub
		for (int i = 0; i < 10; i--) {
			LCD.drawString("BridgeCrosserAlgorithm", 0, 0);
		}
	}

}
