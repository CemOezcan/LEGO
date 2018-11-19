package mission;

import lejos.hardware.Button;
import lejos.hardware.Sound;
import lejos.hardware.lcd.LCD;
import robot.RegulatorP;
import robot.Robot;

public class BridgeCrosser implements Mission {

	private final Robot robot;
	
	private final float tempo = 300f;
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

		this.robot.ultraSonicSensorDown();
		this.robot.getColorSensor().setColorIDMode();
		RegulatorP regulator = new RegulatorP(this.robot, this.tempo, this.KP, this.OPTIMALVALUE);

		boolean end = false;
		boolean afterFirstCorner = false;
		boolean afterSecondCorner = false;
		robot.adjustMotorspeed(tempo, tempo);
		int j = 0;
		while (Button.LEFT.isUp() && !end) {
			float actualGroundDistance = getDistanceValue(robot.getUltraSonicSensor().getDistance());
			float actualColorValue = robot.getColorSensor().getColor()[0];
			if (actualGroundDistance < ROBOTHEIGHT + OFFSET) {
				j++;
			} else {
				j = 0;
			}
			if (j > 7000) {
				if (afterFirstCorner = true) {
					afterSecondCorner = true;
				} else {
					afterFirstCorner = true;
				}
			}
			
			//if (actualColorValue == 1 || actualColorValue == 2) {
			//	end = true;
			//} else {
				this.robot.drawString(" distance: "  + actualGroundDistance + "  " + j, 0, 0);
				regulator.bridgeRegulate(actualGroundDistance);
			//}
				
		}
		return end;
	}
	
	public float getDistanceValue(float value) {
		float result = value;
		if (value > ROBOTHEIGHT + OFFSET) {
			result = 0.2f;
		}
		return result;
	}

}
