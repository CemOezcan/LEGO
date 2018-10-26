package sensor;

import lejos.hardware.port.Port;

public abstract class Sensor {
	
	protected Port port;
	
	public Sensor(Port port) {
		this.port = port;
	}
	
	protected abstract void switchMode(int mode);
	
}
