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
	private final float OPTIMAL_VALUE = 0.11f;
	
	private boolean foundWhite;
	private boolean foundRed;
	private boolean leftSide;
	
	private ColorSensor colorSensor;
	
	public TreasureHunter(Robot robot) {
		this.robot = robot;
		this.leftSide = true;
		this.foundWhite = false;
		this.foundRed = false;
		this.robot.setTravelSpeed(6);
		this.robot.setRotateSpeed(5);
		this.colorSensor = this.robot.getColorSensor();
		this.colorSensor.setColorIDMode();
	}
	
	@Override
	public boolean executeMission() {
		
		this.robot.beep();
		boolean isTouched;
		robot.forward();

		while (!(this.foundRed && this.foundWhite)) {
			if (!Button.LEFT.isUp()) {
				this.robot.pilotStop();
				return false;
			}
			
			isTouched = (robot.getPressureSensorLeft().isTouched() || robot.getPressureSensorRight().isTouched());
			this.scan();
			if (isTouched) {
				if (this.leftSide) {
					this.turnLeft();
					this.leftSide = false;
				} else {
					turnRight();
					this.leftSide = true;
				}
				this.robot.forward();
			}
		}
		
		this.robot.pilotStop();
		return true;
	}
	
	private void turnLeft() {
		this.robot.pilotTravel(-4);
		for(int i = 0; i < 2; i++) {
			this.robot.RotateLeft(280);
			this.scan();
		}
		
		this.robot.pilotTravel(2);
		this.scan();
		for(int i = 0; i < 2; i++) {
			this.robot.RotateLeft(280);
			this.scan();
		}
	}
	
	private void turnRight() {
		this.robot.pilotTravel(-4);
		for(int i = 0; i < 2; i++) {
			this.robot.RotateRight(280);
			this.scan();
		}
		this.robot.pilotTravel(2);
		this.scan();
		for(int i = 0; i < 2; i++) {
			this.robot.RotateRight(280);
			this.scan();
		}
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
		
		boolean isTouched = (robot.getPressureSensorLeft().isTouched() || robot.getPressureSensorRight().isTouched());
		this.robot.forward();
		
		while (!isTouched) {
			isTouched = (robot.getPressureSensorLeft().isTouched() || robot.getPressureSensorRight().isTouched());
		}
		
		this.robot.pilotTravel(-3);
		this.robot.RotateRight(550);
		
		float actualSonicValue;
		RegulatorP regulator = new RegulatorP(this.robot, this.SPEED, this.KP, this.OPTIMAL_VALUE);
		
		while (!foundWhite) {
			isTouched = (robot.getPressureSensorLeft().isTouched() || robot.getPressureSensorRight().isTouched());
			if (isTouched) {
				this.robot.pilotTravel(-3);
				this.robot.RotateRight(550);
			}
			
			if (this.colorSensor.getColor()[0] == this.BLUE) {
				this.executeMission();
			}
			
			actualSonicValue = this.robot.getUltraSonicSensor().getDistance();
			regulator.sonicRegulate(actualSonicValue);
			this.scan();
			
		}
	
	}	

}
