package mission;

import robot.Robot;

public class BoxShifter implements Mission {

	private final Robot robot;
	private final float BLACK = 0.08f;
	private final float OFFSET = 0.10f;

	public BoxShifter(Robot robot) {
		this.robot = robot;
	}

	@Override
	public boolean executeMission() {
		this.robot.beep();

		int distanceCounter = 0;

		this.robot.getColorSensor().setRedMode();

		// turn around
		this.robot.pilotTravel(7);
		this.robot.RotateLeft(1130);

		// find box
		distanceCounter = this.findBox();

		this.shiftToWall();

		this.shiftToEdge(distanceCounter);

		this.findBlueLine();

		this.robot.pilotStop();
		this.robot.motorsStop();
		
		return true; //return the correct value
	}

	private int findBox() {
		float actualSonicValue = 0.0f;
		int distanceCounter = 0;

		this.robot.drawString("find box", 0, 0);

		this.robot.backward();

		actualSonicValue = this.robot.getUltraSonicSensor().getDistance();

		while (actualSonicValue > 0.17) {
			distanceCounter++;
			actualSonicValue = this.robot.getUltraSonicSensor().getDistance();
			this.robot.drawString("Abstand: " + actualSonicValue, 0, 0);
		}
		return distanceCounter;
	}

	private void shiftToWall() {
		this.robot.drawString("push Box to wall", 0, 0);
		this.robot.RotateLeft(550);

		this.robot.forward();

		// first white line
		float colorValue = this.robot.getColorSensor().getColor()[0];
		while (colorValue < BLACK + OFFSET) {
			colorValue = this.robot.getColorSensor().getColor()[0];
		}

		this.robot.beep();
		this.robot.forward();

		// second white line
		colorValue = this.robot.getColorSensor().getColor()[0];
		while (colorValue < BLACK + OFFSET) {
			colorValue = this.robot.getColorSensor().getColor()[0];
		}
		this.robot.beep();

		// push box to wall
		this.robot.pilotTravel(4);
	}

	private void shiftToEdge(int distanceCounter) {
		float actualSonicValue = 0.0f;

		this.robot.drawString("push Box to edge", 0, 0);

		this.robot.pilotTravel(-2);
		this.robot.RotateLeft(550);
		this.robot.pilotTravel(10);
		this.robot.RotateRight(550);
		this.robot.pilotTravel(6);
		this.robot.RotateRight(550);

		this.robot.forward();

		// zuvor zur¸ckgelegte Distanz wieder zur¸ckfahren
		while (distanceCounter > 0) {
			actualSonicValue = this.robot.getUltraSonicSensor().getDistance();
			this.robot.drawString("Abstand: " + actualSonicValue, 0, 0);
			distanceCounter--;
		}

		// roboter l‰nge weiterfahren
		this.robot.pilotTravel(6);
	}

	private void findBlueLine() {
		// Suche weiﬂe Linie
		this.robot.pilotTravel(-7);
		this.robot.RotateRight(550);

		this.robot.forward();

		float colorValue = this.robot.getColorSensor().getColor()[0];
		while (colorValue < BLACK + OFFSET) {
			colorValue = this.robot.getColorSensor().getColor()[0];
		}

		// Suche zweite weiﬂe Linie
		this.robot.pilotTravel(5);
		this.robot.RotateLeft(530);

		this.robot.forward();

		colorValue = this.robot.getColorSensor().getColor()[0];
		while (colorValue < BLACK + OFFSET) {
			colorValue = this.robot.getColorSensor().getColor()[0];
		}

		// Suche blaue Linie
		this.robot.getColorSensor().setColorIDMode();
		this.robot.forward();
		colorValue = this.robot.getColorSensor().getColor()[0];
		while (!((colorValue == 1) || (colorValue == 2))) {
			colorValue = this.robot.getColorSensor().getColor()[0];
		}
	}
}
