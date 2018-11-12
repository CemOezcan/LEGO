package robot;

import lejos.hardware.Button;
import lejos.utility.TextMenu;
import mission.BoxShifter;
import mission.BridgeCrosser;
import mission.MazeRunner;
import mission.RouteNavigator;
import mission.Task;
import mission.RouteNavigator;
import mission.BoxShifter;
import mission.BridgeCrosser;
import mission.TreasureHunter;

public class GUI extends TextMenu{
	
	private final Robot robot;
	private RouteNavigator routeNavigator;
	private BridgeCrosser bridgeCrosser;
	private BoxShifter boxShifter;
	private MazeRunner mazeRunner;
	private TreasureHunter treasureHunter;
	
	public GUI(Robot robot) {
		super(Task.getTaskList());
		
		this.robot = robot;
		
		routeNavigator = new RouteNavigator(robot);
		bridgeCrosser = new BridgeCrosser(robot);
		boxShifter = new BoxShifter(robot);
		mazeRunner = new MazeRunner(robot);
		treasureHunter = new TreasureHunter(robot);
	}
	
	public void showMenu() {
		RouteNavigator routeNavigator = new RouteNavigator(robot);
		boolean previousMissionComplete = false;
		int previousMission = 0;
		
		while (Button.ESCAPE.isUp()) {
			String[] itemList = this.getItems();
			String nextMission = "";
			if (previousMissionComplete && previousMission < 4) {
				nextMission = itemList[previousMission + 1];
			} else {
				nextMission = itemList[this.select()];
			}
			
			switch(nextMission) {
			
			case "Route Navigator":
				lcd.clear();
				previousMissionComplete = routeNavigator.executeMission();
				previousMission = 0;
				break;

			case "Box Shifter":
				lcd.clear();
				previousMissionComplete = boxShifter.executeMission();
				previousMission = 1;
				break;
				
			case "Bridge Crosser":
				lcd.clear();
				previousMissionComplete = bridgeCrosser.executeMission();
				previousMission = 2;
				break;
				
			case "Treasure Hunter":
				lcd.clear();
				previousMissionComplete = treasureHunter.executeMission();
				previousMission = 3;
				break;
				
			case "Maze Runner":
				lcd.clear();
				previousMissionComplete = mazeRunner.executeMission();
				previousMission = 4;
				break;	
				
			default:
				break;
			}
		}
	}

}
