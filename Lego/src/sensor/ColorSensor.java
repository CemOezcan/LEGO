package sensor;

import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.Color;

public class ColorSensor extends Sensor {
	
	private EV3ColorSensor sensor;
	
	/**
	 * Creates new ColorSensor-Object, representing a EV3ColorSensor.
	 * @param port port of the represented sensor.
	 */
	public ColorSensor(Port port) {
		super(port);
		this.sensor = new EV3ColorSensor(this.port);
		this.setAmbientMode();
	}
	
	/**
	 * Set sensor mode to "ColorID". 
	 * This mode measures colors and returns a single numerical value.
	 */
	public void setClorIDMode() {
		this.switchMode(this.sensor, "ColorID");
		this.sensor.setFloodlight(true);
	}
	
	/**
	 * Set sensor mode to "Red".
	 * This mode measures the brightness of reflected red light.
	 */
	public void setRedMode() {
		this.switchMode(this.sensor, "Red");
		this.sensor.setFloodlight(Color.RED);
	}
	
	/**
	 * Set sensor mode to "RGB".
	 * This mode measures the brightness of reflected red, green and blue light.
	 */
	public void setRGBMode() {
		this.switchMode(this.sensor, "RGB");
		this.sensor.setFloodlight(Color.WHITE);
	}
	
	/**
	 * Set sensor mode to "Ambient".
	 * This mode measures the ambient light level.
	 */
	public void setAmbientMode() {
		this.switchMode(this.sensor, "Ambient");
		this.sensor.setFloodlight(false);
	}
	
	/**
	 * Detected Values depend on sensor mode.
	 * @return array of numerical values, representing detected colors.
	 */
	public float[] getColor() {
		float[] sample = new float[this.sensor.sampleSize()];
		this.sensor.fetchSample(sample, 0);
		return sample;
	}
	
}