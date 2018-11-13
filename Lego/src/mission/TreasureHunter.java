package mission;

import lejos.hardware.Button;
import lejos.hardware.Sound;
import lejos.hardware.lcd.LCD;
import robot.RegulatorP;
import robot.Robot;

public class TreasureHunter implements Mission {

	private final Robot robot;
	
	private final float tempo = 150f;
	private final float[] red = {1,1}; //Points to find
	private final float[] blue = {1,1};
	
	public TreasureHunter(Robot robot) {
		this.robot = robot;
	}
	
	@Override
	public void executeMission() {
		this.robot.beep();

		this.robot.getColorSensor().setRGBMode();
		boolean foundBlue = false;
		boolean foundRed = false;
		robot.adjustMotorspeed(tempo, tempo);

		
		while (Button.LEFT.isUp() && !(foundRed && foundBlue)) {
			robot.forward();
			float[] actualColorValue = robot.getColorSensor().getColor();
			boolean isTouched = (robot.getPressureSensorLeft().isTouched() || robot.getPressureSensorRight().isTouched());
			if (actualColorValue == red) {
				foundRed = true;
				Sound.beepSequence();
			} else if (actualColorValue == blue) {
				foundBlue = true;
				Sound.beepSequence();
			}
			if (isTouched) {
				turn();
			}
		
		}
	}
	
	public void turn() {
		
	}

}
