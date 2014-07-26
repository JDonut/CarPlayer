/**
 * Responsible for playing tracks from a PlayList.
 *
 * @author James Moretti
 * @since July 26, 2014
 */
public class Player {
	private int curTrack;
	private PlayList curList;
	private boolean shuffle;

	public void play(int track) {
	}

	public void pause() {
	}

	/**
	 * Makes a system call to stop playing the current track.
	 */
	public void stop() {
		
	}

	/**
	 * Retreats to last track. Loops back to last track at end of play list.
	 */
	public void back() {
		if (curTrack == 0)
			curTrack = curList.size();
		else if (curTrack > 0)
			curTrack--;

		play(curTrack);
	}

	/**
	 * Advances to next track. Loops back to first track at end of play list.
	 */
	public void next() {
		if (curTrack < curList.size())
			curTrack++;
		else if (curTrack == curList.size())
			curTrack = 1;

		play(curTrack);
	}
}
