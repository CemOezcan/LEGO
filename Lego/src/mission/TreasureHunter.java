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
	private final float[] white = {1,1};
	
	public TreasureHunter(Robot robot) {
		this.robot = robot;
	}
	
	@Override
	public void executeMission() {
		this.robot.beep();

		this.robot.getColorSensor().setRGBMode();
		boolean foundWhite = false;
		boolean foundRed = false;
		boolean leftSide = false;
		robot.adjustMotorspeed(tempo, tempo);

		while (Button.LEFT.isUp() && !(foundRed && foundWhite)) {
			robot.forward();
			float[] actualColorValue = robot.getColorSensor().getColor();
			boolean isTouched = (robot.getPressureSensorLeft().isTouched() || robot.getPressureSensorRight().isTouched());
			if (actualColorValue == red) {
				foundRed = true;
				Sound.beepSequence();
			} else if (actualColorValue == white) {
				foundWhite = true;
				Sound.beepSequence();
			}
			if (isTouched) {
				if (leftSide) {
					turnLeft();
					leftSide = false;
				} else {
					turnRight();
					leftSide = true;
				}
			}
		}
	}
	
	public void turnLeft() {
		this.robot.pilotTravel(-2);
		this.robot.RotateLeft(550);
		this.robot.pilotTravel(3);
		this.robot.RotateLeft(550);
	}
	
	public void turnRight() {
		this.robot.pilotTravel(-2);
		this.robot.RotateRight(550);
		this.robot.pilotTravel(3);
		this.robot.RotateRight(550);
	}

}
