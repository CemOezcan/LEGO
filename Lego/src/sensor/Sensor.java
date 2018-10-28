package sensor;

import lejos.hardware.port.Port;
import lejos.hardware.sensor.SensorMode;

public abstract class Sensor {
	
	protected Port port;
	
	public Sensor(Port port) {
		this.port = port;
	}
	
	/**
	 * Switch current mode.
	 * @param newSensorMode the new mode
	 */
	protected abstract void switchMode(SensorMode newSensorMode);
	
}
