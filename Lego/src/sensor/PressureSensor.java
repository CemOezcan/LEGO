package sensor;

import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3TouchSensor;

public class PressureSensor extends AnalogSensor {
	
	private EV3TouchSensor sensor;
	
	public PressureSensor(Port port) {
		super(port);
		
		this.sensor = new EV3TouchSensor(this.port);
		this.switchMode(this.sensor.getTouchMode());
	}
	
	/**
	 * 
	 * @return true, exactly when sensor detects touch 
	 */
	public boolean isTouched() {
		float[] sample = new float[this.sampleProvider.sampleSize()];
		this.sampleProvider.fetchSample(sample, 0);
		
		return (sample[0] == 0) ? (false) : (true);
	}
	
}
