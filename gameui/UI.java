package gameui;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class UI {
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

	public static boolean readYesNoInput(String msg) {
		do {
			print(msg);
			print("Y/N");
			char c = new Scanner(System.in).next().charAt(0);
			if (c == 'Y') return true;
			if (c == 'N') return false;
			UI.print("Your input is invalid.");
		}
		while (true);
	}

	public static int readDigitInput(String msg, int min, int max) {
		do {
			print(msg);
			int i = new Scanner(System.in).nextInt();
			if (i >= min && i <= max) {
				return i;
			}
			UI.print("Your input is invalid.");
		}
		while (true);
	}
}
