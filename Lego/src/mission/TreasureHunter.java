package mission;

import lejos.hardware.Button;
import lejos.hardware.Sound;
import lejos.hardware.lcd.LCD;
import lejos.utility.Delay;
import robot.RegulatorP;
import robot.Robot;
import sensor.ColorSensor;

public class TreasureHunter implements Mission {

	private final Robot robot;
	
	private final float tempo = 150f;
	private final float RED = 0;
	private final float WHITE = 6;
	
	private boolean foundWhite = false;
	private boolean foundRed = false;
	
	private ColorSensor colorSensor;
	
	public TreasureHunter(Robot robot) {
		this.robot = robot;
		colorSensor = this.robot.getColorSensor();
	}
	
	@Override
	public void executeMission() {
		
		this.robot.beep();
		this.colorSensor.setColorIDMode();
		boolean leftSide = true;
		
		boolean isTouched;
		
		robot.forward();

		while (Button.LEFT.isUp() && !(foundRed && foundWhite)) {
			isTouched = (robot.getPressureSensorLeft().isTouched() || robot.getPressureSensorRight().isTouched());
			this.scan();
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
		this.scan();
		this.robot.pilotTravel(3);
		this.scan();
		this.robot.RotateLeft(550);
		this.scan();
	}
	
	private void turnRight() {
		this.robot.pilotTravel(-2);
		this.robot.RotateRight(550);
		this.scan();
		this.robot.pilotTravel(3);
		this.scan();
		this.robot.RotateRight(550);
		this.scan();
	}
	
	private void scan() {
		float value = this.colorSensor.getColor()[0];
		if (value == this.WHITE) {
			this.foundWhite = true;
		} else if (value == this.RED) {
			this.foundRed = true;
			this.robot.beepSequence();
		}
	}

}
