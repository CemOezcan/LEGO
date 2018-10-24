package main;
import lejos.hardware.lcd.LCD;
import lejos.hardware.port.SensorPort;
import lejos.utility.Delay;
import sensor.ColorSensor;

public class Main {
	
	public static void main(String[] args) {
		LCD.clearDisplay();
		LCD.drawString("Hello-World", 0, 0);
		//D = links 
		//C = rechts
		//A = Drehmotor
		//1 = Druck rechts
		//4 = Druck links
		//2 = infrarot
		//3 = ultraschall
		//Hallo
		ColorSensor color = new ColorSensor(SensorPort.S2);
		
		color.setColorIdMode();
		color.setFloodLight(false);
		int i = 10;
		while (i != 0) {
			LCD.clearDisplay();
			LCD.drawString("" + color.getColorID(), 0, 0);
			Delay.msDelay(1000);
		}
	}

}
