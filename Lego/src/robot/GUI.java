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
	
	private RouteNavigator routeNavigator;
	private BridgeCrosser bridgeCrosser;
	private BoxShifter boxShifter;
	private MazeRunner mazeRunner;
	private TreasureHunter tresureHunter;
	
	public GUI() {
		super(Task.getTaskList());
		
		routeNavigator = new RouteNavigator();
		bridgeCrosser = new BridgeCrosser();
		boxShifter = new BoxShifter();
		mazeRunner = new MazeRunner();
		tresureHunter = new TreasureHunter();
	}
	
	public void showMenu() {
		while (Button.ESCAPE.isUp()) {
			
			lcd.clear();
			
			switch(this.select()) {
			case 1:
				
			}
		}
	}

}
