/**
 * As the name implies, processes input from the keypad.
 *
 * @author James Moretti
 * @since July 26, 2014
 */
import java.io.Reader;

public class InputHandler {

	// Enums
	private enum InputState {
		WAITING, QUEUEING, PLAYING
	}

	// Constants
	private final String[] TO_RAW = {"/bin/bash", "-c", "stty raw </dev/tty"};
	private final String[] TO_COOKED = {"/bin/bash", "-c", "stty cooked </dev/tty"};
	private final String QUIT_STRING = "quit";

	private PlayManager playManager;
	private Speaker speaker;
	private InputState mode;
	private int bufpos;
	private char[] quitStringBuffer = new char[QUIT_STRING.length()];
	private String playListSelection = "";

	/**
	 * Constructor for InputHandler. Just initializes some objects.
	 */
	public InputHandler() {
		playManager = new PlayManager();
		speaker = new Speaker();
		bufpos = 0;
		mode = InputState.WAITING;
	}

	/**
	 * Puts the console in raw mode and continually polls for input until
	 * the quit string has been typed in. Since the target platform is a
	 * Raspberry pi with a number pad for input, under normal operation this
	 * loop will only be terminated when the device loses power.
	 */
	public void runInputLoop() {
		speaker.speak("Awaiting input");
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
		}
		catch (Exception e) {
			System.err.println("Main loop done broked");
			e.printStackTrace();
		}
		finally {
			// Put the console back into cooked input mode
			try {
				Runtime.getRuntime().exec(TO_COOKED).waitFor();
			}
			catch (Exception e2) {
				System.err.println("Crashed reseting terminal");
				e2.printStackTrace();
			}
		}
	}

	/**
	 * Checks to see if each character of the quit string has been typed
	 * consecutively, in order. Keeps a running count of how many characters
	 * have been matched. Sets it back to 0 if a character doesn't match.
	 *
	 * @param	c	character to check.
	 */
	private void checkKillString(char c) {
		if (c == QUIT_STRING.charAt(bufpos)) {
			quitStringBuffer[bufpos] = c;
			bufpos++;
		}
		else bufpos = 0;
	}

	/**
	 * Checks the given character against a list of commands.
	 * Executes matched command.
	 *
	 * @param	c	character to process.
	 */
	private void processInput(char c) {
		// This is really bad. I'm sorry.
		switch (c) {
			case '+': playManager.next(); break;
			case '-': playManager.back(); break;
			case '*': System.out.print("[Shuffle]"); break;
			case '/': System.out.print("[Linear]"); break;
			case '\r':
				if (mode == InputState.QUEUEING) {
					// Turn individual keystrokes into a number
					int num = Integer.parseInt(playListSelection);
					playListSelection = ""; // Reset input number
					playManager.setPlayList(new PlayList(num));
					playManager.play();
					mode = InputState.PLAYING;
				}
				break;
			case '.':
				if (mode == InputState.PLAYING) {
					playManager.stop();
					playManager.setPlayList(null);
					mode = InputState.WAITING;
				}
				break;
			default:
				if (charIsDigit(c)) {
					if (mode == InputState.WAITING)
						mode = InputState.QUEUEING;
					// Append numbers to string to make an int later
					playListSelection += c;
				}
				break;
		}
	}

	/**
	 * Checks if a the given character is a digit.
	 * @param	c	character to test.
	 * @return	true is character is a digit, false if not.
	 */
	private boolean charIsDigit(char c) {
		return c == '0' || c == '1' || c == '2' || c == '3' || c == '4' ||
				c == '5' || c == '6' || c == '7' || c == '8' || c == '9';
	}
}
