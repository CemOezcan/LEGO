package sensor;

import lejos.hardware.port.Port;
import lejos.hardware.sensor.BaseSensor;

public abstract class Sensor {
	
	protected Port port;
	
	public Sensor(Port port) {
		this.port = port;
	}
	
	protected void switchMode(BaseSensor sensor, String mode) {
		sensor.setCurrentMode(mode);
	}
	
}
