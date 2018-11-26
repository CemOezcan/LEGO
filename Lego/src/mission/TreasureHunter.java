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
	
	private boolean foundWhite = false;
	private boolean foundRed = false;
	boolean leftSide;
	
	private ColorSensor colorSensor;
	
	public TreasureHunter(Robot robot) {
		this.robot = robot;
		this.leftSide = true;
		this.robot.setTravelSpeed(5);
		this.robot.setRotateSpeed(5);
		colorSensor = this.robot.getColorSensor();
	}
	
	@Override
	public boolean executeMission() {
		
		this.robot.beep();
		colorSensor = this.robot.getColorSensor();
		this.colorSensor.setColorIDMode();
		this.foundRed = false;
		this.foundWhite = false;
		boolean isTouched;
		
		robot.forward();

		while (!(foundRed && foundWhite)) {
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
		this.robot.pilotTravel(-3);
		for(int i = 0; i < 2; i++) {
			this.robot.RotateLeft(280);
			this.scan();
		}
		
		this.robot.pilotTravel(2.5);
		this.scan();
		for(int i = 0; i < 2; i++) {
			this.robot.RotateLeft(280);
			this.scan();
		}
	}
	
	private void turnRight() {
		this.robot.pilotTravel(-3);
		for(int i = 0; i < 2; i++) {
			this.robot.RotateRight(280);
			this.scan();
		}
		this.robot.pilotTravel(2.5);
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
		} else if (value == this.RED) {
			this.foundRed = true;
			if (!this.foundWhite) {
				this.findWhite();
			}
			this.robot.beepSequence();
		}
	}
	
	private void findWhite() {
		if (!this.leftSide) {
			this.robot.RotateRight(1050);
		}
		boolean isTouched = (robot.getPressureSensorLeft().isTouched() || robot.getPressureSensorRight().isTouched());
		this.robot.forward();
		while (!isTouched) {
			isTouched = (robot.getPressureSensorLeft().isTouched() || robot.getPressureSensorRight().isTouched());
		}
		
		this.robot.pilotTravel(-1);
		this.robot.RotateLeft(1650);
		
		float kpSonic = 1500;
		float actualSonicValue = this.robot.getUltraSonicSensor().getDistance();
		float optimalValue = 0.05f;
		RegulatorP regulator = new RegulatorP(this.robot, 150f, kpSonic, optimalValue);
		isTouched = (robot.getPressureSensorLeft().isTouched() || robot.getPressureSensorRight().isTouched());
		
		while (!isTouched) {
			actualSonicValue = this.robot.getUltraSonicSensor().getDistance();
			regulator.sonicRegulateBackward(actualSonicValue);
			if (this.robot.getColorSensor().getColor()[0] == this.WHITE) {
				this.foundWhite = true;
				break;
			}
		}
	
	}	

}
