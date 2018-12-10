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
	
	private final float RED = 0;
	private final float WHITE = 6;
	private final float BLUE = 2;
	private final float SPEED = 500;
	private final float KP = 1500;
	private final float OPTIMAL_VALUE = 0.12f;
	
	private boolean foundWhite;
	private boolean foundRed;
	private boolean leftSide;
	
	private ColorSensor colorSensor;
	
	public TreasureHunter(Robot robot) {
		this.robot = robot;
		this.robot.setTravelSpeed(5);
		this.robot.setRotateSpeed(5);
		this.colorSensor = this.robot.getColorSensor();
		this.colorSensor.setColorIDMode();
	}
	
	@Override
	public boolean executeMission() {
		
		this.robot.beep();
		this.leftSide = true;
		this.foundWhite = false;
		this.foundRed = false;
		boolean isTouched;
		this.robot.forward();

		while (!(this.foundRed && this.foundWhite)) {
			if (!Button.LEFT.isUp()) {
				this.robot.pilotStop();
				return false;
			}
			
			isTouched = (robot.getPressureSensorLeft().isTouched() || robot.getPressureSensorRight().isTouched() || (this.colorSensor.getColor()[0] == this.BLUE) );
			this.scan();
			if (isTouched && !(this.foundWhite && this.foundRed)) {
				if (this.leftSide) {
					this.turnLeft();
					this.leftSide = false;
				} else {
					turnRight();
					this.leftSide = true;
				}
			}
		}
		
		this.robot.pilotStop();
		return true;
	}
	
	private void scan() {
		float value = this.colorSensor.getColor()[0];
		if (value == this.WHITE) {
			this.foundWhite = true;
			this.robot.beepSequence();
		} else if ((value == this.RED) && (!this.foundRed)) {
			this.robot.beepSequence();
			this.foundRed = true;
			if (!this.foundWhite) {
				this.findWhite();
			}
		}
	}
	
	private void findWhite() {
		
		if (this.leftSide) {
			this.robot.RotateRight(1050);
		}
		
		boolean isTouched = false;
		this.robot.forward();
		while (!isTouched) {
			isTouched = (robot.getPressureSensorLeft().isTouched() || robot.getPressureSensorRight().isTouched());
		}
		
		this.robot.pilotTravel(-3);
		this.robot.RotateRight(550);
		
		float actualSonicValue;
		RegulatorP regulator = new RegulatorP(this.robot, this.SPEED, this.KP, this.OPTIMAL_VALUE);
		
		while (!this.foundWhite) {
			isTouched = (this.robot.getPressureSensorLeft().isTouched() || this.robot.getPressureSensorRight().isTouched());
			if (isTouched) {
				this.robot.pilotTravel(-3);
				this.robot.RotateRight(550);
			}
			
			actualSonicValue = this.robot.getUltraSonicSensor().getDistance();
			regulator.sonicRegulate(actualSonicValue);
			this.foundWhite = this.colorSensor.getColor()[0] == this.WHITE;
			
		}
	
	}	
	
	private void turnRight() {
		this.robot.pilotTravel(-4);
		for(int i = 0; i < 2; i++) {
			this.robot.RotateRight(275);
			this.scan();
		}
		this.robot.pilotTravel(2.5);
		this.scan();
		for(int i = 0; i < 2; i++) {
			this.robot.RotateRight(275);
			this.scan();
		}
		this.robot.forward();
	}
	
	private void turnLeft() {
		this.robot.pilotTravel(-4);
		for(int i = 0; i < 2; i++) {
			this.robot.RotateLeft(275);
			this.scan();
		}
		
		this.robot.pilotTravel(2.5);
		this.scan();
		for(int i = 0; i < 2; i++) {
			this.robot.RotateLeft(275);
			this.scan();
		}
		this.robot.forward();
	}

}
