/* A simple test to make sure the wrapper for the mpg123 system calls
 * starts and stops playing songs correctly
 */
public class MpgProxyTest {
	public static void main(String[] args) {
		Mpg123Proxy myprox = new Mpg123Proxy();
		System.out.println("File <" + args[0] + ">");

		myprox.stop();
		myprox.play(args[0]);

		try {
			Thread.sleep(5000);
		}
		catch (Exception e) {
			//stop compiler whining
		}

		myprox.stop();
	}
}
