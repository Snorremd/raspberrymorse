/**
 * A simple morse program for Raspberry Pi.
 * Make sure you have a led light connected to the gpio-port 0
 * or change the port in the source code.
 * 
 * @author Snorre Magnus Davøen
 * @version 2013.03.15
 * 
 * License (Beerware):
 * The user is allowed to do anything with the licensed material.
 * Should the user of the product meet the author and consider the
 * software useful, he is encouraged to buy the author a beer
 * "in return" (or, in some variations, to drink a beer in the 
 * author's honor).
 */

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;

import javax.xml.stream.events.Characters;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;

/**
 * Class that makes morse code from text
 *
 */
public class MorsePi {

	private HashMap<String, LinkedList<Boolean>> characterConversions;
	private GpioController gpioController;
	private GpioPinDigitalOutput gpio0;

	public MorsePi(String[] words) {
		gpioController = GpioFactory.getInstance();
		gpio0 = gpioController.provisionDigitalOutputPin(RaspiPin.GPIO_00, "gpio0", PinState.LOW);

		// Make alphabet to morsecode dictionary:
		characterConversions = new HashMap<>(40);
		characterConversions.put("a", Arrays.asList(false, true});
		characterConversions.put("b", Arrays.asList(true, false, false, false);
		characterConversions.put("c", Arrays.asList(true, false, true, false));
       	        characterConversions.put("d", Arrays.asList(true, false, false));
                characterConversions.put("e", Arrays.asList(false));
                characterConversions.put("f", Arrays.asList(false, false, true, false));
                characterConversions.put("g", Arrays.asList(true, true, false));
                characterConversions.put("h", Arrays.asList(false, false, false, false));
                characterConversions.put("i", Arrays.asList(false, false));
                characterConversions.put("j", Arrays.asList(false, true, true, true));
                characterConversions.put("k", Arrays.asList(true, false, true));
                characterConversions.put("l", Arrays.asList(false, true, false, false));
                characterConversions.put("m", Arrays.asList(true, true));
                characterConversions.put("n", Arrays.asList(true, false));
                characterConversions.put("o", Arrays.asList(true, true, true));
                characterConversions.put("p", Arrays.asList(false, true, true, false));
                characterConversions.put("q", Arrays.asList(true, true, false, true));
                characterConversions.put("h", Arrays.asList(false, true, false));
                characterConversions.put("s", Arrays.asList(false, false, false));
                characterConversions.put("t", Arrays.asList(true));
                characterConversions.put("u", Arrays.asList(false, false, true));
                characterConversions.put("v", Arrays.asList(false, false, false, true));
                characterConversions.put("x", Arrays.asList(true, false, false, true));
                characterConversions.put("y", Arrays.asList(true, false, true, true));
                characterConversions.put("z", Arrays.asList(true, true, false, false));

		// For each word in input string
		for(String word : words) {
			try {
				morse(word);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		gpio0.setState(PinState.LOW);
		gpioController.shutdown();

	}

	/**
	 * Takes one string argument and outputs that text as morse code in light.
	 * Only accept a - z.
	 * @param args
	 */
	public static void main(String[] args) {

		String[] words = args[0].split(" ");
		new MorsePi(words);

	}

	public void morse(String word) throws InterruptedException {
		// Get each individual character in that word
		char letters[] = word.toCharArray();
		for(char letter : letters) {
			// Get the morse code if it exist
			LinkedList<Boolean> morseCode = characterConversions.get(Character.toString(letter));
			if(morseCode != null) {
				// Let there be light (short or long)
				for(boolean morse : morseCode) {
					if(morse) {
						gpio0.pulse(600);
						Thread.sleep(1100);

					} else {
						gpio0.pulse(250);
						Thread.sleep(750);
					}
				}
			}
			Thread.sleep(1500);
		}

	}
}
