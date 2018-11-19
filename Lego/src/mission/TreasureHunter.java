package mission;

import lejos.hardware.Button;
import lejos.hardware.Sound;
import lejos.hardware.lcd.LCD;
import lejos.utility.Delay;
import robot.RegulatorP;
import robot.Robot;

public class TreasureHunter implements Mission {

	private final Robot robot;
	
	private final float tempo = 150f;
	private final float RED = 0;
	private final float WHITE = 6;
	
	public TreasureHunter(Robot robot) {
		this.robot = robot;
	}
	
	@Override
	public void executeMission() {
		
		this.robot.beep();
		this.robot.getColorSensor().setColorIDMode();
		boolean foundWhite = false;
		boolean foundRed = false;
		boolean leftSide = true;
		
		float actualColorValue = robot.getColorSensor().getColor()[0];
		boolean isTouched = (robot.getPressureSensorLeft().isTouched() && robot.getPressureSensorRight().isTouched());
		
		robot.forward();

		while (Button.LEFT.isUp() && !(foundRed && foundWhite)) {
			actualColorValue = robot.getColorSensor().getColor()[0];
			isTouched = (robot.getPressureSensorLeft().isTouched() && robot.getPressureSensorRight().isTouched());
			if (actualColorValue == this.RED) {
				foundRed = true;
				Sound.beepSequence();
			} else if (actualColorValue == this.WHITE) {
				foundWhite = true;
				break;
			}
			if (isTouched) {
				if (leftSide) {
					this.turnLeft();
					leftSide = false;
				} else {
					turnRight();
					leftSide = true;
				}
				this.robot.forward();
			}
		}
		this.robot.pilotStop();
	}
	
	private void turnLeft() {
		this.robot.pilotTravel(-2);
		this.robot.RotateLeft(550);
		this.robot.pilotTravel(3);
		this.robot.RotateLeft(550);
	}
	
	private void turnRight() {
		this.robot.pilotTravel(-2);
		this.robot.RotateRight(550);
		this.robot.pilotTravel(3);
		this.robot.RotateRight(550);
	}

}
