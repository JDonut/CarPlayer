/* James Moretti
 * July 26, 2014
 *
 * Speaker.java - Wrapper class for the UNIX text to speech functionality.
 */

public static class Speaker {
	/**
	* Makes a system call to play the given text using espeak
	*
	* @param text the string to be spoken
	*/
	public static void speak(String text) {
		String cmd = "espeak \"" + text + "\" 2> /dev/null";
		try {
			Runtime.getRuntime().exec(cmd);
		}
		catch(Exception e){
			//Compiler whines if we don't catch this exception
		}
	}
}
