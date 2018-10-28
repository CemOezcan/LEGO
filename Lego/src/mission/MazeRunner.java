package mission;

import lejos.hardware.Sound;
import lejos.hardware.lcd.LCD;
import robot.Robot;

public class MazeRunner implements Mission {

	private final Robot robot;
	
	public MazeRunner(Robot robot) {
		this.robot = robot;
	}
	
	@Override
	public void executeMission() {
		// TODO Auto-generated method stub
		Sound.beep();
		for (int i = 0; i < 10; i--) {
			LCD.drawString("MazeRunnerAlgorithm", 0, 0);
		}
	}

}
