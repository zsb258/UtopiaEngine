package files;

import files.gameui.UI;

import java.util.Arrays;
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

		public static int[] rollTwo() {
			return new int[]{rollOne(), rollOne()};
		}
	}

	static class Grid {

		static class ArtifactGrid {
			final static int ACTIVATION_ENERGY = 4;
			char[] attemptOne = emptyResultGrid();
			boolean[] resultOne = new boolean[4];
			char[] attemptTwo = emptyResultGrid();
			boolean[] resultTwo = new boolean[4];
			int energy = 0;

			ArtifactGrid() {
				Arrays.fill(resultOne, false);
				Arrays.fill(resultTwo, false);
			}

			void runActivation() {
				do {
					this.printActivationView();
					int[] rolls = Dice.rollTwo();
					UI.print(String.format("You rolled %d and %d.", rolls[0], rolls[1]));
					int[] inputs = UserInput.readTwoDigitsInput(
							"Choose how to place the values:", 1, 8);
					while (attemptOne[inputs[0] - 1] != '_' || attemptOne[inputs[1] - 1] != '_') {
						UI.print("Your inputs are invalid.");
						inputs = UserInput.readTwoDigitsInput(
								"Choose how to place the values:", 1, 8);
					}
					attemptOne[inputs[0] - 1] = (char) (rolls[0] + '0');
					attemptOne[inputs[1] - 1] = (char) (rolls[1] + '0');
					this.calculateGridCols(attemptOne, resultOne);
				} while (!this.isFilled(resultOne));
				this.updateEnergy();
				UI.print(String.format("You gained %d energy from the first activation attempt", energy));
				this.printActivationView();
				if (energy >= ACTIVATION_ENERGY) {
					// to implement
				}
			}

			char[] emptyResultGrid() {
				char[] res = new char[12];
				Arrays.fill(res, '_');
				return res;
			}

			void calculateGridCols(char[] grid, boolean[] result) {
				for (int i = 0; i < 4; i++) {
					if (result[i]) {
						continue;
					}
					if (grid[i] != '_' && grid[i + 4] != '_') {
						if (grid[i] == grid[i + 4]) {
							// clear column
							UI.print(String.format("Column %d is reset.", i + 1));
							grid[i] = '_';
							grid[i + 4] = '_';
							continue;
						}
						int val = grid[i] - grid[i + 4];
						if (val == 4) {
							grid[i + 8] = '1';
						}
						else if (val == 5) {
							grid[i + 8] = '2';
						}
						else {
							grid[i + 8] = 'X'; // lock
						}
						result[i] = true;
						if (val < 0) {
							GameData.player.receiveDamage(1);
							UI.print("You lose 1 HP from Artifact backfire.");
							UI.print(String.format("You are now at %d HP", GameData.player.getHp()));
						}
					}
				}
			}

			void updateEnergy() {
				if (isFilled(resultOne)) {
					for (int i = 8; i < attemptOne.length; i++) {
						energy += (attemptOne[i] != 'X') ? attemptOne[i] - '0' : 0;
					}
				}
				if (isFilled(resultTwo)) {
					for (int i = 8; i < attemptTwo.length; i++) {
						energy += (attemptTwo[i] != 'X') ? attemptTwo[i] - '0' : 0;
					}
				}
			}

			boolean isFilled(boolean[] result) {
				int i = 0;
				for (boolean b : result) {
					i += b ? 1 : 0;
				}
				return i == 4;
			}

			void printGrid(char[] grid) {
				//noinspection StringBufferReplaceableByString
				StringBuilder s = new StringBuilder();
				s.append(String.format("[%c] [%c] [%c] [%c]", grid[0], grid[1], grid[2], grid[3]));
				s.append(System.lineSeparator());
				s.append(String.format("[%c] [%c] [%c] [%c]", grid[4], grid[5], grid[6], grid[7]));
				s.append(System.lineSeparator());
				s.append(String.format("{%c} {%c} {%c} {%c}", grid[8], grid[9], grid[10], grid[11]));
				UI.immediatePrint(s.toString());
			}

			void printActivationView() {
				UI.immediatePrint("First attempt: ");
				printGrid(attemptOne);
				UI.immediatePrint("Second attempt: ");
				printGrid(attemptTwo);
				UI.immediatePrint("Energy: " + energy);
				if (energy < 4) {
					UI.immediatePrint("Gain 4 or more energy within two attempts to activate the Artifact.");
				}
			}
		}
	}

	public static void main(String[] args) {
		new Grid.ArtifactGrid().runActivation();
	}
}
