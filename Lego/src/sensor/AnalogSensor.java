package sensor;

import lejos.hardware.port.Port;
import lejos.hardware.sensor.SensorMode;
import lejos.robotics.SampleProvider;

public abstract class AnalogSensor extends Sensor {

	protected SampleProvider sampleProvider;
	
	public AnalogSensor(Port port) {
		super(port);
	}

	/**
	 * Switch current mode.
	 * @param newSensorMode the new mode
	 */
	protected void switchMode(SampleProvider newSampleProvider) {
		this.sampleProvider = newSampleProvider;
		this.update();
		
	}
	
}
