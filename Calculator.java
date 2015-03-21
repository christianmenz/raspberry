import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPin;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinDirection;
import com.pi4j.io.gpio.PinMode;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.gpio.trigger.GpioCallbackTrigger;
import com.pi4j.io.gpio.trigger.GpioPulseStateTrigger;
import com.pi4j.io.gpio.trigger.GpioSetStateTrigger;
import com.pi4j.io.gpio.trigger.GpioSyncStateTrigger;
import com.pi4j.io.gpio.event.GpioPinListener;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import com.pi4j.io.gpio.event.PinEventType;

/**
 * Einfache Ansteuerung einer 7-Segment-Anzeige
 * @author Christian Menz
 */
public class Calculator {

	private final static GpioController gpio = GpioFactory.getInstance();

	private static Pin[] allPins = {RaspiPin.GPIO_29, RaspiPin.GPIO_28, RaspiPin.GPIO_27, RaspiPin.GPIO_25, RaspiPin.GPIO_24, RaspiPin.GPIO_23, RaspiPin.GPIO_03};
	private static PinState[] OFF = {PinState.LOW, PinState.LOW, PinState.LOW, PinState.LOW, PinState.LOW, PinState.LOW, PinState.LOW};
	private static PinState[] ON = {PinState.HIGH, PinState.HIGH, PinState.HIGH, PinState.HIGH, PinState.HIGH, PinState.HIGH, PinState.HIGH};
	private static PinState[] ZERO = {PinState.HIGH, PinState.HIGH, PinState.HIGH, PinState.HIGH, PinState.HIGH, PinState.HIGH, PinState.LOW}; // done
	private static PinState[] ONE = {PinState.HIGH, PinState.LOW, PinState.LOW, PinState.HIGH, PinState.LOW, PinState.LOW, PinState.LOW}; // done
	private static PinState[] TWO = {PinState.LOW, PinState.HIGH, PinState.HIGH, PinState.HIGH, PinState.HIGH, PinState.LOW, PinState.HIGH}; // done
	private static PinState[] THREE = {PinState.HIGH, PinState.HIGH, PinState.LOW, PinState.HIGH, PinState.HIGH, PinState.LOW, PinState.HIGH}; // done
	private static PinState[] FOUR = {PinState.HIGH, PinState.LOW, PinState.LOW, PinState.HIGH, PinState.LOW, PinState.HIGH, PinState.HIGH}; // done
	private static PinState[] FIVE = {PinState.HIGH, PinState.HIGH, PinState.LOW, PinState.LOW, PinState.HIGH, PinState.HIGH, PinState.HIGH}; // done
	private static PinState[] SIX = {PinState.HIGH, PinState.HIGH, PinState.HIGH, PinState.LOW, PinState.HIGH, PinState.HIGH, PinState.HIGH}; // done
	private static PinState[] SEVEN = {PinState.HIGH, PinState.LOW, PinState.LOW, PinState.HIGH, PinState.HIGH, PinState.LOW, PinState.LOW}; // done
	private static PinState[] EIGHT = {PinState.HIGH, PinState.HIGH, PinState.HIGH, PinState.HIGH, PinState.HIGH, PinState.HIGH, PinState.HIGH}; // done
	private static PinState[] NINE = {PinState.HIGH, PinState.LOW, PinState.LOW, PinState.HIGH, PinState.HIGH, PinState.HIGH, PinState.HIGH}; // done

	private static PinState[][] NUMBERS = {ZERO, ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE};

	public static void setPins(PinState... state) {
		if (state.length != allPins.length) {
			throw new RuntimeException("Please...");
		}

		for (int i = 0; i < allPins.length; i++) {
			GpioPinDigitalOutput pin = gpio.provisionDigitalOutputPin(allPins[i]);	
			pin.setState(state[i]);
			gpio.unprovisionPin(pin);
		}
	}

	public static void main(String[] args) throws Exception{	

		setPins(OFF);
		//setPins(ON);

		for (int i = 0; i < 10; i++) {
			setPins(NUMBERS[i]);
			Thread.sleep(250);
		}


		// Test code for me to number the LEDs
		/*for (int i = 0; i < 7; i++) {
			System.out.println("Testing GPIO " + allPins[i]);
			setPins(OFF);
			GpioPinDigitalOutput pin = gpio.provisionDigitalOutputPin(allPins[i]);	
			pin.setState(PinState.HIGH);
			gpio.unprovisionPin(pin);
			System.console().readLine();
		}*/

		gpio.shutdown();
	}
}

