package mission;

import lejos.utility.TextMenu;
import mission.Task;

public class TaskMenu extends TextMenu {

	
	public TaskMenu() {
		super(Task.getTaskList());
	}
	
	
	public void showMenu() {
		
	}
	
}

