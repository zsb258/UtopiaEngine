package files;

import files.gameui.UI;

import java.util.*;

public class Locations {
	public final static int MAX_SEARCHES = 6;

	interface Location {
		void foundComponent(Player player, int num);
		void foundArtifact(Player player);
		void foundTreasure(Player player);
		Monsters.Monster getMonster(int level);
		String getName();
		boolean regionSearch();
		Artifacts.Artifact getArtifact();
	}

	static class LocationImpl implements Location {
		String name;
		int component;
		int index;
		Artifacts.Artifact artifact;
		Treasures.Treasure treasure;
		boolean foundArtifact = false;
		boolean foundTreasure = false;
		List<Monsters.Monster> monsters;
		boolean[] searchTracker = new boolean[Locations.MAX_SEARCHES];

		@Override
		public void foundComponent(Player player, int num) {
			for (int i = 0; i < num; i++) {
				player.obtainOneComponent(component);
			}
			UI.print(String.format("You gained %d %s.",
					num, Player.PlayerConstants.Materials.toName(component)));
		}

		@Override
		public void foundArtifact(Player player) {
			if (!foundArtifact) {
				player.getArtifact(artifact);
				foundArtifact = true;
				UI.print(String.format("You gained Artifact: %s.", artifact.getName()));
				artifact = null;
				return;
			}
			foundComponent(player, 2);
		}

		@Override
		public void foundTreasure(Player player) {
			if (!foundTreasure) {
				player.getTreasure(treasure);
				foundTreasure = true;
				UI.print(String.format("You gained Treasure: %s.", treasure.getName()));
				treasure = null;
			}
		}

		@Override
		public Monsters.Monster getMonster(int level) {
			return monsters.get(level - 1);
		}

		@Override
		public String getName() {
			return this.name;
		}

		@Override
		public boolean regionSearch() {
			int currSearch = 0;
			while (currSearch < MAX_SEARCHES) {
				new SingleSearch(this).run();
				if (! Fn.UserInput.readYesNoInput("Do you still want to search in this region?") ) {
					return false;
				}
				currSearch++;
			}
			// max number of searches reached
			// immediately get artifact
			this.foundArtifact(GameData.player);
			return true;
		}

		@Override
		public Artifacts.Artifact getArtifact() {
			return this.artifact;
		}

	}

	static class WildernessZero extends LocationImpl {
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
			searchTracker[0] = true;
			searchTracker[1] = true;
			searchTracker[2] = false;
			searchTracker[3] = true;
			searchTracker[4] = false;
			searchTracker[5] = false;
		}
	}

	static class WildernessOne extends LocationImpl {
		WildernessOne() {
			super();
			this.name = "The Great Wilds";
			this.component = Player.PlayerConstants.Materials.ONE;
			this.index = RegionIndex.ONE;
			this.treasure = new Treasures.TreasureOne();
			this.artifact = new Artifacts.ArtifactOne();
		}
	}

	static class WildernessTwo extends LocationImpl {
		WildernessTwo() {
			super();
			this.name = "Root-Strangled Marshes";
			this.component = Player.PlayerConstants.Materials.TWO;
			this.index = RegionIndex.TWO;
			this.treasure = new Treasures.TreasureTwo();
			this.artifact = new Artifacts.ArtifactTwo();
		}
	}

	static class WildernessThree extends LocationImpl {
		WildernessThree() {
			super();
			this.name = "Glassrock Canyon";
			this.component = Player.PlayerConstants.Materials.THREE;
			this.index = RegionIndex.THREE;
			this.treasure = new Treasures.TreasureThree();
			this.artifact = new Artifacts.ArtifactThree();
		}
	}

	static class WildernessFour extends LocationImpl {
		WildernessFour() {
			super();
			this.name = "Ruined City of the Ancients";
			this.component = Player.PlayerConstants.Materials.FOUR;
			this.index = RegionIndex.FOUR;
			this.treasure = new Treasures.TreasureFour();
			this.artifact = new Artifacts.ArtifactFour();
		}
	}

	static class WildernessFive extends LocationImpl {
		WildernessFive() {
			super();
			this.name = "The Fiery Maw";
			this.component = Player.PlayerConstants.Materials.FIVE;
			this.index = RegionIndex.FIVE;
			this.treasure = new Treasures.TreasureFive();
			this.artifact = new Artifacts.ArtifactFive();
		}
	}

	static class Workshop extends LocationImpl {
		Workshop() {
			super();
			this.name = "Workshop";
			this.index = RegionIndex.WORKSHOP;
		}
	}

	static class RegionIndex {
		private RegionIndex(){}
		public final static int ZERO = 0;
		public final static int ONE = 1;
		public final static int TWO = 2;
		public final static int THREE = 3;
		public final static int FOUR = 4;
		public final static int FIVE = 5;
		public final static int WORKSHOP = 6;
	}

	static class SingleSearch {
		Location location;
		char[] grid;
		int searchValue;

		SingleSearch(Location location) {
			this.location = location;
			grid = emptyResultGrid();
		}

		void run() {
			for (int i = 0; i < 3; i++) {
				int[] rolls, inputs;

				while (true) {
					printSearchGrid();
					rolls = Fn.Dice.rollTwoAndShow();
					System.out.println("Please enter two positions to fill grid:");
					inputs = Fn.UserInput.readTwoDigitsInput(
							"Please enter two positions to fill grid:", 1, 6);

					if (inputs[0] >= 0 && inputs[0] < 6 && inputs[1] >= 0 && inputs[1] < 6) {
						if (grid[inputs[0]] == '_' && grid[inputs[1]] == '_') {
							break;
						}
					}
					System.out.println("Your inputs are invalid.");
				}

				grid[inputs[0]] = (char) (rolls[0] + '0');
				grid[inputs[1]] = (char) (rolls[1] + '0');
			}

			printSearchGrid();
			searchValue = resolveGrid();
			UI.fPrintNum("Your search value is %d.", searchValue);

			resolveSearchResult();
		}

		char[] emptyResultGrid() {
			char[] res = new char[6];
			Arrays.fill(res, '_');
			return res;
		}

		void printSearchGrid() {
			String gridString = String.format("[%c] [%c] [%c]%n(%d) (%d) (%d)%n[%c] [%c] [%c]%n(%d) (%d) (%d)",
					grid[0], grid[1], grid[2], 1, 2, 3, grid[3], grid[4], grid[5], 4, 5, 6);
			UI.immediatePrint(gridString);
		}

		int resolveGrid() {
			int firstNum = (grid[0] - '0') * 100 + (grid[1] - '0') * 10 + (grid[2] - '0');
			int secondNum = (grid[3] - '0') * 100 + (grid[4] - '0') * 10 + (grid[5] - '0');
			return firstNum - secondNum;
		}

		void resolveSearchResult() {
			int searchResult = Results.resolveSearchValue(searchValue);
			UI.print(String.format("Your search yields %s.", Results.toName(searchResult)));
			//noinspection EnhancedSwitchMigration
			switch (searchResult) {
				case Results.ENCOUNTER:
					int encounterLevel = Results.encounterLevel(searchValue);
					Monsters.Monster enemy = location.getMonster(encounterLevel);
					enemy.combat();
					break;
				case Results.COMPONENT:
					location.foundComponent(GameData.player, 1);
					break;
				case Results.INACTIVE_ARTIFACT:
					location.foundArtifact(GameData.player);
					break;
			}
		}
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

		static int encounterLevel(int i) {
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

	public static void main(String[] args) {}
}
