package files;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Locations {
	public final static int MAX_SEARCHES = 6;

	protected static class Location {
		int component;
		boolean foundArtifact = false;
		boolean foundTreasure = false;
	}

	protected static class HalebeardPeak extends Location {
		HalebeardPeak() {
			this.component = Player.PlayerConstants.Materials.SILVER;
		}
	}

	protected static class Wilderness {
		private Wilderness(){}
		public final static int WORKSHOP = 0;
		public final static int HALEBEARD_PEAK = 1;
		public final static int THE_GREAT_WILDS = 2;
		public final static int ROOT_STRANGLED_MARSHES = 3;
		public final static int GLASSROCK_CANYON = 4;
		public final static int RUINED_CITY = 5;
		public final static int THE_FIERY_MAW = 6;
	}

	static class Results {
		private Results(){}
		public final static int ENCOUNTER = 0;
		public final static int COMPONENT = 1;
		public final static int INACTIVE_ARTIFACT = 2;
		public final static int ACTIVATED_ARTIFACT = 3;

		static int resolveSearchValue(int value) {
			if (value >= 100 || value < 0) {
				return Results.ENCOUNTER;
			}
			if (value >= 11) {
				return Results.COMPONENT;
			}
			if (value >= 1) {
				return Results.INACTIVE_ARTIFACT;
			}
			return Results.ACTIVATED_ARTIFACT;
		}

		static String toName(int i) {
			return switch (i) {
				case ENCOUNTER -> "an encounter";
				case COMPONENT -> "a component";
				case INACTIVE_ARTIFACT -> "an inactive artifact";
				case ACTIVATED_ARTIFACT -> "an activated artifact";
				default -> "";
			};
		}

		int encounterLevel(int i) {
			if ((i >= 100 && i <= 199) || (i <= -1 && i >= -100)) {
				return 1;
			}
			if ((i >= 200 && i <= 299) || (i <= -101 && i >= -200)) {
				return 1;
			}
			if ((i >= 300 && i <= 399) || (i <= -201 && i >= -300)) {
				return 1;
			}
			if ((i >= 400 && i <= 499) || (i <= -301 && i >= -400)) {
				return 1;
			}
			if ((i >= 500 && i <= 555) || (i <= -401 && i >= -555)) {
				return 1;
			}
			return 0;
		}
	}

	static int valueToLocation() {
		return 0;
	}

	void searchHalebeardPeak() {
		int currSearch = 0;

	}

	void singleSearch() {
		Random dice = new Random();
		int[] grid = emptyResultGrid();
		for (int rolls = 0; rolls < 3; rolls++) {
			int firstRoll = dice.nextInt(6) + 1;
			int secondRoll = dice.nextInt(6) + 1;
			int firstPos, secondPos;

			Scanner in = new Scanner(System.in);
			while (true) {
				System.out.println(gridToString(grid));
				System.out.printf("You rolled %d and %d %n", firstRoll, secondRoll);
				System.out.println("Please enter the positions to fill grid:");

				firstPos = inputToGridIndices(in.nextInt());
				secondPos = inputToGridIndices(in.nextInt());
				if (firstPos > 0 && firstPos < 6 && secondPos > 0 && secondPos < 6) {
					if (grid[firstPos] != 0 && grid[secondPos] != 0) {
						break;
					}
				}

				System.out.println("Your inputs are invalid.");
			}
			in.close();

			grid[firstPos] = firstRoll;
			grid[secondPos] = secondRoll;
		}

		int searchValue = resolveGrid(grid);
		System.out.printf("Your search value is %d. %n", searchValue);
		int searchResult = Results.resolveSearchValue(searchValue);
		System.out.printf("Your search yields %s. %n", Results.toName(searchResult));
	}

	int[] emptyResultGrid() {
		int[] res = new int[6];
		Arrays.fill(res, 0);
		return res;
	}

	int inputToGridIndices(int i) {
		return i - 1;
	}

	String gridToString(int[] grid) {
		return String.format("[%d] [%d] [%d] %n[%d] [%d] [%d] %n",
				grid[0], grid[1], grid[2], grid[3], grid[4], grid[5]);
	}

	int resolveGrid(int[] grid) {
		int firstNum = grid[0] * 100 + grid[1] * 10 + grid[2];
		int secondNum = grid[3] * 100 + grid[4] * 10 + grid[5];
		return firstNum - secondNum;
	}

	public void run() {
		System.out.println(gridToString(new int[]{1,2,3,4,5,6}));
	}

	public static void main(String[] args) {
		new Locations().run();
	}
}
