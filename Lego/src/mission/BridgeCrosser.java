package mission;

import lejos.hardware.Button;
import lejos.hardware.Sound;
import lejos.hardware.lcd.LCD;
import robot.RegulatorP;
import robot.Robot;

public class BridgeCrosser implements Mission {

	private final Robot robot;
	
	private final float tempo = 200f;
	private final float ROBOTHEIGHT = 0.07f;
	private final float BRIDGEHEIGHT = 0.3f;
	private final float OPTIMALVALUE = 0.1f;
	private final float KP = 1500;
	private final float OFFSET = 0.03f;
	
	public BridgeCrosser(Robot robot) {
		this.robot = robot;
	}
	
	@Override
	public void executeMission() {
		this.robot.beep();

		this.robot.ultraSonicSensorDown();
		this.robot.getColorSensor().setColorIDMode();
		RegulatorP regulator = new RegulatorP(this.robot, this.tempo, this.KP, this.OPTIMALVALUE);

		boolean end = false;
		robot.adjustMotorspeed(tempo, tempo);

		while (Button.LEFT.isUp() && !end) {
			float actualGroundDistance = getDistanceValue(robot.getUltraSonicSensor().getDistance());
			float actualColorValue = robot.getColorSensor().getColor()[0];
			if (actualColorValue == 1 || actualColorValue == 2) {
				end = true;
			} else {
				this.robot.drawString(" distance: " + actualGroundDistance, 0, 0);
				regulator.sonicRegulate(actualGroundDistance);
			}
		}
	}
	
	public float getDistanceValue(float value) {
		float result = value;
		if (value > ROBOTHEIGHT + OFFSET) {
			result = 0.2f;
		}
		return result;
	}

}
