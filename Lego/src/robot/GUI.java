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
	private TreasureHunter tresureHunter;
	
	public GUI(Robot robot) {
		super(Task.getTaskList());
		
		this.robot = robot;
		
		routeNavigator = new RouteNavigator(robot);
		bridgeCrosser = new BridgeCrosser(robot);
		boxShifter = new BoxShifter(robot);
		mazeRunner = new MazeRunner(robot);
		tresureHunter = new TreasureHunter(robot);
	}
	
	public void showMenu() {
		RouteNavigator routeNavigator = new RouteNavigator(robot);
		
		while (Button.ESCAPE.isUp()) {
			
			String[] itemList = this.getItems();
			
			switch(itemList[this.select()]) {
			
			case "Route Navigator":
				lcd.clear();
				routeNavigator.executeMission();
				break;

			case "Box Shifter":
				lcd.clear();
				boxShifter.executeMission();
				break;
				
			case "Bridge Crosser":
				lcd.clear();
				bridgeCrosser.executeMission();
				break;
				
			case "Treasure Hunter":
				lcd.clear();
				tresureHunter.executeMission();
				break;
				
			case "Maze Runner":
				lcd.clear();
				mazeRunner.executeMission();
				break;	
				
			default:
				break;
			}
		}
	}

}
