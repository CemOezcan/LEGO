package sensor;

import lejos.hardware.port.Port;

public abstract class Sensor {
	
	protected Port port;
	protected float[] sample;
	
	public Sensor(Port port) {
		this.port = port;
	}
	
	/**
	 * Getter-method
	 * @return the sample
	 */
	protected float[] getSample() {
		this.update();
		return this.sample;
	}
	
	/**
	 * Update the sample.
	 */
	protected abstract void update();
	
}
