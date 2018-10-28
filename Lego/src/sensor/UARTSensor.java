package sensor;

import lejos.hardware.port.Port;
import lejos.hardware.sensor.SensorMode;

public class UARTSensor extends Sensor {
	
	protected SensorMode sensorMode;

	public UARTSensor(Port port) {
		super(port);
	}

	@Override
	protected void switchMode(SensorMode newSensorMode) {
		this.sensorMode = newSensorMode;		
	}

}
