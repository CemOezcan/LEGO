package sensor;

import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3TouchSensor;

public class TouchSensor extends AnalogSensor {
	
	private EV3TouchSensor sensor;
	
	public TouchSensor(Port port) {
		super(port);
		this.sensor = new EV3TouchSensor(this.port);
		this.switchMode(this.sensor.getTouchMode());
		
	}
	
	/**
	 * 
	 * @return true, exactly when sensor detects touch 
	 */
	public boolean isTouched() {
		float[] value = this.getSample();
		return (value[0] == 0) ? (false) : (true);
		
	}

	@Override
	protected void update() {
		this.sample = new float[this.sampleProvider.sampleSize()];
		this.sampleProvider.fetchSample(this.sample, 0);
		
	}
	
}
