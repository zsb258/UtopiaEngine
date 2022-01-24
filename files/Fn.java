package files;

import files.gameui.UI;

import java.util.Random;
import java.util.Scanner;

public class Fn {

	static class UserInput {
		/**
		 * Prints a message on terminal and asks user to enter one of two inputs: Y or N
		 * @param msg the message to print on terminal
		 * @return true if user input is "Y" and false if user input is "N"
		 */
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

		/**
		 * Prints a message on terminal
		 * and asks user to enter a single integer value in the range [min, max] (both inclusive).
		 * If user input is out of range, repeats above request until user gives an accepted value
		 * @param msg the message to print on terminal
		 * @param min specifies the minimum accepted value of user input
		 * @param max specifies the maximum accepted value of user input
		 * @return the accepted user input value
		 */
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

		/**
		 * Prints a message on terminal
		 * and asks user to enter two integer values in the range [min, max] (both inclusive).
		 * If any user input is out of range, repeats above request until user gives two accepted values
		 * @param msg the message to print on terminal
		 * @param min specifies the minimum accepted value of user input
		 * @param max specifies the maximum accepted value of user input
		 * @return array containing the two accepted user input values
		 */
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
		/**
		 * To simulate rolling a die once
		 * @return a random integer in the range [1, 6] (both inclusive)
		 */
		public static int rollOne() {
			return new Random().nextInt(6) + 1;
		}

		/**
		 * To simulate rolling two dice simultaneously
		 * and print the rolled values to terminal
		 * @return array containing two integers in the range [1, 6] (both inclusive)
		 */
		public static int[] rollTwoAndShow() {
			int[] rolls = new int[]{rollOne(), rollOne()};
			UI.print(String.format("You rolled %d and %d.", rolls[0], rolls[1]));
			return rolls;
		}
	}

	public static void main(String[] args) {}
}
