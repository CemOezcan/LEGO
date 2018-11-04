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
				
				// findLine() ausführen ist vermutlich besser.
				
				leftMotorSpeed = -1.2f * tempo;
				rightMotorSpeed = 1.2f * tempo;

				// adjust the robot's speed
				this.robot.adjustMotorspeed(leftMotorSpeed, rightMotorSpeed);
				
			} else if (actualValue < BLACK + OFFSET) { //LineGap
				// nur ausführen wenn der Robot nichts nach dem umschauen gefunden hat
				// also in findLine() ausführen
				findLineAfterGap();
				
			} else { //normal case calculate the new speeds for both motors
				lastDifference = actualValue - OPTIMALVALUE;
				float y = kp * lastDifference;
				
				leftMotorSpeed = tempo - y;;
				rightMotorSpeed = tempo + y;
				
				//RobotMotorGeschwindigkeit anpassen mit den übergebenen Werten left right Motor speed
				
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
		this.robot.pilotStop();
		
		// links/rechts schauen bis die Linie wieder gefunden wurde
		// wenn nichts gefunden wurde -> finLineAfterGab()
		
		this.robot.forward();
	}
	
}
