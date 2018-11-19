package mission;

public enum Task {
	RouteNavigator("Route Navigator"), BoxShifter("Box Shifter"), BridgeCrosser("Bridge Crosser"), TreasureHunter("Treasure Hunter");
	
	private String name;
	
	private Task(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public static String[] getTaskList() {
		String[] taskList = new String[Task.values().length];
		int i = 0;
		for(Task task : Task.values()) {
			taskList[i] = task.getName();
			i++;
		}
		return taskList;
	}
}

