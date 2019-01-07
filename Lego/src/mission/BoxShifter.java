package mission;

import lejos.hardware.Button;
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
		
		this.robot.setTravelSpeed(5);
		
		this.robot.beep();

		int distanceCounter = 0;
		boolean end = false;
		
		this.robot.ultraSonicSensorUp();
		this.robot.getColorSensor().setRedMode();
		
		while (Button.LEFT.isUp() && !end) {
			// turn around
			this.robot.pilotTravel(7);
			this.robot.RotateRight(1050);

			// find box
			distanceCounter = this.findBox();

			this.shiftToWall();

			this.shiftToEdge(distanceCounter);

			this.findBlueLine();

			this.robot.pilotStop();
			end = true;
		}
		
		return end; //return the correct value
	}

	private int findBox() {
		float actualSonicValue = 0.0f;
		int distanceCounter = 0;

		this.robot.drawString("find box", 0, 0);

		this.robot.backward();

		actualSonicValue = this.robot.getUltraSonicSensor().getDistance();

		while (actualSonicValue > 0.24) {
			distanceCounter++;
			actualSonicValue = this.robot.getUltraSonicSensor().getDistance();
			this.robot.drawString("Abstand: " + actualSonicValue, 0, 0);
		}
		return distanceCounter;
	}

	private void shiftToWall() {
		this.robot.drawString("push Box to wall", 0, 0);
		this.robot.RotateLeft(560);

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
		this.robot.pilotTravel(3);
	}

	private void shiftToEdge(int distanceCounter) {
		float actualSonicValue = 0.0f;

		this.robot.drawString("push Box to edge", 0, 0);

		this.robot.pilotTravel(-2);
		this.robot.RotateLeft(560);
		this.robot.pilotTravel(10);
		this.robot.RotateRight(550);
		this.robot.pilotTravel(5);
		this.robot.RotateRight(530);

		this.robot.forward();

		// zuvor zur¸ckgelegte Distanz wieder zur¸ckfahren
		while (distanceCounter > 0) {
			actualSonicValue = this.robot.getUltraSonicSensor().getDistance();
			this.robot.drawString("Abstand: " + actualSonicValue, 0, 0);
			distanceCounter--;
		}

		// roboter l‰nge weiterfahren
		this.robot.pilotTravel(8);
	}

	private void findBlueLine() {
		// Suche weiﬂe Linie
		this.robot.pilotTravel(-7);
		this.robot.RotateRight(520);

		this.robot.forward();

		float colorValue = this.robot.getColorSensor().getColor()[0];
		while (colorValue < BLACK + OFFSET) {
			colorValue = this.robot.getColorSensor().getColor()[0];
		}

		// Suche zweite weiﬂe Linie
		this.robot.pilotTravel(4);
		this.robot.RotateLeft(555);

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
			if (robot.getPressureSensorLeft().isTouched()) {
				robot.pilotTravel(-5);
				robot.RotateRight(60);
				this.robot.forward();
			}
			colorValue = this.robot.getColorSensor().getColor()[0];
		}
	}
}
