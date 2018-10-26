package robot;

import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.Port;
import lejos.hardware.port.SensorPort;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.navigation.DifferentialPilot;

public class Robot {
	
	private DifferentialPilot pilot;

	private final Port MOTOR_LEFT = MotorPort.C;
	private final Port MOTOR_Right = MotorPort.D;
	private final Port MOTOR_ULTRA_SONIC = MotorPort.A;
	
	private final Port SENSOR_COLOR = SensorPort.S1;
	private final Port SENSOR_TOUCH_LEFT = SensorPort.S2;
	private final Port SENSOR_TOUCH_RIGHT = SensorPort.S3;
	private final Port SENSOR_ULTRA_SONIC = SensorPort.S4;
	
	private RegulatedMotor motorRight = new EV3LargeRegulatedMotor(MOTOR_Right);
	private RegulatedMotor motorLeft = new EV3LargeRegulatedMotor(MOTOR_LEFT);
	private RegulatedMotor motorUltraSonic = new EV3MediumRegulatedMotor(MOTOR_LEFT);
	
	
	public Robot() {
		//TODO: real values
		double rightWheel = 0;
		double leftWheel = 0;
		double trackWidth = 0;
		boolean reverse = false;
		
		this.pilot = new DifferentialPilot(leftWheel, rightWheel, trackWidth, this.motorLeft, this.motorRight, reverse);
		
	}
	
	public void forward() {
		this.pilot.stop();
		this.pilot.forward();
	}
	
	public void backward() {
		this.pilot.stop();
		this.pilot.backward();
	}
	
	public void turnLeft() {
		this.pilot.stop();
		this.pilot.steer(200, 90);
	}
	
	public void turnRight() {
		this.pilot.stop();
		this.pilot.steer(-200, 90);
	}
	
	public void RotateLeft(double degree) {
		this.pilot.stop();
		this.pilot.steer(200, degree);
	}
	
	public void RotateRight(double degree) {
		this.pilot.stop();
		this.pilot.steer(-200, degree);
	}
	
}
