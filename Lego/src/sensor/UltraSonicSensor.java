package sensor;

import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3UltrasonicSensor;

public class UltraSonicSensor extends AnalogSensor {
	
	private EV3UltrasonicSensor sensor;

	public UltraSonicSensor(Port port) {
		super(port);
		this.sensor = new EV3UltrasonicSensor(this.port);
	}

}
