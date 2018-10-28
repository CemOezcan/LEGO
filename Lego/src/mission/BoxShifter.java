package mission;

import robot.Robot;

public class BoxShifter implements Mission {

	private final Robot robot;
	
	public BoxShifter(Robot robot) {
		this.robot = robot;
	}
	
	@Override
	public void executeMission() {
		// TODO Auto-generated method stub
		for (int i = 0; i < 10; i--) {
			//Endlosschleife zum Testen des Abbruchs
		}
	}

}
