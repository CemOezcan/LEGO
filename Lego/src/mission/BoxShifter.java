package mission;

import lejos.hardware.Button;
import lejos.hardware.Sound;
import lejos.hardware.lcd.LCD;
import robot.Robot;

public class BoxShifter implements Mission {

	private final Robot robot;
	
	public BoxShifter(Robot robot) {
		this.robot = robot;
	}
	
	@Override
	public void executeMission() {
		// TODO Auto-generated method stub
		while (Button.ENTER.isUp()) {
			Sound.beep();
			for (int i = 0; i < 10; i--) {
				LCD.drawString("BoxShifterAlgorithm", 0, 0);
			}
		}
		
	}

}
