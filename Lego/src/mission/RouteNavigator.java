package mission;

import robot.RegulatorP;
import robot.Robot;
import sensor.ColorSensor;
import sensor.Sensor;
import lejos.hardware.Button;
import lejos.hardware.motor.Motor;
import lejos.utility.Delay;

import java.util.Timer;
import java.util.TimerTask;

public class RouteNavigator implements Mission {

	private final Robot robot;

	/*
	 * the final color values of the colors
	 */
	private final float BLACK = 0.08f;
	private final float WHITE = 0.48f;
	private final float OFFSET = 0.01f;
	private final float OPTIMALVALUE = (WHITE + BLACK) / 2;/*
	private int state = 0;
	private int ctr = 0;
	private boolean next = false;
	private final boolean[] findRight = {true, true, false, true, true};
	private final float[] speed = {350, 250, 350, 250, 350};
	private final float[] kpValue = {2100, 1950, 2100, 1950, 2100};*/


	private float tempo = 350f; // 250

	/*
	 * the constant for the p-controller 1200
	 */
	private float kp = 2100; //1950

	private boolean afterBox = false;
	private boolean nextMission = false;
	private boolean complete = false;
	private int secondGap = 0;

	/*
	 * constructs a new route navigator
	 */
	public RouteNavigator(Robot robot) {
		this.robot = robot;
	}

	@Override
	public boolean executeMission() {
		this.robot.beep();

		this.robot.getColorSensor().setRedMode();
		RegulatorP regulator = new RegulatorP(this.robot, this.tempo, this.kp, this.OPTIMALVALUE);

		boolean end = false;
		boolean findLine = false;
		robot.adjustMotorspeed(tempo, tempo);

		while (Button.LEFT.isUp() && !end) {
			float actualColorValue = robot.getColorSensor().getColor()[0];

			// the touchsensors are touched and the robot has to drive around
			// the obstacle
			if (this.robot.getPressureSensorLeft().isTouched() || this.robot.getPressureSensorRight().isTouched()) {
				robot.pilotStop();
				robot.motorsStop();
				driveAroundObstacle();
				afterBox = true;

			} else if (actualColorValue < BLACK + 0.005f) { // find line
				findLine();
				findLine = true;
				if (this.nextMission) {
					end = true;
					break;
				}

			} else { // normal case calculate the new speeds for both motors
				if (findLine) {
					regulator.setKpValue(900);
					regulator.regulate(actualColorValue);
					regulator.setKpValue(kp);
					findLine = false;
				} else {
					regulator.regulate(actualColorValue);
				}
			}
			// after f.e findGab switch to rgb mode to find the end of the line
			// with the blue strip
		}
		this.driveToNextMission();
		robot.pilotStop();
		return end;

	}

	/**
	 * the robot drives around the obstacle
	 */
	public void driveAroundObstacle() {
		this.tempo = 250f;
		this.kp = 1950;

		float OPTIMALVALUE = 0.06f; // 0.1
		float actualSonicValue = 0.0f;
		float actualColorValue = 0.0f;

		float kpSonic = 2500;

		// enter()
		this.robot.motorsStop();
		this.robot.pilotStop();
		this.robot.clearLCD();
		this.robot.beepSequence();
		this.robot.drawString("Block umfahren", 0, 0);
		RegulatorP regulator = new RegulatorP(this.robot, this.tempo + 150, kpSonic, OPTIMALVALUE);

		// start()
		this.robot.pilotTravel(-3);
		this.robot.RotateRight(450); // 90 Grad

		// Regulator start
		this.robot.forward();
		actualColorValue = this.robot.getColorSensor().getColor()[0];

		while (actualColorValue < WHITE - 2 * OFFSET) {

			actualSonicValue = this.robot.getUltraSonicSensor().getDistance();

			this.robot.drawString("Abstand: " + actualSonicValue, 0, 0);

			regulator.sonicRegulate(actualSonicValue);

			actualColorValue = this.robot.getColorSensor().getColor()[0];
			this.robot.drawString("Lichtwert: " + actualColorValue, 0, 10);
		}

		// end
		this.robot.drawString("Ende", 0, 0);
		this.tempo = 350;
		this.kp = 2100;
	}

	/*
	 * the robot searches for the line
	 */
	public void findLine() {
		while (true) {
			if (this.findRight()) {
				break;
			}
			this.rotateLeft();
			if (this.findLeft()) {
				break;
			}
			this.rotateRight();
			if (afterBox) {
				this.nextMission = true;  
				break;
			}
			if (this.secondGap == 2) {
				this.robot.pilotTravel(6.5);
			} else {
				this.robot.pilotTravel(8);
				this.secondGap++;
			}
			
		}
		this.robot.motorsStop();
	}

	public void driveToNextMission() {
		float OPTIMALVALUE = 0.03f;
		float actualSonicValue = 0.0f;
		float actualBlueValue = 0.0f; // Blue

		float kpSonic = 4000;

		this.robot.drawString("next mission", 0, 0);

		// enter()
		this.robot.motorsStop();
		this.robot.pilotStop();
		this.robot.adjustMotorspeed(300, 300);

		RegulatorP regulator = new RegulatorP(this.robot, 300f, kpSonic, OPTIMALVALUE);

		this.robot.getColorSensor().setColorIDMode();
		// Regulator start
		//this.robot.RotateLeft(110);
		this.robot.pilotTravel(5);
		this.robot.forward();
		actualBlueValue = this.robot.getColorSensor().getColor()[0];

		while (!(actualBlueValue == 1 || actualBlueValue == 2)) {

			actualSonicValue = this.robot.getUltraSonicSensor().getDistance();
			this.robot.drawString("Abstand: " + actualSonicValue, 0, 1);
			this.robot.drawString("ColorValue: " + actualBlueValue, 0, 0);
			regulator.sonicRegulate(actualSonicValue);

			actualBlueValue = this.robot.getColorSensor().getColor()[0];
		}
	}

	private boolean findRight() {
		this.robot.adjustMotorspeed(400, -133);
		for (int i = 0; i < 1100; i++) {
			Delay.msDelay(1);
			if (this.robot.getColorSensor().getColor()[0] > WHITE - 12 * OFFSET) {
				this.robot.motorsStop();
				return true;
			}
		}
		return false;
	}
	 
	
	private boolean findLeft() {
		this.robot.adjustMotorspeed(-133, 400);
		for (int i = 0; i < 750; i++) {
			Delay.msDelay(1);
			if (this.robot.getColorSensor().getColor()[0] > WHITE - 12 * OFFSET) {
				Delay.msDelay(100);
				this.robot.motorsStop();
				return true;
			}
		}
		return false;
	}
	

	private void rotateLeft() {
		this.robot.adjustMotorspeed(-133, 400);
		for (int i = 0; i < 1100; i++) {
			if (this.robot.getColorSensor().getColor()[0] > WHITE - 12 * OFFSET) {

			}
			Delay.msDelay(1);
		}
		Delay.msDelay(350);
	}
	
	
	private void rotateRight() {
		this.robot.adjustMotorspeed(400, -133);
		for (int i = 0; i < 750; i++) {
			if (this.robot.getColorSensor().getColor()[0] > WHITE - 12 * OFFSET) {

			}
			Delay.msDelay(1);
		}
	}
	
	private void setSpeedFast() {
		this.tempo = 350f;
		this.tempo = 2100f;
	}
	
	private void setSpeedSlow() {
		this.tempo = 250f;
		this.tempo = 1950f;
	}
	
}
