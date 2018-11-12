package mission;

import lejos.hardware.Button;
import lejos.hardware.Sound;
import lejos.hardware.lcd.LCD;
import lejos.utility.Delay;
import robot.Robot;

public class BoxShifter implements Mission {

	private final Robot robot;

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

		// this.robot.forward();
		// while(!robot.getPressureSensorLeft().isTouched() &&
		// !robot.getPressureSensorRight().isTouched()) {
		// robot.drawString("Left: " +
		// robot.getPressureSensorLeft().isTouched(), 0, 0);
		// robot.drawString("Right: " +
		// robot.getPressureSensorRight().isTouched(), 0, 10);
		// }

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
		

		// this.robot.forward();

		// while(!robot.getPressureSensorLeft().isTouched() &&
		// !robot.getPressureSensorRight().isTouched()) {
		// robot.drawString("Left: " +
		// robot.getPressureSensorLeft().isTouched(), 0, 0);
		// robot.drawString("Right: " +
		// robot.getPressureSensorRight().isTouched(), 0, 10);
		// }
		robot.motorsStop();
	}
}
