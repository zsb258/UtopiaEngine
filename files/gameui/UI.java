package files.gameui;

import java.util.concurrent.TimeUnit;

public class UI {
	public final static String SEP = System.lineSeparator();

	public static void print(String msg) {
		immediatePrint(msg);
		pause(500);
	}

	public static void fastPrint(String msg) {
		immediatePrint(msg);
		pause(100);
	}

	public static void immediatePrint(String msg) {
		System.out.println(msg);
	}

	public static void fPrintNum(String msg, int val) {
		print(String.format(msg, val));
	}

	public static void printGameFailed() {
		print("Doomsday has come.");
		print("You failed to build the Utopia Engine.");
		print("THE END");
	}

	public static void printSearchGrid(char[] grid) {
		String gridString = String.format("[%c] [%c] [%c]%n(%d) (%d) (%d)%n[%c] [%c] [%c]%n(%d) (%d) (%d)",
					grid[0], grid[1], grid[2], 1, 2, 3, grid[3], grid[4], grid[5], 4, 5, 6);
		immediatePrint(gridString);
	}

	public static void pause(int duration) {
		try {
			TimeUnit.MILLISECONDS.sleep(duration);
		}
		catch (InterruptedException ignored) {}
	}

	public static void main(String[] args) {
//		for (int i = 0; i < 10; i++) {
//			UI.fPrintNum("You have placed the digit %d", i);
//		}
	}
}
