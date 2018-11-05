package mission;

import lejos.hardware.Button;
import lejos.hardware.Sound;
import lejos.hardware.lcd.LCD;
import lejos.utility.Delay;
import robot.Robot;

public class BoxShifter implements Mission {

	private final Robot robot;
	
	public BoxShifter(Robot robot) {
		this.robot = robot;
	}
	
	@Override
	public void executeMission() {
		// TODO Auto-generated method stub
		Sound.beep();
		Delay.msDelay(1000);
		while (Button.ENTER.isUp()) {
			for (int i = 0; i < 100; i++) {
				LCD.drawString("BoxShifterAlgorithm", 0, 0);
			}
		}
		
	}

}
