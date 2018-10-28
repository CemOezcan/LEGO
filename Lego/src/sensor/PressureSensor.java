package sensor;

import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3TouchSensor;

public class PressureSensor extends Sensor {
	
	private EV3TouchSensor sensor;

	public PressureSensor(Port port) {
		super(port);
		this.sensor = new EV3TouchSensor(this.port);
	}
	
}
