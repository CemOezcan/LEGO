package sensor;

import lejos.hardware.port.Port;
import lejos.hardware.sensor.SensorMode;
import lejos.robotics.SampleProvider;

public abstract class UARTSensor extends Sensor {
	
	protected String sensorMode;

	public UARTSensor(Port port) {
		super(port);
	}

	/**
	 * Switch current mode using its name.
	 * @param newSensorMode the new mode
	 */
	protected abstract void switchMode(String newSensorMode);

}
