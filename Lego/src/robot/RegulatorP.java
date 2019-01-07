package robot;

public class RegulatorP {

	private final Robot robot;

	private final float TEMPO;
	private float KP;
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

		this.robot.adjustMotorspeedFast(leftMotorSpeed, rightMotorSpeed);
	}
	
	public void sonicRegulate(float actualValue) {

		float y = KP * (actualValue - OPTIMALVALUE);

		// if value is = infinity -> turn right
		if (actualValue > 200) {
			y = -100;
		}
		
		if(y > 200) {
			y = 200.0f;
		} else if (y < - 200){
			y = -200.0f;
		}
		
		leftMotorSpeed = TEMPO - y;
		rightMotorSpeed = TEMPO + y;

		this.robot.adjustMotorspeedFast(leftMotorSpeed, rightMotorSpeed);
	}
	
	public void bridgeRegulate(float actualValue) {
		
		float y = KP * (actualValue - OPTIMALVALUE);

		leftMotorSpeed = TEMPO + y;
		rightMotorSpeed = TEMPO - y;

		this.robot.adjustMotorspeedFast(leftMotorSpeed, rightMotorSpeed);
	}
	
	public void setKpValue(float kp) {
		this.KP = kp;
	}
}
