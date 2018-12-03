package mission;

import robot.RegulatorP;
import robot.Robot;
import lejos.hardware.Button;
import java.util.Timer;
import java.util.TimerTask;

public class RouteNavigator implements Mission {

	private final Robot robot;

	/*
	 * the final color values of the colors
	 */
	private final float BLACK = 0.08f;
	private final float WHITE = 0.48f;
	private final float OFFSET = 0.01f;
	private final float OPTIMALVALUE = (WHITE + BLACK) / 2;

	private final float tempo = 200f; //150

	/*
	 * the constant for the p-controller 1200
	 */
	private final float kp = 1500;

	/*
	 * constructs a new route navigator
	 */
	public RouteNavigator(Robot robot) {
		this.robot = robot;
	}

	@Override
	public boolean executeMission() {
		this.robot.beep();
		Timer timer = new Timer();
		this.robot.getColorSensor().setRedMode();
		RegulatorP regulator = new RegulatorP(this.robot, this.tempo, this.kp, this.OPTIMALVALUE);

		//this.robot.forward();
		boolean end = false;
		boolean afterBox = false;
		boolean findLine = false;
		int cnt = 0;
		boolean fst = true;
		
		robot.adjustMotorspeed(tempo, tempo);

		while (Button.LEFT.isUp() && !end) {
			float actualColorValue = robot.getColorSensor().getColor()[0];

			//this.robot.drawString(" color: " + actualColorValue, 0, 0);
			robot.clearLCD();

			// the touchsensors are touched and the robot has to drive around
			// the obstacle
			if (this.robot.getPressureSensorLeft().isTouched() || this.robot.getPressureSensorRight().isTouched()) {
				robot.pilotStop();
				robot.motorsStop();
				driveAroundObstacle();
				afterBox = true;

			} else if (actualColorValue < BLACK + 0.01f) { // find line
				robot.adjustMotorspeed(200, -66);
				if (fst) {
					robot.beep();
					timer.schedule(new FindLineTask(), 2000);
					fst = false;
				}
				
				if (actualColorValue >= BLACK + 0.01f) {
					timer.cancel();
				}
				
				/*
				robot.pilotStop();
				robot.motorsStop();
				if (!afterBox || cnt < 1) {
					findLine();
					findLine = true;
					if (afterBox) {
						cnt++;
					}
					robot.beep();
				} else {
					this.robot.drawString("drive to next mission", 0, 0);
					driveToNextMission();
					end = true;
				}
				*/
			} else { // normal case calculate the new speeds for both motors
				regulator.regulate(actualColorValue);
				fst = true;
			}
			// after f.e findGab switch to rgb mode to find the end of the line
			// with the blue strip
		}
		robot.pilotStop();
		return end;
	}
		
	
	
	private class FindLineTask extends TimerTask {

		@Override
		public void run() {
			
			findLine();
		}
		
	}

	/**
	 * the robot drives around the obstacle
	 */
	public void driveAroundObstacle() {

		float OPTIMALVALUE = 0.06f; //0.1
		float actualSonicValue = 0.0f;
		float actualColorValue = 0.0f;

		float kpSonic = 2500;

		// enter()
		this.robot.motorsStop();
		this.robot.pilotStop();
		this.robot.clearLCD();
		this.robot.beepSequence();
		this.robot.drawString("Block umfahren", 0, 0);
		RegulatorP regulator = new RegulatorP(this.robot, this.tempo + 150, kpSonic, OPTIMALVALUE);

		// start()
		this.robot.pilotTravel(-3);
		this.robot.RotateRight(450); // 90 Grad

		// Regulator start
		this.robot.forward();
		actualColorValue = this.robot.getColorSensor().getColor()[0];

		while (actualColorValue < WHITE - 2 * OFFSET) {

			actualSonicValue = this.robot.getUltraSonicSensor().getDistance();

			this.robot.drawString("Abstand: " + actualSonicValue, 0, 0);
			
			regulator.sonicRegulate(actualSonicValue);

			actualColorValue = this.robot.getColorSensor().getColor()[0];
			this.robot.drawString("Lichtwert: " + actualColorValue, 0, 10);
		}

		// end
		this.robot.drawString("Ende", 0, 0);
	}

	/*
	 * the robot searches for the line
	 */
	public void findLine() {
		this.robot.drawString("Find Line", 0, 0);
		this.robot.beep();
		this.robot.pilotStop();
		this.robot.motorsStop();
		this.robot.RotateLeft(560);
		this.robot.pilotTravel(8);
		float actualColor = this.robot.getColorSensor().getColor()[0];
		this.robot.adjustMotorspeed(200, -66);
		while (actualColor < BLACK + 0.01f) {
			actualColor = this.robot.getColorSensor().getColor()[0];
		}
		/*
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
				found = this.robot.getColorSensor().getColor()[0] > WHITE - 12 * OFFSET;
				arc += 40;
			}
			if (!found) {
				this.robot.RotateLeft(arc);
				this.robot.pilotTravel(1);
				arc = 0;
				while (arc <= 440 && !found) {
					this.robot.RotateLeft(40);
					found = this.robot.getColorSensor().getColor()[0] > WHITE - 12 * OFFSET;
					if (found) {
						fromRight = true;
					}
					arc += 40;
				}
			}
			if (!found) {
				this.robot.RotateRight(arc - 100);
				this.robot.drawString("Line not found", 0, 0);
				this.robot.pilotTravel(7);
			}
		}

		if (fromRight) {
			robot.RotateLeft(100);
		}
		this.robot.pilotStop();
		this.robot.motorsStop();
		*/
	}
	
	public void driveToNextMission() {
		float OPTIMALVALUE = 0.04f;
		float actualSonicValue = 0.0f;
		float actualBlueValue = 0.0f; //Blue

		float kpSonic = 2200;

		this.robot.drawString("next mission", 0, 0);
		
		// enter()
		this.robot.motorsStop();
		this.robot.pilotStop();
		this.robot.clearLCD();
		this.robot.beepSequence();
		this.robot.beep();
		this.robot.adjustMotorspeed(300, 300);
		
		RegulatorP regulator = new RegulatorP(this.robot, 300f, kpSonic, OPTIMALVALUE);

		this.robot.getColorSensor().setColorIDMode();
		// Regulator start
		this.robot.RotateLeft(90);
		this.robot.forward();
		actualBlueValue = this.robot.getColorSensor().getColor()[0];

		while (!(actualBlueValue == 1 || actualBlueValue == 2)) {

			actualSonicValue = this.robot.getUltraSonicSensor().getDistance();
			this.robot.drawString("Abstand: " + actualSonicValue, 0, 10);
			this.robot.drawString("ColorValue: " + actualBlueValue, 0, 0);
			regulator.sonicRegulate(actualSonicValue);

			actualBlueValue = this.robot.getColorSensor().getColor()[0];
		}
	}
}
