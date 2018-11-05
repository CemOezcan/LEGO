package mission;

import lejos.hardware.lcd.LCD;
import lejos.utility.Delay;
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
		
		//whats the startconfiguration??
		//should we make a method for startconfiguration?
		robot.forward();
		
		boolean end = false;
		
		float lastDifference = 0f;
		
		robot.adjustMotorspeed(tempo, tempo);
		
		float leftMotorSpeed = 0;
		float rightMotorSpeed = 0;
		
		while (Button.LEFT.isUp() && !end) {
			float actualValue = robot.getColorSensor().getColor()[0];
			
			LCD.drawString("speed = " + leftMotorSpeed + ", " + rightMotorSpeed, 0, 0);
			LCD.drawString("color = " + actualValue, 10 ,0);
			
			
			
			//the touchsensors are touched and the robot has to drive around the obstacle
			if (robot.getPressureSensorLeft().isTouched() || robot.getPressureSensorRight().isTouched()) {
				driveAroundObstacle();
				
				
			} /**else if (actualValue < WHITE - 2 * OFFSET) { //90 degree turn
				
				// findLine() ausführen ist vermutlich besser.
				
				leftMotorSpeed =  tempo;
				rightMotorSpeed = tempo;

				// adjust the robot's speed
				this.robot.adjustMotorspeed(leftMotorSpeed, rightMotorSpeed);
				
			} */else if (actualValue < BLACK + OFFSET) { //LineGap
				// nur ausführen wenn der Robot nichts nach dem umschauen gefunden hat
				//muss sich eig nicht umschauen, da wenn plötzlich Schwarz wird, aufjedenfall Lücke kommt
				// also in findLine() ausführen
				findLine();
				
			} else { //normal case calculate the new speeds for both motors
				lastDifference = actualValue - OPTIMALVALUE;
				float y = kp * lastDifference;
				
				leftMotorSpeed = tempo - y;
				rightMotorSpeed = tempo + y;
				
				//RobotMotorGeschwindigkeit anpassen mit den übergebenen Werten left right Motor speed
				
				this.robot.adjustMotorspeed(leftMotorSpeed, rightMotorSpeed);
			}
			
			//after f.e findGab switch to rgb mode to find the end of the line with the blue strip
		}
		robot.pilotStop();
	}

	/**
	 * the robot drives around the obstacle
	 */
	public void driveAroundObstacle() {
		Sound.beepSequence();
	}
	
	public void findLineAfterGap() {
		Sound.beepSequence();
	}
	

	/*
	 * the robot searches for the line
	 */
	public void findLine() {
		LCD.clear();
		LCD.drawString("Find Line", 0, 0);
		
		this.robot.pilotStop();
		this.robot.motorsStop();
		
		boolean found = false;
		while (!found) {
			int arc = 0;
			while (arc < 360 && !found) {
				this.robot.RotateRight(40);
				found = this.robot.getColorSensor().getColor()[0] > BLACK + 2 * OFFSET;
				arc += 40;
			}
			if (!found) {
				this.robot.RotateLeft(arc);
				arc = 0;
				while (arc < 360 && !found) {
					this.robot.RotateLeft(40);
					found = this.robot.getColorSensor().getColor()[0] > BLACK + 2 * OFFSET;
					arc += 40;
				}
			}
			if (!found) {
				this.robot.RotateRight(arc);
				LCD.drawString("Line not found", 0, 0);
				this.robot.pilotTravel(5);
			}
		}
		
		this.robot.forward();
	}
	
}
