package sensor;

import lejos.hardware.port.Port;
import lejos.hardware.sensor.BaseSensor;

public abstract class Sensor {
	
	protected Port port;
	
	public Sensor(Port port) {
		this.port = port;
	}
	
	/**
	 * Switch mode of the sensor
	 * @param sensor represented sensor
	 * @param mode new mode
	 */
	protected void switchMode(BaseSensor sensor, String mode) {
		sensor.setCurrentMode(mode);
	}
	
}
