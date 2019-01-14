package mission;

import lejos.hardware.Button;
import robot.RegulatorP;
import robot.Robot;
import sensor.ColorSensor;

public class TreasureHunter implements Mission {

	private final Robot robot;

	private final float RED = 0;
	private final float WHITE = 6;
	private final float BLUE = 2;
	private final float SPEED = 7;
	private final float KP = 1500;
	private final float OPTIMAL_VALUE = 0.13f;
	
	//Regulator
	

	// RGB
	float[] red = new float[] { 0.095f, 0.25f, 0.013f };
	float[] blue = new float[] { 0.016f, 0.06f, 0.05f };
	float[] white = new float[] { 0.1f, 0.2f, 0.09f };
	float rgbOffset = 0.02f;

	private boolean foundWhite;
	private boolean foundRed;
	private boolean foundBlue;
	private boolean leftSide;
	private boolean isTouched;
	
	private RegulatorP regulator;

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
		this.foundBlue = false;
		this.isTouched = false;

		this.robot.setTravelSpeed(SPEED);
		robot.getColorSensor().setRGBMode();

		this.robot.pilotTravel(4);
		this.robot.forward();
		// colorTest();

		while (!(this.foundRed && this.foundWhite)) {
			if (!Button.LEFT.isUp()) {
				this.robot.pilotStop();
				return false;
			}

			robot.drawString("White: " + this.foundWhite, 0, 0);
			robot.drawString("Red: " + this.foundRed, 0, 1);

			isTouched = (robot.getPressureSensorLeft().isTouched() || robot.getPressureSensorRight().isTouched()); // ||
																													// (isBlue(this.colorSensor.getColor())
			this.scan();

			if (isTouched) {
				robot.pilotStop();
				if (this.leftSide) {
					this.turnLeft();
					this.leftSide = false;
				} else {
					turnRight();
					this.leftSide = true;
				}
			}
			if (this.foundWhite && this.foundRed) {
				robot.pilotStop();
				robot.drawString("FERTIG!" + this.foundWhite, 0, 2);
			}
		}
		this.robot.pilotStop();
		return true;
	}

	private void scan() {
		float[] colorValue = this.colorSensor.getColor();
		
		if (isWhite(colorValue)) {
			this.foundWhite = true;
			this.robot.beepSequence();
		}else if (isRed(colorValue) && (!this.foundRed)) {
			this.robot.beepSequence();
			this.foundRed = true;
		}else if (isBlue(colorValue) && !foundBlue) {
			foundBlue = true;
			this.robot.drawString("blue = " + foundBlue, 0, 4);
			this.robot.pilotTravel(-4);
			for (int i = 0; i < 2; i++) {
				this.robot.RotateRight(275);
				this.scan();
			}
			this.robot.pilotTravel(3);
			this.scan();
			for (int i = 0; i < 2; i++) {
				this.robot.RotateRight(275);
				this.scan();
			}
			this.robot.forward();
			this.leftSide = true;
		}
		if (this.foundRed && this.foundWhite) {
			robot.pilotStop();
			robot.drawString("FERTIG!" + this.foundWhite, 0, 2);
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
			isTouched = (this.robot.getPressureSensorLeft().isTouched()
					|| this.robot.getPressureSensorRight().isTouched());
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
		for (int i = 0; i < 2; i++) {
			this.robot.RotateRight(275);
			this.scan();
		}
		this.robot.pilotTravel(3);
		this.scan();
		for (int i = 0; i < 2; i++) {
			this.robot.RotateRight(275);
			this.scan();
		}

		this.robot.pilotTravel(-5);
		this.robot.forward();
	}

	private void turnLeft() {
		this.robot.pilotTravel(-4);
		for (int i = 0; i < 2; i++) {
			this.robot.RotateLeft(275);
			this.scan();
		}

		this.robot.pilotTravel(3); // 2.5
		this.scan();
		for (int i = 0; i < 2; i++) {
			this.robot.RotateLeft(275);
			this.scan();
		}

		this.robot.pilotTravel(-5);
		this.robot.forward();
	}

	private boolean isRed(float[] color) {
		if ((color[0] > 0.09) && (color[1] < 0.03) && (color[2] < 0.03)) {
			return true;
		} else {
			return false;
		}
	}

	private boolean isWhite(float[] color) {
		if ((color[0] < white[0] + rgbOffset) && (color[1] < white[1] + rgbOffset)
				&& (color[2] > white[2] - rgbOffset)) {
			return true;
		} else {
			return false;
		}
	}

	private boolean isBlue(float[] color) {
		if ((color[0] < blue[0] + rgbOffset && color[0] > blue[0] - rgbOffset)
				&& (color[1] < blue[1] + rgbOffset && color[1] > blue[1] - rgbOffset)
				&& (color[2] < blue[2] + rgbOffset && color[2] > blue[2] - rgbOffset)) {
			return true;
		} else {
			return false;
		}
	}

	private void colorTest() {
		robot.pilotStop();
		for (int i = 0; i < 1; i = i) {
			robot.drawString("" + robot.getColorSensor().getColor()[0], 0, 0);
			robot.drawString("" + robot.getColorSensor().getColor()[1], 0, 1);
			robot.drawString("" + robot.getColorSensor().getColor()[2], 0, 2);

			robot.drawString("Rot: " + isRed(robot.getColorSensor().getColor()), 0, 3);

			robot.drawString("Blau: " + isBlue(robot.getColorSensor().getColor()), 0, 4);

			robot.drawString("Wei�: " + isWhite(robot.getColorSensor().getColor()), 0, 5);
		}
	}

	//Berechnet einen Mittelwert von 50 Messungen
	private float getDistance() {
		float distance = 0f;
		int times = 0;
		for (times = 0; times < 20; times++) {
			distance += robot.getUltraSonicSensor().getDistance();
		}
		return (distance/times);
	}
}
