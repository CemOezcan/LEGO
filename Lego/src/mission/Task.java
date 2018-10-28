package mission;

public enum Task {
	LineFollower("Line follow"), Bridge("Bridge"), PointFinder("Pointfinder"),
	ObstacleMoving("Obstacle moving");
	
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
		String[] list = {LineFollower.getName(), Bridge.getName(), PointFinder.getName(), ObstacleMoving.getName()};
		return list;
	}
}

