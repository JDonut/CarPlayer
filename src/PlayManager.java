/**
 * Responsible for playing tracks from a PlayList.
 *
 * @author James Moretti
 * @since July 26, 2014
 */
public class PlayManager implements Runnable {
	private int curTrackNum;
	private PlayList curList;
	private Mpg123Proxy player;
	private boolean shuffle;
	private Thread playThread;

	/**
	 * Constructor for PlayManager. Defaults attributes.
	 */
	public PlayManager() {
		curTrackNum = 0;
		curList = null;
		shuffle = false;
		player = new Mpg123Proxy();
	}

	/**
	 * Gives the Player a PlayList to play.
	 *
	 * @param	list	PlayList to play
	 */
	public void setPlayList(PlayList list) {
		stop();
		curTrackNum = 0;
		curList = list;
	}

	/**
	 * Tells our player to start playing the selected track.
	 *
	 * @param	track	Track number to play.
	 */
	public void play(int track) {
		if (curList != null) {
			if (track > curList.size() - 1)
				track = curList.size() - 1;

			curTrackNum = track;
			play();
		}
	}

	/**
	 * Tells our player to start playing on whatever the internal track is.
	 *
	 * @param	trackNum	Number of the track to play
	 */
	public void play() {
		if (curList != null) {
			stop(); // Stop current playing

			playThread = new Thread(this);
			playThread.start();
		}
	}

	/**
	 * The separate thread the track is played in. Allows us to automatically
	 * continue to the next song when the current song ends.
	 * Thusly, this method is invoked by calling playThread.start().
	 */
	public void run() {
		while (!playThread.interrupted()) {
			player.play(curList.getTrackPath(curTrackNum));

			// Wait for current song to stop playing
			while (player.isPlaying()) {}

			curTrackNum++;
			if (curTrackNum > curList.size() - 1)
				curTrackNum = 0;
		}
	}

	/**
	 * Tells our player to stop playing the current track.
	 */
	public void stop() {
		player.stop();
		if (playThread != null) {
			playThread.interrupt();
			while (playThread.isAlive()) { }
		}
	}

	/**
	 * Retreats to last track. Loops back to last track at end of play list.
	 */
	public void back() {
		if (curList != null) {
			curTrackNum--;

			if (curTrackNum < 0)
				curTrackNum = curList.size() - 1;

			play();
		}
	}

	/**
	 * Advances to next track. Loops back to first track at end of play list.
	 */
	public void next() {
		if (curList != null) {
			curTrackNum++;

			if (curTrackNum == curList.size())
				curTrackNum = 0;

			play();
		}
	}
}

