package sensor;

import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.Color;

public class ColorSensor extends UARTSensor {
	
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
	public void setColorIDMode() {
		this.sensor.setCurrentMode("ColorID");
		this.sensor.setFloodlight(false);
		
	}
	
	/**
	 * Set sensor mode to "Red".
	 * This mode measures the brightness of reflected red light.
	 */
	public void setRedMode() {
		this.sensor.setCurrentMode("Red");
		this.sensor.setFloodlight(Color.RED);
		
	}
	
	/**
	 * Set sensor mode to "RGB".
	 * This mode measures the brightness of reflected red, green and blue light.
	 */
	public void setRGBMode() {
		this.sensor.setCurrentMode("RGB");
		this.sensor.setFloodlight(Color.WHITE);
		
	}
	
	/**
	 * Set sensor mode to "Ambient".
	 * This mode measures the ambient light level.
	 */
	public void setAmbientMode() {
		this.sensor.setCurrentMode("Ambient");
		this.sensor.setFloodlight(false);
		
	}
	
	/**
	 * Detected Values depend on sensor mode.
	 * @return array of numerical values, representing detected colors.
	 */
	public float[] getColor() {
		return this.getSample();
		
	}

	@Override
	protected void switchMode(String newSensorMode) {
		this.sensorMode = newSensorMode;
		this.sensor.setCurrentMode(this.sensorMode);
		this.update();
		
	}

	@Override
	protected void update() {
		this.sample = new float[this.sensor.sampleSize()];
		this.sensor.fetchSample(this.sample, 0);
		
	}
	
}