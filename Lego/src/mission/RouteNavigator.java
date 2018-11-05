package mission;

import lejos.hardware.lcd.LCD;
import robot.RegulatorP;
import robot.Robot;
import lejos.hardware.Button;
import lejos.hardware.Sound;

public class RouteNavigator implements Mission {

	private final Robot robot;
	
	/*
	 * the final color values of the colors
	 */
	private final float BLACK = 0.08f;
	private final float WHITE = 0.48f;
	private final float BLUE = 0.0f; //TODO: add value
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
		Sound.beep();
		
		robot.getColorSensor().setRedMode();
		RegulatorP regulator = new RegulatorP(this.robot, this.tempo, this.kp, this.OPTIMALVALUE);
		
		robot.forward();
		
		boolean end = false;
		
		robot.adjustMotorspeed(tempo, tempo);
		
		float leftMotorSpeed = 0;
		float rightMotorSpeed = 0;
		
		while (Button.LEFT.isUp() && !end) {
			float actualValue = robot.getColorSensor().getColor()[0];
			
			LCD.drawString("speed = " + leftMotorSpeed + ", " + rightMotorSpeed + " color: " + actualValue, 0, 0);
			
			//the touchsensors are touched and the robot has to drive around the obstacle
			if (robot.getPressureSensorLeft().isTouched() || robot.getPressureSensorRight().isTouched()) {
				robot.pilotStop();
				robot.motorsStop();
				driveAroundObstacle();
				
				
			} else if (actualValue < BLACK + OFFSET) { //find line
				robot.pilotStop();
				robot.motorsStop();
				findLine();
				
			} else { //normal case calculate the new speeds for both motors
				regulator.regulate(actualValue);
			}
			//after f.e findGab switch to rgb mode to find the end of the line with the blue strip
		}
		robot.pilotStop();
	}

	/**
	 * the robot drives around the obstacle
	 */
	public void driveAroundObstacle() {

		float FARAWAY = 0.1f;
		float actualSonicValue = 0.0f;
		float actualColorValue = 0.0f;
		float leftMotorSpeed = 0.0f;
		float rightMotorSpeed = 0.0f;
		
		float kpSonic = 500;

		// enter()
		this.robot.motorsStop();
		this.robot.pilotStop();
		Sound.beepSequence();
		LCD.drawString("Block umfahren", 0, 0);
		
		// start()
		this.robot.pilotTravel(-4);
		this.robot.RotateRight(550); // 90 grad

		// P Regler Abstand messen
		robot.forward();
		boolean end = false;
		while (actualColorValue < WHITE - 2 * OFFSET) {
		
			actualColorValue = this.robot.getColorSensor().getColor()[0];
			actualSonicValue = this.robot.getUltraSonicSensor().getDistance();
			
			if (actualColorValue > WHITE - 2 * OFFSET) {
				end = true;
			} else {
				float y = kpSonic * (actualSonicValue - FARAWAY);
				leftMotorSpeed = tempo - y;
				rightMotorSpeed = tempo + y;
				
				//RobotMotorGeschwindigkeit anpassen mit den übergebenen Werten left right Motor speed
				
				this.robot.adjustMotorspeed(leftMotorSpeed, rightMotorSpeed);
			}
		}

		// ende
		LCD.drawString("Ende", 0, 0);
		this.robot.pilotTravel(1);
		this.robot.RotateRight(200);

	}
	
	/*
	 * the robot searches for the line
	 */
	public void findLine() {
		LCD.clear();
		LCD.drawString("Find Line", 0, 0);
		
		this.robot.pilotStop();
		this.robot.motorsStop();
		boolean fromRight = false;
		boolean found = false;
		while (!found) {
			int arc = 0;
			while (arc <= 440 && !found) {
				this.robot.RotateRight(40);
				found = this.robot.getColorSensor().getColor()[0] > BLACK + 2 * OFFSET;
				arc += 40;
			}
			if (!found) {
				this.robot.RotateLeft(arc);
				arc = 0;
				while (arc <= 440 && !found) {
					this.robot.RotateLeft(40);
					found = this.robot.getColorSensor().getColor()[0] > BLACK + 2 * OFFSET;
					if (found) {
						fromRight = true;
					}
					arc += 40;
				}
			}
			if (!found) {
				this.robot.RotateRight(arc);
				LCD.drawString("Line not found", 0, 0);
				this.robot.pilotTravel(6);
			}
		}
		
		if (fromRight) {
			robot.RotateLeft(100);
		}
	}
	
}
