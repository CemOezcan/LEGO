package mission;

import lejos.hardware.Button;
import lejos.hardware.Sound;
import lejos.hardware.lcd.LCD;
import lejos.utility.Delay;
import robot.Robot;

public class BoxShifter implements Mission {

	private final Robot robot;
	private final float BLACK = 0.08f;
	private final float OFFSET = 0.01f;


	public BoxShifter(Robot robot) {
		this.robot = robot;
	}

	@Override
	public void executeMission() {
		Sound.beep();

		float actualSonicValue = 0.0f;
		int distanceCounter = 0;
		
		this.robot.getColorSensor().setRedMode();

		// find box
		this.robot.drawString("find box", 0, 0);

		this.robot.pilotTravel(7);
		this.robot.RotateLeft(1130);
		this.robot.backward();

		actualSonicValue = this.robot.getUltraSonicSensor().getDistance();

		while (actualSonicValue > 0.17) {
			distanceCounter++;
			actualSonicValue = this.robot.getUltraSonicSensor().getDistance();
			this.robot.drawString("" + actualSonicValue, 0, 0);
		}

		// push box to wall
		this.robot.drawString("push Box to wall", 0, 0);

		this.robot.RotateLeft(550);

		this.robot.pilotTravel(20);

		// push box to edge
		this.robot.drawString("push Box to edge", 0, 0);

		this.robot.pilotTravel(-2);
		this.robot.RotateLeft(550);
		this.robot.pilotTravel(10);
		this.robot.RotateRight(550);
		this.robot.pilotTravel(6);
		this.robot.RotateRight(550);

		this.robot.forward();
		
		while(distanceCounter > 0) {
			actualSonicValue = this.robot.getUltraSonicSensor().getDistance();
			this.robot.drawString("egal " + actualSonicValue, 0, 0);
			distanceCounter--;
		}
		
		this.robot.pilotTravel(5);
		this.robot.pilotStop();
		this.robot.motorsStop();
		
		//Suche weiﬂe Linie
		this.robot.pilotTravel(-5);
		this.robot.RotateRight(550);
		
		float value = this.robot.getColorSensor().getColor()[0];
		while (value < BLACK + OFFSET) {
			this.robot.forward();
			value = this.robot.getColorSensor().getColor()[0];
		}
		
		//Suche zweite weiﬂe Linie
		this.robot.pilotTravel(5);
		this.robot.RotateLeft(550);
		value = this.robot.getColorSensor().getColor()[0];
		while (value < BLACK + OFFSET) {
			this.robot.forward();
			value = this.robot.getColorSensor().getColor()[0];
		}
		
		//Suche blaue Linie
		this.robot.getColorSensor().setColorIDMode();
		value = this.robot.getColorSensor().getColor()[0];
		while ((value == 1) || (value == 2)) {
			this.robot.forward();
			value = this.robot.getColorSensor().getColor()[0];
		}
		
	}
}
