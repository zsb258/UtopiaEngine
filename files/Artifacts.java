package files;

import files.Fn.Dice;
import files.gameui.UI;
import files.Fn.UserInput;

import java.util.Arrays;

public class Artifacts {

	interface Artifact {
		void foundByPlayer();
		void activateArtifact();
		String getName();
		boolean isActivated();
		void setActivated();
		boolean notFound();
	}

	private static class ArtifactImpl implements Artifact {
		String name = "Artifact name";
		String effect;
		int inRegion;
		boolean found = false;
		boolean activated = false;
		boolean used;

		@Override
		public void foundByPlayer() {
			this.found = true;
		}

		@Override
		public void activateArtifact() {
			new ArtifactActivator(this).runActivation();
		}

		@Override
		public String getName() {
			return this.name;
		}

		@Override
		public boolean isActivated() {
			return this.activated;
		}

		@Override
		public void setActivated() {
			this.activated = true;
		}

		@Override
		public boolean notFound() {
			return !this.found;
		}
	}

	static class ArtifactZero extends ArtifactImpl {
		ArtifactZero() {
			super();
			name = "Seal of Balance";
			effect = "Ignore all events in any one region (only once per game)";
			inRegion = Locations.RegionIndex.ZERO;

			used = false;
		}
	}

	static class ArtifactOne extends ArtifactImpl {
		ArtifactOne() {
			super();
			name = "Hermetic Mirror";
			effect = "Subtract up to 10 from any Search in Halebeard Peak and the Fiery Maw";
			inRegion = Locations.RegionIndex.TWO;
		}
	}

	static class ArtifactTwo extends ArtifactImpl {
		ArtifactTwo() {
			super();
			name = "Void Gate";
			effect = "Recover from unconsciousness in four days instead of six";
			inRegion = Locations.RegionIndex.THREE;
		}
	}

	static class ArtifactThree extends ArtifactImpl {
		ArtifactThree() {
			super();
			name = "Golden Chassis";
			effect = "+1 to each die while in combat against a spirit encounter (S)";
			inRegion = Locations.RegionIndex.THREE;
		}
	}

	static class ArtifactFour extends ArtifactImpl {
		ArtifactFour() {
			super();
			name = "Scrying Lens";
			effect = "Subtract up to 10 from any Search in Root-Strangled Marshes and Glassrock Canyon";
			inRegion = Locations.RegionIndex.FOUR;
		}
	}

	static class ArtifactFive extends ArtifactImpl {
		ArtifactFive() {
			super();
			name = "Crystal Battery";
			effect = "Spend any three components to recharge one tool belt item";
			inRegion = Locations.RegionIndex.FIVE;
		}
	}

	static class ArtifactActivator {
		final static int ACTIVATION_ENERGY = 4;
		char[][] attempts = new char[][]{emptyResultGrid(), emptyResultGrid()};
		boolean[][] results = new boolean[2][4];
		int energy = 0;
		Artifact artifact;

		ArtifactActivator(Artifact artifact) {
			Arrays.fill(results[0], false);
			Arrays.fill(results[1], false);
			this.artifact = artifact;
		}

		void runActivation() {
			if (artifact.isActivated()) {
				UI.print("The Artifact is already active.");
				return;
			}
			for (int i = 0; i < attempts.length; i++) {
				do {
					this.printActivationView();
					int[] rolls = Dice.rollTwoAndShow();
					int[] inputs = UserInput.readTwoDigitsInput(
							"Choose how to place the values:", 1, 8);
					while (attempts[i][inputs[0] - 1] != '_' || attempts[i][inputs[1] - 1] != '_') {
						UI.print("Your inputs are invalid.");
						inputs = UserInput.readTwoDigitsInput(
								"Choose how to place the values:", 1, 8);
					}
					attempts[i][inputs[0] - 1] = (char) (rolls[0] + '0');
					attempts[i][inputs[1] - 1] = (char) (rolls[1] + '0');
					this.calculateGridCols(attempts[i], results[i]);
				} while (!this.isFilled(results[i]));
				this.updateEnergy();
				UI.print(String.format("You gained %d energy from the first activation attempt", energy));
				this.printActivationView();
				if (energy >= ACTIVATION_ENERGY) {
					artifact.setActivated();
					UI.print(String.format(
							"You successfully activated Artifact :%s", artifact.getName()));
					// needs to deal with excess activation energy
					// to be implemented
					return;
				}
				UI.print("Begin your second attempt.");
			}
			// after both attempts are over
			UI.print(String.format(
					"You failed to activate Artifact :%s", artifact.getName()));
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
						GameData.player.receiveDamage(1, "Artifact backfire");
					}
				}
			}
		}

		void updateEnergy() {
			for (int i = 0; i < attempts.length; i++) {
				if (isFilled(results[i])) {
					for (int j = 8; j < attempts[i].length; j++) {
						energy += (attempts[i][j] != 'X') ? attempts[i][j] - '0' : 0;
					}
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
			printGrid(attempts[0]);
			UI.immediatePrint("Second attempt: ");
			printGrid(attempts[1]);
			UI.immediatePrint("Energy: " + energy);
			if (energy < 4) {
				UI.immediatePrint("Gain 4 or more energy within two attempts to activate the Artifact.");
			}
		}
	}

	interface ArtifactConnector {}

	private static class ArtifactConnectorImpl implements ArtifactConnector {
		int requiredCompt;
		Artifact artifact1, artifact2;
		char[] grid;
		boolean[] result;
		boolean toTerminate = false;

		private ArtifactConnectorImpl() {
			grid = this.emptyResultGrid();
			result = new boolean[3];
			Arrays.fill(result, false);
			//noinspection UnnecessaryLocalVariable
			final int PLACEHOLDER_VALUE = 0;
			requiredCompt = PLACEHOLDER_VALUE;
		}

		void runConnection() {
			UI.print("Attempting connection...");
			if (artifact1.notFound() || artifact2.notFound()) {
				// cannot start connection if either artifact is not found
				if (artifact1.notFound() && artifact2.notFound()) {
					UI.print(String.format(
							"Unable to start connection because you have not found %s and %s.",
							artifact1.getName(), artifact2.getName()));
				} else if (artifact1.notFound()) {
					UI.print(String.format(
							"Unable to start connection because you have not found %s.",
							artifact1.getName()));
				} else if (artifact2.notFound()) {
					UI.print(String.format(
							"Unable to start connection because you have not found %s.",
							artifact2.getName()));
				}
				return;
			}
			UI.print("Starting Connection...");
			while (!isFilled()) {
				int[] rolls = Dice.rollTwoAndShow();
				fillTwo(rolls);
				if (toTerminate) {
					return;
				}
			}
			int val = resolveGrid();
			UI.fPrintNum("The difficulty level of the " +
					"Final Activation has increased by %d.", val);
			GameData.player.addFinalDifficulty(val);
		}

		void fillTwo(int[] rolls) {
			UI.print("Processing each roll...");
			fillOne(rolls[0]);
			if (toTerminate) {
				return;
			}
			if (isFilled()) {
				UI.print("The connect grid has been filled");
				UI.fPrintNum("The digit %d is automatically discarded.", rolls[1]);
				GameData.player.addToWaste(rolls[1]);
				return;
			}
			fillOne(rolls[1]);
		}

		void fillOne(int val) {
			printGrid();
			boolean toDiscard = UserInput.readYesNoInput(String.format(
					"Do you want to discard the digit %d?" + UI.SEP +
							"(If you discard, this digit will go to the wastebasket.)",
					val));
			if (toDiscard) {
				GameData.player.addToWaste(val);
				return;
			}
			int input = UserInput.readDigitInput(String.format("Choose where to place the digit %d", val), 1, 6);
			while (grid[input - 1] != '_') {
				UI.print("Your input is invalid.");
				input = UserInput.readDigitInput(String.format("Choose where to place the digit %d", val), 1, 6);
			}
			grid[input - 1] = (char) ('0' + val);
			UI.fPrintNum("You have placed the digit %d", val);
			calculateGridCols();
		}

		void calculateGridCols() {
			for (int i = 0; i < 3; i++) {
				if (result[i]) {
					continue;
				}
				if (grid[i] != '_' && grid[i + 3] != '_') {
					int val = grid[i] - grid[i + 3];
					grid[i + 6] = (char) ('0' + val);
					result[i] = true;
					if (val < 0) {
						grid[i + 6] = 'X';
						printGrid();
						UI.fPrintNum("You obtained a negative result on column %d.", i + 1);
						GameData.player.receiveDamage(1, "Connection fault");
						if (GameData.player.getComponentCount(this.requiredCompt) > 0) {
							UI.print(String.format("You have sufficient %s.",
									Player.PlayerConstants.Materials.toName(this.requiredCompt)));
						}
						else {
							UI.print(String.format("You do not have sufficient %s.",
									Player.PlayerConstants.Materials.toName(this.requiredCompt)));
							UI.print("You are forced to abandon this Connection.");
							toTerminate = true;
							return;
						}
						boolean toProceed = UserInput.readYesNoInput(
								"Do you wish to spend a component and fix the result to be 2?");
						if (toProceed) {
							GameData.player.spendOneComponent(this.requiredCompt);
							grid[i + 6] = '2';
						}
						else {
							toTerminate = true;
							return;
						}
					}
				}
			}
		}

		int resolveGrid() {
			int res = 0;
			for (int i = grid.length - 1; i >= grid.length - 3; i--) {
				res += grid[i] - '0';
			}
			return res;
		}

		char[] emptyResultGrid() {
			char[] res = new char[9];
			Arrays.fill(res, '_');
			return res;
		}

		void printGrid() {
			//noinspection StringBufferReplaceableByString
			StringBuilder s = new StringBuilder("Connector: " + UI.SEP);
			s.append(String.format("[%c] [%c] [%c]", grid[0], grid[1], grid[2]));
			s.append(System.lineSeparator());
			s.append(String.format("[%c] [%c] [%c]", grid[3], grid[4], grid[5]));
			s.append(System.lineSeparator());
			s.append(String.format("{%c} {%c} {%c}", grid[6], grid[7], grid[8]));
			UI.immediatePrint(s.toString());
		}

		boolean isFilled() {
			int count = 0;
			for (int i = grid.length - 1; i >= grid.length - 4; i--) {
				count += grid[i] != '_' ? 1 : 0;
			}
			return count == 4;
		}
	}

	static class ConnectorZero extends ArtifactConnectorImpl {

		ConnectorZero() {
			super();
			// Seal of Balance
			this.artifact1 = GameData.gameMap.get(0).getArtifact();

			// Golden Chassis
			this.artifact2 = GameData.gameMap.get(3).getArtifact();

			// Quartz
			this.requiredCompt = Player.PlayerConstants.Materials.QUARTZ;
		}

	}

	public static void main(String[] args) {

	}
}
