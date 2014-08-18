/**
* Provides a proxy for the system calls required to mp3s using mpg123.
* Obviously using this makes your program dependant on linux and mpg123.
*
* @author	James Moretti
* @since Aug 2, 2014
*/
public class Mpg123Proxy  {
	private Process sysCall;

	/**
	* Makes a system call to play the specified mp3 using mpg123.
	* @param	songPath - Relative path to the mp3 file to play.
	*/
	public void play(String trackPath) {
		stop(); // Only play one song at a time

		String s = "mpg123 \"" + trackPath + "\"";
		String[] cmd = {"/bin/bash", "-c", s};
		try {
			sysCall = Runtime.getRuntime().exec(cmd);
		}
		catch (Exception e) {
			System.err.println("Failed to play <" + trackPath + ">");
		}
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
			System.err.println("Mpg123Proxy broke while stopping track");
			e.printStackTrace();
		}
		finally {
			sysCall = null;
		}
	}

	/**
	* Reports whether or not a track is playing. Does not check if track
	* ended naturally. Only checks for manual stop.
	*
	* @return	true if track is playing, false if not.
	*/
	public boolean isPlaying() {
		if (sysCall != null) {
			try {
				sysCall.exitValue();
				sysCall = null; // Set this to null since we're not playing
				return false;
			}
			catch (IllegalThreadStateException e) {
				return true;
			}
		}
		else return false; // Can't be playing if sysCall is null
	}

	/**
	* Grants external classes the ability to wait for Mpg123Proxy to finish
	* playing a track.
	*/
	public void waitFor() {
		if (sysCall != null) {
			try {
				sysCall.waitFor();
			}
			catch (Exception e) {
				System.err.println("Mpg123Proxy external waitFor broke");
				e.printStackTrace();
			}
		}
	}
}
