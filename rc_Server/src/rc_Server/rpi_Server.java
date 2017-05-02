package rc_Server;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import java.net.ServerSocket;
import java.net.Socket;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GPIOFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;

public class rpi_Server {
	
	public static final byte forward_GO   = 10;
	public static final byte reverse_GO   = 11;
	public static final byte right_GO     = 12;
	public static final byte left_GO      = 13;
	
	public static final byte forward_STOP = 20;
	public static final byte reverse_STOP = 21;
	public static final byte right_STOP   = 22;
	public static final byte left_STOP    = 23;
	
	public static void main(String [] args) throws IOException {
		
		final GpioController       gpio        = GpioFactory.getInstance();
		final GpioPinDigitalOutput forward_pin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_12, "Forward", PinState.LOW);
		final GpioPinDigitalOutput reverse_pin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_06, "reverse", PinState.LOW);
		final GpioPinDigitalOutput left_pin    = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_04, "left", PinState.LOW);
		final GpioPinDigitalOutput right_pin   = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_01, "right", PinState.LOW);
		
		ServerSocket listener = new ServerSocket(15000);
		try{
			while(true){
				Socket socket = listener.accept();
				try{
					DataInputStream dis = new DataInputStream(socket.getInputStream());
					boolean done = false;
					while(!done){
						byte msg_Rec = dis.readByte();
						switch(msg_Rec){
						case forward_GO:
							forward_pin.high();
							break;
							
						case reverse_GO:
							reverse_pin.high();
							break;
							
						case right_GO:
							right_pin.high();
							break;
							
						case left_GO:
							left_pin.high();
							break;
							
						case forward_STOP:
							forward_pin.low();
							break;
							
						case reverse_STOP:
							reverse_pin.low();
							break;
							
						case right_STOP:
							right_pin.low();
							break;
							
						case left_STOP:
							left_pin.low();
							break;
						}
					}
					
					
				}
				finally{
					
					socket.close();
				}
			}
			
		}
		finally{
			listener.close();
			gpio.shutdown();
		}
		
	}
	
}
