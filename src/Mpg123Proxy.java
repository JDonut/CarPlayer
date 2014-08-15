/**
 * Provides a proxy for the system calls required to mp3s using mpg123.
 * Obviously using this makes your program dependant on linux.
 *
 * @author	James Moretti
 * @since Aug 2, 2014
 */
public class Mpg123Proxy  {
	private volatile Process sysCall;
	private boolean playing = false;

	/**
	 * Makes a system call to play the specified mp3 using mpg123.
	 *
	 * We make the system call in its own thread so we can wait for the system
	 * call to terminate while the rest of our program continues to run.
	 * This allows us to check the status of the process by seeing whether or
	 * not our reference to it is null.
	 *
	 * @param	songPath	Relative path to the mp3 file to play.
	 */
	public void play(final String trackPath) {
		stop(); // Only play one song at a time

		Thread t = new Thread(new Runnable() {
			public void run() {
				String s = "mpg123 \"" + trackPath + "\"";
				String[] cmd = {"/bin/bash", "-c", s};
				try {
					sysCall = Runtime.getRuntime().exec(cmd);
					sysCall.waitFor();
				}
				catch (Exception e) {
					System.out.println("Failed to play <" + trackPath + ">");
				}
				finally {
					sysCall = null;
					playing = false;
				}
			}
		});
		t.start();
		playing = true;
	}

	/**
	 * Stops playing an mp3 by killing the mpg123 process.
	 */
	public void stop() {
		try {
			if (sysCall != null)
				sysCall.destroy();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			playing = false;
		}
	}

	/**
	 * Reports whether or not a track is playing.
	 *
	 * @return	true if track is playing, false if not.
	 */
	public boolean isPlaying() {
		return playing;
	}
}
