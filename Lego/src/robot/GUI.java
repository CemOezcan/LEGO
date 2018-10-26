package robot;

import lejos.hardware.Button;
import lejos.utility.TextMenu;
import mission.Task;
import mission.RouteNavigator;
import mission.BoxShifter;
import mission.BridgeCrosser;
import mission.TreasureHunter;

public class GUI extends TextMenu{
	
	public static final int MENU_ITEM_LINE_FOLLOWING = 0;
			
	public GUI() {
		super(Task.getTaskList());
	}
	
	public void showMenu(Robot robot) {
		RouteNavigator routeNavigator = new RouteNavigator(robot);
		
		while (Button.ESCAPE.isUp()) {
			
			lcd.clear();
			
			switch(this.select()) {
			
			case GUI.MENU_ITEM_LINE_FOLLOWING: 
				lcd.clear();
				routeNavigator.start();
			
			
			}
		}
	}

}
