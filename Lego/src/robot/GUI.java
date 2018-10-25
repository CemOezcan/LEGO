package robot;

import lejos.utility.TextMenu;
import mission.Task;

public class GUI extends TextMenu{
	
	public GUI() {
		super(Task.getTaskList());
	}
	
	public void showMenu() {
		
	}

}
