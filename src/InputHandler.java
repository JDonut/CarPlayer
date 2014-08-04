/**
 * As the name implies, processes input from the keypad.
 *
 * @author James Moretti
 * @since July 26, 2014
 */
import java.io.Reader;

public class InputHandler {

	// Constants
	private final String[] TO_RAW = {"/bin/bash", "-c", "stty raw </dev/tty"};
	private final String[] TO_COOKED = {"/bin/bash", "-c", "stty cooked </dev/tty"};
	private final String QUIT_STRING = "quit";

	private PlayManager playManager;
	private Speaker speaker;
	private int bufpos;
	private char[] quitStringBuffer = new char[QUIT_STRING.length()];

	/**
	 * Constructor for InputHandler. Just initializes some objects.
	 */
	public InputHandler() {
		playManager = new PlayManager();
		speaker = new Speaker();
		bufpos = 0;
	}

	/**
	 * Puts the console in raw mode and continually polls for input until
	 * the quit string has been typed in. Since the target platform is a
	 * Raspberry pi with a number pad for input, under normal operation this
	 * loop will only be terminated when the device loses power.
	 */
	public void runInputLoop() {
		Reader reader = System.console().reader();

		// A few of these commands require their exceptions to be caught so
		// I just put this whole thing in a try block. You're gonna like it.
		try {
			// Put the console in raw input mode
			Runtime.getRuntime().exec(TO_RAW).waitFor();

			// Read each char from the console
			char curchar;
			while (!QUIT_STRING.equals(new String(quitStringBuffer))) {
				curchar = (char)reader.read();
				checkKillString(curchar);
				processInput(curchar);
			}

			// Put the console back into cooked input mode
			Runtime.getRuntime().exec(TO_COOKED).waitFor();
		}
		catch (Exception e) {
			System.err.println("Main loop done broked");
			e.printStackTrace();
		}
	}

	/**
	 * Checks to see if each character of the quit string has been typed
	 * consecutively, in order. Keeps a running count of how many characters
	 * have been matched. Sets it back to 0 if a character doesn't match.
	 */
	private void checkKillString(char c) {
		if (c == QUIT_STRING.charAt(bufpos)) {
			quitStringBuffer[bufpos] = c;
			bufpos++;
		}
		else bufpos = 0;
	}

	private void processInput(char c) {
	}

}
