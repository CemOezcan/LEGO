package sensor;

import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3ColorSensor;

public class ColorSensor extends Sensor {
	
	private EV3ColorSensor sensor;
	
	public ColorSensor(Port port) {
		super(port);
		this.sensor = new EV3ColorSensor(this.port);
	}

	@Override
	protected void switchMode(int mode) {
		this.sensor.setCurrentMode(mode);
	}
	
}