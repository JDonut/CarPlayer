/**
 * Responsible for playing tracks from a PlayList.
 *
 * @author James Moretti
 * @since July 26, 2014
 */
public class PlayManager {
	private int curTrackNum;
	private PlayList curList;
	private Mpg123Proxy player;
	private boolean shuffle;

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
			player.play(curList.getTrackPath(curTrackNum));
		}
	}

	/**
	 * Tells our player to stop playing the current track.
	 */
	public void stop() {
		player.stop();
	}

	/**
	 * Retreats to last track. Loops back to last track at end of play list.
	 */
	public void back() {
		if (curList != null) {
			curTrackNum--;

			if (curTrackNum < 0)
				curTrackNum = curList.size();

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

