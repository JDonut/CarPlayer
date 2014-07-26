/**
 * Loads in and contains play lists from file.
 *
 * @author	James Moretti
 * @since	July 26, 2014
 */
import java.util.ArrayList;
import java.io.File;

public class PlayList {
	private String[] tracks;

	/**
	 * Populates internal track name list using file names in the given
	 * play list directory.
	 *
	 * @param	listNum	Play list to load in
	 */
	public void loadPlayList(int listNum) {
		File f = new File("./tracks/" + listNum);
		tracks = f.list();
	}

	/**
	 * Gets the track name at the given position in the tracks array.
	 *
 	 * @param	trackNum	Track number
	 * @return	Track name that corresponds to trackNum.
	 *			Gets last track if trackNum is out of range.
	 */
	public String getTrack(int trackNum) {
		if (trackNum > tracks.length)
			return tracks[tracks.length - 1];
		else
			return tracks[trackNum - 1];
	}
}
