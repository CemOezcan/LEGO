package robot;

public class RegulatorP {

	private final Robot robot;
	
	private final float TEMPO;
	private final float KP;
	private final float OPTIMALVALUE;
	
	float leftMotorSpeed = 0;
	float rightMotorSpeed = 0;
	
	public RegulatorP(Robot robot, float tempo, float kp, float optimalValue) {
		this.robot = robot;
		this.TEMPO = tempo;
		this.KP = kp;
		this.OPTIMALVALUE = optimalValue;
	}
	
	public void regulate(float actualValue) {
		
		float y = KP * (actualValue - OPTIMALVALUE);
		
		leftMotorSpeed = TEMPO - y;
		rightMotorSpeed = TEMPO + y;
		
		this.robot.adjustMotorspeed(leftMotorSpeed, rightMotorSpeed);
	}
	
}
