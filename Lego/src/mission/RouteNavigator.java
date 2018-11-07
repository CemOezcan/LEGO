package mission;

import robot.RegulatorP;
import robot.Robot;
import lejos.hardware.Button;

public class RouteNavigator implements Mission {

	private final Robot robot;

	/*
	 * the final color values of the colors
	 */
	private final float BLACK = 0.08f;
	private final float WHITE = 0.48f;
	private final float BLUE = 0.0f; // TODO: add value
	private final float OFFSET = 0.01f;
	private final float OPTIMALVALUE = (WHITE + BLACK) / 2;

	private final float tempo = 150f;

	/*
	 * the constant for the p-controller
	 */
	private final float kp = 1200;

	/*
	 * constructs a new route navigator
	 */
	public RouteNavigator(Robot robot) {
		this.robot = robot;
	}

	@Override
	public void executeMission() {
		this.robot.beep();

		this.robot.setRedMode();
		RegulatorP regulator = new RegulatorP(this.robot, this.tempo, this.kp, this.OPTIMALVALUE);

		this.robot.forward();

		boolean end = false;

		robot.adjustMotorspeed(tempo, tempo);

		while (Button.LEFT.isUp() && !end) {
			float actualColorValue = robot.getColorValue();

			this.robot.drawString(" color: " + actualColorValue, 0, 0);

			// the touchsensors are touched and the robot has to drive around
			// the obstacle
			if (this.robot.touchSensorLeftIsTouched() || this.robot.touchSensorRightIsTouched()) {
				robot.pilotStop();
				robot.motorsStop();
				driveAroundObstacle();

			} else if (actualColorValue < BLACK + OFFSET) { // find line
				robot.pilotStop();
				robot.motorsStop();
				findLine();

			} else { // normal case calculate the new speeds for both motors
				regulator.regulate(actualColorValue);
			}
			// after f.e findGab switch to rgb mode to find the end of the line
			// with the blue strip
		}
		robot.pilotStop();
	}

	/**
	 * the robot drives around the obstacle
	 */
	public void driveAroundObstacle() {

		float OPTIMALVALUE = 0.05f;
		float actualSonicValue = 0.0f;
		float actualColorValue = 0.0f;

		float kpSonic = 1000;

		// enter()
		this.robot.motorsStop();
		this.robot.pilotStop();

		this.robot.clearLCD();
		this.robot.beepSequence();
		this.robot.drawString("Block umfahren", 0, 0);
		RegulatorP regulator = new RegulatorP(this.robot, this.tempo, kpSonic, OPTIMALVALUE);

		// start()
		this.robot.pilotTravel(-4);
		this.robot.RotateRight(550); // 90 Grad

		// Regulator start
		this.robot.forward();
		actualColorValue = this.robot.getColorValue();

		while (actualColorValue < WHITE - 2 * OFFSET) {

			actualSonicValue = this.robot.getUltraSonicDistance();

			this.robot.drawString("Abstand: " + actualSonicValue, 0, 0);

			regulator.regulate(actualSonicValue);

			actualColorValue = this.robot.getColorValue();
			this.robot.drawString("Lichtwert: " + actualColorValue, 0, 10);
		}

		// end
		this.robot.drawString("Ende", 0, 0);
		this.robot.pilotTravel(1);
		this.robot.RotateRight(400);
	}

	/*
	 * the robot searches for the line
	 */
	public void findLine() {
		this.robot.clearLCD();
		this.robot.drawString("Find Line", 0, 0);

		this.robot.pilotStop();
		this.robot.motorsStop();
		boolean fromRight = false;
		boolean found = false;
		while (!found) {
			int arc = 0;
			while (arc <= 440 && !found) {
				this.robot.RotateRight(40);
				found = this.robot.getColorValue() > BLACK + 2 * OFFSET;
				arc += 40;
			}
			if (!found) {
				this.robot.RotateLeft(arc);
				arc = 0;
				while (arc <= 440 && !found) {
					this.robot.RotateLeft(40);
					found = this.robot.getColorValue() > BLACK + 2 * OFFSET;
					if (found) {
						fromRight = true;
					}
					arc += 40;
				}
			}
			if (!found) {
				this.robot.RotateRight(arc);
				this.robot.drawString("Line not found", 0, 0);
				this.robot.pilotTravel(6);
			}
		}

		if (fromRight) {
			robot.RotateLeft(100);
		}
	}

}
