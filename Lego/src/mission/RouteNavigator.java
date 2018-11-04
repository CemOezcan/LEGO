package mission;

import lejos.hardware.lcd.LCD;
import lejos.utility.Delay;
import robot.Robot;
import lejos.hardware.Button;
import lejos.hardware.Sound;

public class RouteNavigator implements Mission {

	private final Robot robot;
	
	//TODO: color values
	private final float BLACK = 0.08f;
	private final float WHITE = 0.48f;
	private final float BLUE = 0.0f; //TODO: add value
	private final float OFFSET = 0.01f; 
	private final float OPTIMALVALUE = (WHITE + BLACK) / 2;
	
	private final float tempo = 220f;
	
	
	private final float kp = (tempo / WHITE - OPTIMALVALUE);

	public RouteNavigator(Robot robot) {
		this.robot = robot;
	}

	@Override
	public void executeMission() {
		Sound.beep();
		
		robot.getColorSensor().setRedMode();
		
		robot.forward();
		
		boolean end = false;
		
		float lastDifference = 0f;
		
		while (Button.LEFT.isUp() && !end) {
			float actualValue = robot.getColorSensor().getColor()[0];
			
			LCD.drawString("color = " + actualValue, 0, 0);
			LCD.drawString("TS1 = " + this.robot.getPressureSensorLeft().isTouched(), 1, 0);
			LCD.drawString("TS1 = " + this.robot.getPressureSensorRight().isTouched(), 2, 0);
			
			float leftMotorSpeed = 0;
			float rightMotorSpeed = 0;
			
			
			//the touchsensors are touched and the robot has to drive around the obstacle
			if (robot.getPressureSensorLeft().isTouched() || robot.getPressureSensorRight().isTouched()) {
				driveAroundObstacle();
				
				
			} else if (actualValue > WHITE - 2 * OFFSET) { //90 degree turn
				
				// findLine() ausf�hren ist vermutlich besser.
				
				leftMotorSpeed = -1.2f * tempo;
				rightMotorSpeed = 1.2f * tempo;

				// adjust the robot's speed
				this.robot.adjustMotorspeed(leftMotorSpeed, rightMotorSpeed);
				
			} else if (actualValue < BLACK + OFFSET) { //LineGap
				// nur ausf�hren wenn der Robot nichts nach dem umschauen gefunden hat
				//muss sich eig nicht umschauen, da wenn pl�tzlich Schwarz wird, aufjedenfall L�cke kommt
				// also in findLine() ausf�hren
				findLine();
				
			} else { //normal case calculate the new speeds for both motors
				lastDifference = actualValue - OPTIMALVALUE;
				float y = kp * lastDifference;
				
				leftMotorSpeed = tempo - y;;
				rightMotorSpeed = tempo + y;
				
				//RobotMotorGeschwindigkeit anpassen mit den �bergebenen Werten left right Motor speed
				
				this.robot.adjustMotorspeed(leftMotorSpeed, rightMotorSpeed);
			}
		}
		robot.pilotStop();
	}

	public void driveAroundObstacle() {
		Sound.beepSequence();
	}
	
	public void findLineAfterGap() {
		Sound.beepSequence();
	}
	
	
	public void findLine() {
		Sound.beep();
		LCD.clear();
		LCD.drawString("Find Line", 0, 0);
		this.robot.pilotStop();
		
		boolean found = false;
		while (!found) {
			this.robot.pilotTravel(9);
			int arc = 0;
			while (arc < 90 && !found) {
				this.robot.RotateRight(10);
				found = this.robot.getColorSensor().getColor()[0] > BLACK + 2 * OFFSET;
				arc += 10;
			}
			if (!found)
				this.robot.RotateLeft(arc);
		}
		
		this.robot.forward();
	}
	
}
