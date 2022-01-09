package files;

import java.util.*;

public class Locations {
	public final static int MAX_SEARCHES = 6;

	protected interface Location {
		void foundComponent(Player player, int num);
		void foundArtifact(Player player);
		void foundTreasure(Player player);
	}

	protected static class LocationClass implements Location {
		String name;
		int component;
		int index;
		Artifacts.Artifact artifact;
		Treasures.Treasure treasure;
		boolean foundArtifact = false;
		boolean foundTreasure = false;
		List<Monsters.Monster> monsters;
		boolean[] searchTracker = new boolean[MAX_SEARCHES];

		@Override
		public void foundComponent(Player player, int num) {
			for (int i = 0; i < num; i++) {
				player.getOneComponent(index);
			}
		}

		@Override
		public void foundArtifact(Player player) {
			if (artifact != null) {
				player.getArtifact(artifact);
				artifact = null;
				return;
			}
			foundComponent(player, 2);
		}

		@Override
		public void foundTreasure(Player player) {
			if (treasure != null) {
				player.getTreasure(treasure);
				treasure = null;
			}
		}
	}

	protected static class WildernessZero extends LocationClass {
		WildernessZero() {
			super();
			this.name = "Halebeard Peak";
			this.component = Player.PlayerConstants.Materials.ZERO;
			this.index = RegionIndex.ZERO;
			this.treasure = new Treasures.TreasureZero();
			this.artifact = new Artifacts.ArtifactZero();
			this.monsters = Monsters.RegionZeroMonsters.getMonsters();
			for (Monsters.Monster monster : monsters) {
				monster.setLocation(this);
			}
		}
	}

	protected static class WildernessOne extends LocationClass {
		WildernessOne() {
			super();
			this.name = "The Great Wilds";
			this.component = Player.PlayerConstants.Materials.ONE;
			this.index = RegionIndex.ONE;
			this.treasure = new Treasures.TreasureOne();
			this.artifact = new Artifacts.ArtifactOne();
		}
	}

	protected static class WildernessTwo extends LocationClass {
		WildernessTwo() {
			super();
			this.name = "Root-Strangled Marshes";
			this.component = Player.PlayerConstants.Materials.TWO;
			this.index = RegionIndex.TWO;
			this.treasure = new Treasures.TreasureTwo();
			this.artifact = new Artifacts.ArtifactTwo();
		}
	}

	protected static class WildernessThree extends LocationClass {
		WildernessThree() {
			super();
			this.name = "Glassrock Canyon";
			this.component = Player.PlayerConstants.Materials.THREE;
			this.index = RegionIndex.THREE;
			this.treasure = new Treasures.TreasureThree();
			this.artifact = new Artifacts.ArtifactThree();
		}
	}

	protected static class WildernessFour extends LocationClass {
		WildernessFour() {
			super();
			this.name = "Ruined City of the Ancients";
			this.component = Player.PlayerConstants.Materials.FOUR;
			this.index = RegionIndex.FOUR;
			this.treasure = new Treasures.TreasureFour();
			this.artifact = new Artifacts.ArtifactFour();
		}
	}

	protected static class WildernessFive extends LocationClass {
		WildernessFive() {
			super();
			this.name = "The Fiery Maw";
			this.component = Player.PlayerConstants.Materials.FIVE;
			this.index = RegionIndex.FIVE;
			this.treasure = new Treasures.TreasureFive();
			this.artifact = new Artifacts.ArtifactFive();
		}
	}

	protected static class Workshop extends LocationClass {
		Workshop() {
			super();
			this.name = "Workshop";
			this.index = RegionIndex.WORKSHOP;
		}
	}

	protected static class RegionIndex {
		private RegionIndex(){}
		public final static int ZERO = 0;
		public final static int ONE = 1;
		public final static int TWO = 2;
		public final static int THREE = 3;
		public final static int FOUR = 4;
		public final static int FIVE = 5;
		public final static int WORKSHOP = 6;
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
			//noinspection EnhancedSwitchMigration
			switch (i) {
				case ENCOUNTER: return "a monster encounter";
				case COMPONENT: return "a component";
				case INACTIVE_ARTIFACT: return "an inactive artifact";
				case ACTIVATED_ARTIFACT: return "an activated artifact";
				default: return "";
			}
		}

		int encounterLevel(int i) {
			if ((i >= 100 && i <= 199) || (i <= -1 && i >= -100)) {
				return 1;
			}
			if ((i >= 200 && i <= 299) || (i <= -101 && i >= -200)) {
				return 2;
			}
			if ((i >= 300 && i <= 399) || (i <= -201 && i >= -300)) {
				return 3;
			}
			if ((i >= 400 && i <= 499) || (i <= -301 && i >= -400)) {
				return 4;
			}
			if ((i >= 500 && i <= 555) || (i <= -401 && i >= -555)) {
				return 5;
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
		char[] grid = emptyResultGrid();

		Scanner in = new Scanner(System.in);
		for (int rolls = 0; rolls < 3; rolls++) {
			int firstRoll = dice.nextInt(6) + 1;
			int secondRoll = dice.nextInt(6) + 1;
			int firstPos, secondPos;

			while (true) {
				System.out.println(gridToString(grid));
				System.out.printf("You rolled %d and %d.%n", firstRoll, secondRoll);
				System.out.println("Please enter two positions to fill grid:");

				firstPos = inputToGridIndices(in.nextInt());
				secondPos = inputToGridIndices(in.nextInt());

				if (firstPos >= 0 && firstPos < 6 && secondPos >= 0 && secondPos < 6) {
					if (grid[firstPos] == '_' && grid[secondPos] == '_') {
						break;
					}
				}
				System.out.println("Your inputs are invalid.");
			}

			grid[firstPos] = (char) (firstRoll + '0');
			grid[secondPos] = (char) (secondRoll + '0');
		}
		in.close();

		System.out.println(gridToString(grid));
		int searchValue = resolveGrid(grid);
		System.out.printf("Your search value is %d.%n", searchValue);
		int searchResult = Results.resolveSearchValue(searchValue);
		System.out.printf("Your search yields %s.%n", Results.toName(searchResult));
	}

	char[] emptyResultGrid() {
		char[] res = new char[6];
		Arrays.fill(res, '_');
		return res;
	}

	int inputToGridIndices(int i) {
		return i - 1;
	}

	String gridToString(char[] grid) {
		return String.format("[%c] [%c] [%c]%n(%d) (%d) (%d)%n[%c] [%c] [%c]%n(%d) (%d) (%d)",
				grid[0], grid[1], grid[2], 1, 2, 3, grid[3], grid[4], grid[5], 4, 5, 6);
	}

	int resolveGrid(char[] grid) {
		int firstNum = (grid[0] - '0') * 100 + (grid[1] - '0') * 10 + (grid[2] - '0');
		int secondNum = (grid[3] - '0') * 100 + (grid[4] - '0') * 10 + (grid[5] - '0');
		return firstNum - secondNum;
	}

	public void run() {
		singleSearch();
	}

	public static void main(String[] args) {
		new Locations().run();
	}
}
