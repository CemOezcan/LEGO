package mission;

import lejos.hardware.Button;
import lejos.hardware.Sound;
import lejos.hardware.lcd.LCD;
import robot.RegulatorP;
import robot.Robot;

public class BridgeCrosser implements Mission {

	private final Robot robot;

	private final float tempo = 250f;
	private final float ROBOTHEIGHT = 0.03f;
	private final float OPTIMALVALUE = 0.1f;
	private final float KP = 2000;
	private final float OFFSET = 0.03f;

	public BridgeCrosser(Robot robot) {
		this.robot = robot;
	}

	@Override
	public boolean executeMission() {
		this.robot.beep();
		this.robot.getColorSensor().setColorIDMode();
		RegulatorP regulator = new RegulatorP(this.robot, this.tempo, this.KP, this.OPTIMALVALUE);
		boolean end = false;
		float actualGroundDistance = 0.0f;

		this.robot.adjustMotorspeed(tempo, tempo);
		
		this.robot.pilotTravel(5);
		this.robot.ultraSonicSensorDown();
		this.robot.forward();

		while (Button.LEFT.isUp() && !end) {
			if (this.robot.getPressureSensorLeft().isTouched()) {
				endSequence();
				end = true;
			} else {
				actualGroundDistance = getDistanceValue(robot.getUltraSonicSensor().getDistance());
				this.robot.drawString("Abstand: " + actualGroundDistance, 0, 0);
				regulator.bridgeRegulate(actualGroundDistance);
			}
		}
		robot.pilotStop();
		
		return end;
	}

	public float getDistanceValue(float value) {
		float result = value;
		if (value > ROBOTHEIGHT + OFFSET) {
			result = 0.2f;
		}
		return result;
	}

	public void endSequence() {
		
		float actualColorValue = 0.0f;
		this.robot.pilotStop();
		this.robot.pilotTravel(-4);
		this.robot.ultraSonicSensorUp();
		this.robot.RotateRight(20);
		this.robot.forward();
		this.robot.adjustMotorspeed(200, 200);
		actualColorValue = robot.getColorSensor().getColor()[0];
		
		while (!(actualColorValue == 1 || actualColorValue == 2)) {
			if (this.robot.getPressureSensorLeft().isTouched()) {
				this.robot.pilotTravel(-3);
				this.robot.RotateRight(20);
				this.robot.forward();
			}
			actualColorValue = robot.getColorSensor().getColor()[0];
			this.robot.drawString("Farbe: " + actualColorValue, 0, 0);
		}		

		// blue line found
//		this.robot.RotateLeft(50);
		this.robot.pilotStop();
	}

}
