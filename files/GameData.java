package files;

import java.util.HashMap;
import java.util.List;

public class GameData {
	List<Locations.Location> possibleLocations;
	Player player;

	GameData() {
		initialiseLocations();
		player = new Player();
	}

	void initialiseLocations() {
		possibleLocations.add(new Locations.WildernessZero());
		possibleLocations.add(new Locations.WildernessOne());
		possibleLocations.add(new Locations.WildernessTwo());
		possibleLocations.add(new Locations.WildernessThree());
		possibleLocations.add(new Locations.WildernessFour());
		possibleLocations.add(new Locations.WildernessFive());
		possibleLocations.add(new Locations.Workshop());
	}
}
