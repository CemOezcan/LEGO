package sensor;

import lejos.hardware.port.Port;
import lejos.hardware.sensor.SensorMode;
import lejos.robotics.SampleProvider;

public class AnalogSensor extends Sensor {

	protected SampleProvider sampleProvider;
	
	public AnalogSensor(Port port) {
		super(port);
	}

	@Override
	protected void switchMode(SensorMode newSensorMode) {
		this.sampleProvider = (SampleProvider) newSensorMode;
	}
	
}
