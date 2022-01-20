package files;

import files.gameui.UI;

import java.util.Random;
import java.util.Scanner;

public class Fn {

	static class UserInput {

		public static boolean readYesNoInput(String msg) {
			do {
				UI.print(msg);
				UI.print("Y/N");
				char c = new Scanner(System.in).next().charAt(0);
				if (c == 'Y') return true;
				if (c == 'N') return false;
				UI.print("Your input is invalid.");
			}
			while (true);
		}

		public static int readDigitInput(String msg, int min, int max) {
			do {
				UI.print(msg);
				int i = new Scanner(System.in).nextInt();
				if (i >= min && i <= max) {
					return i;
				}
				UI.print("Your input is invalid.");
			}
			while (true);
		}

		public static int[] readTwoDigitsInput(String msg, int min, int max) {
			do {
				UI.print(msg);
				Scanner sc = new Scanner(System.in);
				int i = sc.nextInt();
				int j = sc.nextInt();
				if (i != j && (i >= min && i <= max) && (j >= min && j <= max)) {
					return new int[]{i, j};
				}
				UI.print("Your inputs are invalid.");
			} while (true);
		}
	}

	static class Dice {

		public static int rollOne() {
			return new Random().nextInt(6) + 1;
		}

		public static int[] rollTwoAndShow() {
			int[] rolls = new int[]{rollOne(), rollOne()};
			UI.print(String.format("You rolled %d and %d.", rolls[0], rolls[1]));
			return rolls;
		}
	}

	static class Grid {

	}

	public static void main(String[] args) {}
}
