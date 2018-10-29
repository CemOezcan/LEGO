package robot;

import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.Port;
import lejos.hardware.port.SensorPort;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.navigation.DifferentialPilot;
import sensor.ColorSensor;
import sensor.PressureSensor;
import sensor.UltraSonicSensor;

public class Robot {
	
	private DifferentialPilot pilot;

	private final Port MOTOR_LEFT = MotorPort.D;
	private final Port MOTOR_RIGHT = MotorPort.C;
	private final Port MOTOR_ULTRA_SONIC = MotorPort.A;
	
	private final Port SENSOR_COLOR = SensorPort.S2;
	private final Port SENSOR_ULTRA_SONIC = SensorPort.S3;
	private final Port SENSOR_TOUCH_LEFT = SensorPort.S4;
	private final Port SENSOR_TOUCH_RIGHT = SensorPort.S1;
	
	private ColorSensor colorSensor;
	private UltraSonicSensor ultraSonicSensor;
	private PressureSensor pressureSensorLeft;
	private PressureSensor pressureSensorRight;
	
	private RegulatedMotor motorRight = new EV3LargeRegulatedMotor(MOTOR_RIGHT);
	private RegulatedMotor motorLeft = new EV3LargeRegulatedMotor(MOTOR_LEFT);
	private RegulatedMotor motorUltraSonic = new EV3MediumRegulatedMotor(MOTOR_ULTRA_SONIC);
	
	
	public Robot() {
		//TODO: real values
		double rightWheel = 0.3;
		double leftWheel = 0.3;
		boolean reverse = false;
		this.pilot = new DifferentialPilot(leftWheel, rightWheel, this.motorLeft, this.motorRight, reverse);
		
		this.colorSensor = new ColorSensor(this.SENSOR_COLOR);
		this.ultraSonicSensor = new UltraSonicSensor(this.SENSOR_ULTRA_SONIC);
		this.pressureSensorLeft = new PressureSensor(this.SENSOR_TOUCH_LEFT);
		this.pressureSensorRight = new PressureSensor(this.SENSOR_TOUCH_RIGHT);
		
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
	public PressureSensor getPressureSensorLeft() {
		return this.pressureSensorLeft;
	}
	
	/**
	 * 
	 * @return the right touch sensor
	 */
	public PressureSensor getPressureSensorRight() {
		return this.pressureSensorRight;
	}
	
	/**
	 * Robot starts driving forward.
	 */
	public void forward() {
		this.pilot.stop();
		this.pilot.forward();
	}
	
	/**
	 * Robot starts driving forward.
	 */
	public void backward() {
		this.pilot.stop();
		this.pilot.backward();
	}
	
	/**
	 * Robot turns left by 90 degrees with zero radius.
	 */
	public void turnLeft() {
		this.pilot.stop();
		this.pilot.steer(200, 90);
	}
	
	/**
	 * Robot turns right by 90 degrees with zero radius.
	 */
	public void turnRight() {
		this.pilot.stop();
		this.pilot.steer(-200, 90);
	}
	
	/**
	 * Robot turns left with zero radius.
	 * @param degree turning degree
	 */
	public void RotateLeft(double degree) {
		this.pilot.stop();
		this.pilot.steer(200, degree);
	}
	
	/**
	 * Robot turns right with zero radius.
	 * @param degree turning degree
	 */
	public void RotateRight(double degree) {
		this.pilot.stop();
		this.pilot.steer(-200, degree);
	}
	
}
