package sensor;

import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3IRSensor;

public class InfraRedSensor extends Sensor {
	
	private EV3IRSensor sensor;

	public InfraRedSensor(Port port) {
		super(port);
		this.sensor = new EV3IRSensor(this.port);
	}

}
