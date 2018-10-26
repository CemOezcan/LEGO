package sensor;

import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3UltrasonicSensor;

public class UltraSonicSensor extends Sensor {
	
	private EV3UltrasonicSensor sensor;

	public UltraSonicSensor(Port port) {
		super(port);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void switchMode(int mode) {
		// TODO Auto-generated method stub
		
	}

}
