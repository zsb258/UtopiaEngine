package files;

public class Treasures {
	interface Treasure {
		String getName();
	}

	private static class TreasureImpl implements Treasure {
		String name, effect;

		@Override
		public String getName() {
			return this.name;
		}
	}

	static class TreasureZero extends TreasureImpl {
		String name = "Ice Plate";
		String effect = "-1 to attack range of all Monsters";
	}

	static class TreasureOne extends TreasureImpl {
		String name = "Bracelet of Ios";
		String effect = "Begin first activation attempt with 1 free energy point";
	}

	static class TreasureTwo extends TreasureImpl {
		String name = "Shimmering Moonlace";
		String effect = "You may ignore encounters";
	}

	static class TreasureThree extends TreasureImpl {
		String name = "Scale of the Infinity Wurm";
		String effect = "Recover 1 HP each event day";
	}

	static class TreasureFour extends TreasureImpl {
		String name = "The Ancient Record";
		String effect = "Change any one link value to zero (only once per game)";
		boolean used = false;
	}

	static class TreasureFive extends TreasureImpl {
		String name = "The Molten Shard";
		String effect = "+1 to your attack range";
	}
}
