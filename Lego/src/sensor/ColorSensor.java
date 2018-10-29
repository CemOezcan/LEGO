package sensor;

import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.Color;

public class ColorSensor extends UARTSensor {
	
	private EV3ColorSensor sensor;
	private float[] sample;
	
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
	public void setColorIDMode() {
		this.sensor.setCurrentMode("ColorID");
		sample = new float[this.sensor.sampleSize()];
		//this.switchMode(this.sensor.getColorIDMode());
		this.sensor.setFloodlight(false);
	}
	
	/**
	 * Set sensor mode to "Red".
	 * This mode measures the brightness of reflected red light.
	 */
	public void setRedMode() {
		this.sensor.setCurrentMode("Red");
		sample = new float[this.sensor.sampleSize()];
		//this.switchMode(this.sensor.getRedMode());
		this.sensor.setFloodlight(Color.RED);
	}
	
	/**
	 * Set sensor mode to "RGB".
	 * This mode measures the brightness of reflected red, green and blue light.
	 */
	public void setRGBMode() {
		this.sensor.setCurrentMode("RGB");
		sample = new float[this.sensor.sampleSize()];
		//this.switchMode(this.sensor.getRGBMode());
		this.sensor.setFloodlight(Color.WHITE);
	}
	
	/**
	 * Set sensor mode to "Ambient".
	 * This mode measures the ambient light level.
	 */
	public void setAmbientMode() {
		this.sensor.setCurrentMode("Ambient");
		sample = new float[this.sensor.sampleSize()];
		//this.switchMode(this.sensor.getAmbientMode());
		this.sensor.setFloodlight(false);
	}
	
	/**
	 * Detected Values depend on sensor mode.
	 * @return array of numerical values, representing detected colors.
	 */
	public float[] getColor() {
		//float[] sample = new float[this.sensor.sampleSize()];
		this.sensor.fetchSample(this.sample, 0);
		return sample;
	}
	
}