/**
 * Loads in and contains play lists from file.
 *
 * @author	James Moretti
 * @since	July 26, 2014
 */
import java.io.File;

public class PlayList {
	private String[] tracks;
	private String listPath;

	public PlayList(int listNum) {
		loadPlayList(listNum);
	}

	/**
	 * Populates internal track name list using file names in the given
	 * play list directory.
	 *
	 * @param	listNum	Play list to load in
	 */
	public void loadPlayList(int listNum) {
		File f = new File("tracks/" + listNum);
		tracks = f.list();
		listPath = "tracks/" + listNum + "/";
	}

	/**
	 * Gets the track name at the given position in the tracks array.
	 *
 	 * @param	trackNum	Track number
	 * @return	Track name that corresponds to trackNum.
	 *			Gets last track if trackNum is out of range.
	 */
	public String getTrackName(int trackNum) {
		if (trackNum > tracks.length)
			return tracks[tracks.length];
		else if (trackNum < 0)
			return tracks[0];
		else
			return tracks[trackNum];
	}

	/**
	 * @return The size of the track list
	 */
	public int size() {
		return tracks.length;
	}

	/**
	 * #return The path to the play list
	 */
	public String getListPath() {
		return listPath;
	}
}
