/**
 * As the name implies, processes input from the keypad. Since the program
 * is entirely dependent on keypad input, this class holds the main method
 * and drives the rest of the program.
 *
 * @author James Moretti
 * @since July 26, 2014
 */
import java.io.Reader;

public class InputHandler {

	private PlayManager playManager;
	private final String[] TO_RAW = {"/bin/bash", "-c", "stty raw </dev/tty"};
	private final String[] TO_COOKED = {"/bin/bash", "-c", "stty cooked </dev/tty"};
	private final String QUIT_STRING = "quit";
	private int bufPos = 0;
	private char[] quitStringBuffer = new char[QUIT_STRING.length()];

	public static void main(String[] args) {
		
	}

	private void getInput() {
	}

	private void processInput(char c) {
	}

	private void generateTrackLists() {
	}
}
