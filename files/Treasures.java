package files;

public class Treasures {
	interface Treasure {}

	protected static class TreasureZero implements Treasure {
		String name = "Ice Plate";
		String effect = "-1 to attack range of all Monsters";
	}

	protected static class TreasureOne implements Treasure {
		String name = "Bracelet of Ios";
		String effect = "Begin first activation attempt with 1 free energy point";
	}

	protected static class TreasureTwo implements Treasure {
		String name = "Shimmering Moonlace";
		String effect = "You may ignore encounters";
	}

	protected static class TreasureThree implements Treasure {
		String name = "Scale of the Infinity Wurm";
		String effect = "Recover 1 HP each event day";
	}

	protected static class TreasureFour implements Treasure {
		String name = "The Ancient Record";
		String effect = "Change any one link value to zero (only once per game)";
		boolean used = false;
	}

	protected static class TreasureFive implements Treasure {
		String name = "The Molten Shard";
		String effect = "+1 to your attack range";
	}
}
