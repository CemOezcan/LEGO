package robot;

public class RegulatorP {

	private final Robot robot;
	
	private final float TEMPO;
	private final float KP;
	private final float OPTIMALVALUE;
	private final float ACTUALVALUE;
	
	float leftMotorSpeed = 0;
	float rightMotorSpeed = 0;
	
	public RegulatorP(Robot robot, float tempo, float kp, float optimalValue, float actualValue) {
		this.robot = robot;
		this.TEMPO = tempo;
		this.KP = kp;
		this.OPTIMALVALUE = optimalValue;
		this.ACTUALVALUE = actualValue;
	}
	
	public void regulate() {
		
		float y = KP * (ACTUALVALUE - OPTIMALVALUE);
		
		leftMotorSpeed = TEMPO - y;
		rightMotorSpeed = TEMPO + y;
		
		this.robot.adjustMotorspeed(leftMotorSpeed, rightMotorSpeed);
	}
	
}
