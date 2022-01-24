package files;

import files.gameui.UI;

import java.util.ArrayList;
import java.util.List;

public class GameData {
	static List<Locations.Location> gameMap = new ArrayList<>();
	static Player player = new Player();

	public GameData() {
		initialiseLocations();
	}

	/**
	 * to add all possible locations into GameMap
	 */
	void initialiseLocations() {
		gameMap.add(new Locations.WildernessZero());
		gameMap.add(new Locations.WildernessOne());
		gameMap.add(new Locations.WildernessTwo());
		gameMap.add(new Locations.WildernessThree());
		gameMap.add(new Locations.WildernessFour());
		gameMap.add(new Locations.WildernessFive());
		gameMap.add(player.getLocation());
	}

	public void start() {
		UI.print("Game started");
		while (true) {
			userTravel();
			boolean toContinue = userToSearch();
			if (!toContinue) {
				break;
			}
		}
	}

	void userAction() {
		int[] actionRange = printActions();
		int act = Fn.UserInput.readDigitInput("Choose your action: ", actionRange[0], actionRange[1]);
		// do action
	}

	void userTravel() {
		boolean travelled = false;
		while (!travelled) {
			printGameMap();
			int regionIdx = Fn.UserInput.readDigitInput("Choose where to go: ", 1, 7) - 1;
			travelled = player.setLocation(gameMap.get(regionIdx));
		}
	}

	boolean userToSearch() {
		if (Fn.UserInput.readYesNoInput("Do you want to explore this region?")) {
			return player.getLocation().regionSearch();
		}
		return false;
	}

	void printGameMap() {
		for (int i = 0; i < gameMap.size(); i++) {
			UI.fastPrint(String.format("%d: %s", i + 1, gameMap.get(i).getName()));
		}
	}

	int[] printActions() {
		// to implement
		return new int[]{};
	}

	public static void main(String[] args) {
		// test run
		new GameData();
		new Artifacts.ConnectorZero().runConnection();
	}
}
