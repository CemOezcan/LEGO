package robot;

import lejos.hardware.Button;
import lejos.utility.TextMenu;
import mission.Task;

public class GUI extends TextMenu{
	
	public GUI() {
		super(Task.getTaskList());
	}
	
	public void showMenu() {
		while (Button.ESCAPE.isUp()) {
			
			lcd.clear();
			
			switch(this.select()) {
			
			}
		}
	}

}
