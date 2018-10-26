package sensor;

import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3ColorSensor;

public class ColorSensor extends Sensor {
	
	private EV3ColorSensor sensor;
	
	public ColorSensor(Port port) {
		super(port);
		this.sensor = new EV3ColorSensor(this.port);
	}
	
	public void setClorIDMode() {
		this.switchMode(this.sensor, "ColorID");
	}
	
	public void setRedMode() {
		this.switchMode(this.sensor, "Red");
	}
	
	public void setRGBMode() {
		this.switchMode(this.sensor, "RGB");
	}
	
	public void setAmbientMode() {
		this.switchMode(this.sensor, "Ambient");
	}
	
}