/**
 * Responsible for playing tracks from a PlayList.
 *
 * @author James Moretti
 * @since July 26, 2014
 */
import java.util.concurrent.ConcurrentLinkedQueue;

public class PlayManager implements Runnable {
	private PlayList curList;
	private Mpg123Proxy player;
	private Thread playThread;
	private int curTrackNum = 0;
	private boolean shuffle = false;
	private ConcurrentLinkedQueue<Cmd> commands;

	///////////////////////////////
	// Any command the PlayManager is supposed to execute, such as play
	// or stop, is wrapped in one of these mini classes. It is then
	// added to a thread-safe queue that the run method pulls from.
	// The whole goal here is to make the PlayManager more thread-safe.
	///////////////////////////////

	// Parent class for any command
	private abstract class Cmd {
		protected abstract void exec();
	}

	// Command to start playing the current track
	private class PlayCmd extends Cmd {
		protected void exec() {
			player.play(curList.getTrackPath(curTrackNum));
		}
	}

	// Command to stop playing a track
	private class StopCmd extends Cmd {
		protected void exec() {
			player.stop();
			player.waitFor();
		}
	}

	// Command to go forward one track
	private class NextCmd extends Cmd {
		protected void exec() {
			curTrackNum++;
			if (curTrackNum > curList.size() - 1)
				curTrackNum = 0;
		}
	}

	// Command to go back one track
	private class BackCmd extends Cmd {
		protected void exec() {
			curTrackNum--;
			if (curTrackNum < 0)
				curTrackNum = curList.size() - 1;
		}
	}

	// Command to change internal current track
	private class GotoCmd extends Cmd {
		private int track;
		private GotoCmd(int t) {
			track = t;
		}
		protected void exec() {
			curTrackNum = track;
		}
	}


	/**
	* Constructor for PlayManager. This just instantiates fields.
	*/
	public PlayManager() {
		curList = null;
		player = new Mpg123Proxy();
		commands = new ConcurrentLinkedQueue<Cmd>();
		playThread = new Thread(this);
		playThread.start();
	}

	/**
	* The separate thread the track is played in.
	* Thusly, this method is invoked by calling playThread.start().
	*/
	public void run() {
		while (!playThread.interrupted()) {
			Cmd curCmd = commands.poll();
			if (curCmd != null) {
				System.out.println("Ate cmd " + curCmd);
				curCmd.exec();
			}
		}

		System.out.println("Thread " + Thread.currentThread() + " ending");
	}

	/**
	* Gives the Player a PlayList to play. This will destroy any unprocessed
	* commands and stop any currently playing tracks.
	* @param   list - PlayList to play
	*/
	public void setPlayList(PlayList list) {
		commands = new ConcurrentLinkedQueue<Cmd>();
		player.stop();
		curTrackNum = 0;
		curList = list;
	}

	/**
	* Stops playing the current track and interrupts the playing thread.
	* This is called when we're exiting the program.
	*/
	public void shutdown() {
		playThread.interrupt();
		player.stop();
	}

	/**
	* Queues up command to start playing the selected track. Bounds checking
	* is performed. Any number out of range will be changed to the closest
	* existing track number.
	* @param   track - Track number to play.
	*/
	public void play(int track) {
		if (curList != null) {
			// It's thread-safe to check the bounds here instead of in the
			// command object because the playlist doesn't change.
			if (track < 0)
				track = 0;
			if (track > curList.size() - 1)
				track = curList.size() - 1;

			commands.add(new GotoCmd(track));
			commands.add(new PlayCmd());
		}
	}

	/**
	* Queues up command to start playing on whatever the internal track is.
	*/
	public void play() {
		if (curList != null) {
			commands.add(new PlayCmd());
		}
	}

	/**
	* Queues up command to stop playing the current track.
	*/
	public void stop() {
		if (curList != null) {
			commands.add(new StopCmd());
		}
	}

	/**
	* Queues up command to retreat to last track. Bounds checking is
	* performed. The current track will wrap around to the last track
	* when reaching the start of the PlayList.
	*/
	public void back() {
		if (curList != null) {
			commands.add(new StopCmd());
			commands.add(new BackCmd());
			commands.add(new PlayCmd());
		}
	}

	/**
	* Queues up command to advance to next track. Bounds checking is
	* performed. The current track will wrap around to the first track
	* when reaching the end of the PlayList.
	*/
	public void next() {
		if (curList != null) {
			commands.add(new StopCmd());
			commands.add(new NextCmd());
			commands.add(new PlayCmd());
		}
	}
}
