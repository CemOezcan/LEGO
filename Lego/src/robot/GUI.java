package robot;

import lejos.hardware.Button;
import lejos.utility.TextMenu;
import mission.BoxShifter;
import mission.BridgeCrosser;
import mission.MazeRunner;
import mission.RouteNavigator;
import mission.Task;
import mission.TreasureHunter;
import mission.Task;

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
		
		routeNavigator = new RouteNavigator();
		bridgeCrosser = new BridgeCrosser();
		boxShifter = new BoxShifter();
		mazeRunner = new MazeRunner();
		tresureHunter = new TreasureHunter();
	}
	
	public void showMenu() {
		while (Button.ESCAPE.isUp()) {
			
			switch(this.select()) {
			
			case 1:		
				lcd.clear();
				break;
			
			case 2:
				break;
			
			case 3:
				break;
			
			case 4:
				break;
				
			default:
				break;
			}
		}
	}

}
