package robot;

import lejos.hardware.Sound;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.Port;
import lejos.hardware.port.SensorPort;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.navigation.DifferentialPilot;
import sensor.ColorSensor;
import sensor.TouchSensor;
import sensor.UltraSonicSensor;

public class Robot {

	private DifferentialPilot pilot;

	private double travelSpeed = 4;
	private double rotateSpeed = 4;

	private final Port MOTOR_LEFT = MotorPort.D;
	private final Port MOTOR_RIGHT = MotorPort.C;
	private final Port MOTOR_ULTRA_SONIC = MotorPort.B;

	private final Port SENSOR_COLOR = SensorPort.S2;
	private final Port SENSOR_ULTRA_SONIC = SensorPort.S3;
	private final Port SENSOR_TOUCH_LEFT = SensorPort.S4;
	private final Port SENSOR_TOUCH_RIGHT = SensorPort.S1;

	private ColorSensor colorSensor;
	private UltraSonicSensor ultraSonicSensor;
	private TouchSensor touchSensorLeft;
	private TouchSensor touchSensorRight;

	private EV3LargeRegulatedMotor motorRight = new EV3LargeRegulatedMotor(MOTOR_RIGHT);
	private EV3LargeRegulatedMotor motorLeft = new EV3LargeRegulatedMotor(MOTOR_LEFT);
	private EV3MediumRegulatedMotor motorUltraSonic = new EV3MediumRegulatedMotor(MOTOR_ULTRA_SONIC);

	private boolean motorUltraSonicIsUp = true;

	public Robot() {
		// TODO: real values
		double rightWheel = 1.1811;
		double leftWheel = 1.1811;
		boolean reverse = true;
		this.pilot = new DifferentialPilot(leftWheel, rightWheel, this.motorLeft, this.motorRight, reverse);
		this.pilot.setTravelSpeed(this.travelSpeed);
		this.pilot.setRotateSpeed(this.rotateSpeed);

		this.colorSensor = new ColorSensor(this.SENSOR_COLOR);
		this.ultraSonicSensor = new UltraSonicSensor(this.SENSOR_ULTRA_SONIC);
		this.touchSensorLeft = new TouchSensor(this.SENSOR_TOUCH_LEFT);
		this.touchSensorRight = new TouchSensor(this.SENSOR_TOUCH_RIGHT);
	}
	
	public void setTravelSpeed(float travelSpeed) {
		this.travelSpeed = travelSpeed;
		this.pilot.setTravelSpeed(this.travelSpeed);
	}
	
	public void setRotateSpeed(float rotateSpeed) {
		this.rotateSpeed = rotateSpeed;
		this.pilot.setRotateSpeed(this.rotateSpeed);
	}
	
	/**
	 * 
	 * @return the color sensor
	 */
	public ColorSensor getColorSensor() {
		return this.colorSensor;
	}
	
	/**
	 * 
	 * @return the ultrasonic sensor
	 */
	public UltraSonicSensor getUltraSonicSensor() {
		return this.ultraSonicSensor;
	}
	
	/**
	 * 
	 * @return the left touch sensor
	 */
	public TouchSensor getPressureSensorLeft() {
		return this.touchSensorLeft;
	}
	
	/**
	 * 
	 * @return the right touch sensor
	 */
	public TouchSensor getPressureSensorRight() {
		return this.touchSensorRight;
	}

	/**
	 * Robot starts driving forward.
	 */
	public void forward() {
		this.pilot.forward();
	}

	/**
	 * Robot starts driving forward.
	 */
	public void backward() {
		this.pilot.backward();
	}

	/**
	 * Robot turns left by 90 degrees with zero radius.
	 */
	public void turnLeft() {
		this.pilot.steer(200, 90);
	}

	/**
	 * Robot turns right by 90 degrees with zero radius.
	 */
	public void turnRight() {
		this.pilot.steer(200, -90);
	}

	/**
	 * Robot turns left with zero radius.
	 * 
	 * @param degree
	 *            turning degree
	 */
	public void RotateLeft(double degree) {
		this.pilot.steer(200, degree);
	}

	/**
	 * Robot turns right with zero radius.
	 * 
	 * @param degree
	 *            turning degree
	 */
	public void RotateRight(double degree) {
		this.pilot.steer(200, -degree);
	}

	/**
	 * Stops any movement.
	 */
	public void pilotStop() {
		this.pilot.stop();
	}

	/**
	 * the robot travels the distance
	 * 
	 * @param distance
	 *            the distance
	 */
	public void pilotTravel(double distance) {
		this.pilot.travel(distance);
	}

	/**
	 * stops the robot
	 */
	public void motorsStop() {
		this.motorLeft.stop(true);
		this.motorRight.stop(true);
	}

	/**
	 * adjust the speed of the robot
	 * 
	 * @param leftMotorSpeed
	 *            the new speed of the left motor
	 * @param rightMotorSpeed
	 *            the new speed of the right motor
	 */
	public void adjustMotorspeed(float leftMotorSpeed, float rightMotorSpeed) {
		int speedLeft = Math.abs(Math.round(leftMotorSpeed));
		int speedRight = Math.abs(Math.round(rightMotorSpeed));

		this.motorLeft.startSynchronization();
		this.motorRight.startSynchronization();

		if (rightMotorSpeed < 0) {
			this.motorRight.setSpeed(3 * speedRight);
			this.motorLeft.setSpeed(speedLeft);
			this.motorRight.forward();
			this.motorLeft.backward();
		} else if (leftMotorSpeed < 0) {
			this.motorRight.setSpeed(speedRight);
			this.motorLeft.setSpeed(3 * speedLeft);
			this.motorRight.backward();
			this.motorLeft.forward();
			this.motorLeft.backward();
		} else {
			this.motorLeft.setSpeed(speedLeft);
			this.motorRight.setSpeed(speedRight);

			this.motorRight.backward();
			this.motorLeft.backward();
		}

		this.motorLeft.endSynchronization();
		this.motorRight.endSynchronization();
	}

	/**
	 * Turns ultrasonic sensor to the left. Useful to navigate around obstacles.
	 */
	public void ultraSonicSensorUp() {
		if (!this.motorUltraSonicIsUp) {
			this.motorUltraSonic.rotateTo(-90);
			this.motorUltraSonicIsUp = true;
		}
	}

	/**
	 * Turns ultrasonic sensor so that it faces down. Useful to measure the
	 * distance to the ground.
	 */
	public void ultraSonicSensorDown() {
		if (this.motorUltraSonicIsUp) {
			this.motorUltraSonic.rotate(90);
			this.motorUltraSonicIsUp = false;
		}
		
	}

	/**
	 * Prints a message on the LCD display
	 * @param message the message
	 * @param x - position
	 * @param y y position
	 */
	public void drawString(String message, int x, int y) {
		LCD.drawString(message, x, y);
	}

	/**
	 * Clears the LCD display
	 */
	public void clearLCD() {
		LCD.clear();
	}

	/**
	 * Makes a beep sound
	 */
	public void beep() {
		Sound.beep();
	}

	/**
	 * Makes a beepsquence sound
	 */
	public void beepSequence() {
		Sound.beepSequence();
	}
	
	public RegulatedMotor getMotorLeft() {
		return this.motorLeft;
	}
	
	public RegulatedMotor getMotorRight() {
		return this.motorRight;
	}

	private boolean almostEqual(int value1, int value2) {
		if ((value1 + 30 >= value2) && (value2 + 30 >= value1)) {
			return true;
		}
		return false;
	}
	
}
