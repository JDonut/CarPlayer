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
		curList = list;
	}

	/**
	 * Tells our player to start playing the selected track.
	 *
	 * @param	trackNum	Number of the track to play
	 */
	public void play() {
		if (curList != null) {
			player.play(curList.getTrackName(curTrackNum));
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
