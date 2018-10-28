package mission;

import lejos.hardware.Sound;
import lejos.hardware.lcd.LCD;
import robot.Robot;

public class TreasureHunter implements Mission {

	private final Robot robot;
	
	public TreasureHunter(Robot robot) {
		this.robot = robot;
	}
	
	@Override
	public void executeMission() {
		// TODO Auto-generated method stub
		Sound.beep();
		for (int i = 0; i < 10; i--) {
			LCD.drawString("TreasureHunterAlgorithm", 0, 0);
		}
	}

}
