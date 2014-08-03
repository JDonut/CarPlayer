/**
 * Provides a proxy for the system calls required to mp3s using mpg123.
 * Obviously using this makes your program dependant on linux.
 *
 * @author	James Moretti
 * @since Aug 2, 2014
 */
public class Mpg123Proxy implements Runnable {
	Process syscall;

	public void run() {
	}

	/**
	 * Makes a system call to play the specified mp3 using mpg123.
	 *
	 * @param	songPath	Relative path to the mp3 file to play.
	 */
	public void play(String songPath) {
		stop(); // Only play one song at a time

		String s = "mpg123 \"" + songPath + "\"";
		String[] cmd = {"/bin/bash", "-c", s};
		try {
			syscall = Runtime.getRuntime().exec(cmd);
		}
		catch (Exception e) {
			System.out.println("Failed to play <" + songPath + ">");
		}
	}

	/**
	 * Stops playing an mp3 by killing the mpg123 process.
	 */
	public void stop() {
		try {
			syscall.destroy();
		}
		catch (Exception e) {
			// stops compiler whining
		}
	}
}
