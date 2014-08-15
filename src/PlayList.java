/**
 * Loads in and contains play lists from file.
 *
 * @author	James Moretti
 * @since	July 26, 2014
 */
import java.io.File;
import java.util.Arrays;

public class PlayList {
	private static final String TRACK_DIR = "tracks/";
	private String[] tracks;
	private String listPath;

	/**
	 * Find out if the given play list directory exists.
	 *
	 * @param	listNum	list directory to check
	 * @returns	true if directory exists, false if not.
	 */
	public static boolean exists(int listNum) {
		File f = new File(TRACK_DIR + listNum + "/");
		return f.exists();
	}

	public PlayList(int listNum) {
		tracks = new String[0];
		loadPlayList(listNum);
	}

	/**
	 * Populates internal track name list using file names in the given
	 * play list directory.
	 *
	 * @param	listNum	Play list to load in
	 */
	public void loadPlayList(int listNum) {
		File f = new File(TRACK_DIR + listNum);
		tracks = f.list();
		listPath = TRACK_DIR + listNum + "/";
		Arrays.sort(tracks);
	}

	/**
	 * Gets the track name at the given position in the tracks array.
	 *
 	 * @param	trackNum	Track number
	 * @return	Track name that corresponds to trackNum.
	 *			Gets last track if trackNum is out of range.
	 */
	public String getTrackPath(int trackNum) {
		if (tracks.length == 0) return ""; // Return early if empty play list

		if (trackNum > tracks.length - 1)
			return listPath + tracks[tracks.length - 1];
		else if (trackNum < 0)
			return listPath + tracks[0];
		else
			return listPath + tracks[trackNum];
	}

	/**
	 * @return The size of the track list
	 */
	public int size() {
		return tracks.length;
	}
}
