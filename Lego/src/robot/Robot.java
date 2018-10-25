package robot;

import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.Port;
import lejos.hardware.port.SensorPort;
import lejos.robotics.RegulatedMotor;

public class Robot {

	private final Port MOTOR_LEFT = MotorPort.C;
	private final Port MOTOR_Right = MotorPort.D;
	private final Port MOTOR_ULTRA_SONIC = MotorPort.A;
	
	private final Port SENSOR_COLOR = SensorPort.S1;
	private final Port SENSOR_TOUCH_LEFT = SensorPort.S2;
	private final Port SENSOR_TOUCH_RIGHT = SensorPort.S3;
	private final Port SENSOR_ULTRA_SONIC = SensorPort.S4;
	
	
	RegulatedMotor motorRight = new EV3LargeRegulatedMotor(MOTOR_Right);
	RegulatedMotor motorLeft = new EV3LargeRegulatedMotor(MOTOR_LEFT);
	RegulatedMotor motorUltraSonic = new EV3MediumRegulatedMotor(MOTOR_LEFT);
	
	public Robot() {
		
	}
	
	public void forward() {
		
	}
	
	public void backward() {
		
	}
	
	public void turnLeft() {
		
	}
	
	public void turnRight() {
		
	}
	
	public void RotateLeft(int degree) {
		
	}
	
	public void RotateRight(int degree) {
		
	}
}
