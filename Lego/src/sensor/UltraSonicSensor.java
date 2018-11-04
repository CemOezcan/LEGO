package sensor;

import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3UltrasonicSensor;

public class UltraSonicSensor extends AnalogSensor {
	
	private EV3UltrasonicSensor sensor;

	public UltraSonicSensor(Port port) {
		super(port);
		this.sensor = new EV3UltrasonicSensor(this.port);
		this.switchMode(this.sensor.getDistanceMode());
		
	}
	
	/**
	 * Calculates distance to any object the sensor is facing.
	 * @return the distance
	 */
	public float getDistance() {
		float[] value = this.getSample();
		return value[0];
		
	}

	@Override
	protected void update() {
		this.sample = new float[this.sensor.sampleSize()];
		this.sensor.fetchSample(this.sample, 0);
		
	}

}
