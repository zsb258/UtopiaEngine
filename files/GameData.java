package files;

import gameui.UI;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GameData {
	protected static List<Locations.Location> gameMap = new ArrayList<>();
	static Player player;

	GameData() {
		player = new Player();
		initialiseLocations();
	}

	void initialiseLocations() {
		gameMap.add(new Locations.WildernessZero());
		gameMap.add(new Locations.WildernessOne());
		gameMap.add(new Locations.WildernessTwo());
		gameMap.add(new Locations.WildernessThree());
		gameMap.add(new Locations.WildernessFour());
		gameMap.add(new Locations.WildernessFive());
		gameMap.add(player.getLocation());
	}

	void start() {
		UI.print("Game started");
		while (true) {
			userTravel();
			boolean toContinue = userToSearch();
			if (!toContinue) {
				break;
			}
		}
	}

	void userTravel() {
		boolean travelled = false;
		while (!travelled) {
			printGameMap();
			int regionIdx = UI.readDigitInput("Choose where to go: ", 1, 7) - 1;
			travelled = player.setLocation(gameMap.get(regionIdx));
		}
	}

	boolean userToSearch() {
		if (UI.readYesNoInput("Do you want to explore this region?")) {
			return player.getLocation().regionSearch();
		}
		return false;
	}

	void printGameMap() {
		for (int i = 0; i < gameMap.size(); i++) {
			UI.fastPrint(String.format("%d: %s", i + 1, gameMap.get(i).getName()));
		}
	}

	public static void main(String[] args) {
		GameData G = new GameData();
		G.start();
	}
}
