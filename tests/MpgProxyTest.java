/* A simple test to make sure the wrapper for the mpg123 system calls
 * starts and stops playing songs correctly
 */
public class MpgProxyTest {
	public static void main(String[] args) {
		Mpg123Proxy myprox = new Mpg123Proxy();

		if (args.length != 2) {
			System.out.println("Expecting two different tracks for arguments");
		}

		System.out.println("File <" + args[0] + ">");

		myprox.stop();
		myprox.play(args[0]);

		try {
			Thread.sleep(3000);
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		myprox.stop();
		myprox.play(args[1]);

		try {
			Thread.sleep(3000);
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		myprox.stop();
	}
}
