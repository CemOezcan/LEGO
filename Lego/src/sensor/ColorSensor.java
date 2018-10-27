package sensor;

import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.Color;

public class ColorSensor extends Sensor {
	
	private EV3ColorSensor sensor;
	
	public ColorSensor(Port port) {
		super(port);
		this.sensor = new EV3ColorSensor(this.port);
		this.setAmbientMode();
	}
	
	public void setClorIDMode() {
		this.switchMode(this.sensor, "ColorID");
		this.sensor.setFloodlight(true);
	}
	
	public void setRedMode() {
		this.switchMode(this.sensor, "Red");
		this.sensor.setFloodlight(Color.RED);
	}
	
	public void setRGBMode() {
		this.switchMode(this.sensor, "RGB");
		this.sensor.setFloodlight(Color.WHITE);
	}
	
	public void setAmbientMode() {
		this.switchMode(this.sensor, "Ambient");
		this.sensor.setFloodlight(false);
	}
	
	public float[] getColor() {
		float[] sample = new float[this.sensor.sampleSize()];
		this.sensor.fetchSample(sample, 0);
		return sample;
	}
	
}