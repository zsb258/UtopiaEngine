package files;

import javax.swing.event.TreeSelectionListener;

public class Treasures {
	interface Treasure {
		String getName();
	}

	private static class TreasureClass implements Treasure {
		String name, effect;

		@Override
		public String getName() {
			return this.name;
		}
	}

	protected static class TreasureZero extends TreasureClass {
		String name = "Ice Plate";
		String effect = "-1 to attack range of all Monsters";
	}

	protected static class TreasureOne extends TreasureClass {
		String name = "Bracelet of Ios";
		String effect = "Begin first activation attempt with 1 free energy point";
	}

	protected static class TreasureTwo extends TreasureClass {
		String name = "Shimmering Moonlace";
		String effect = "You may ignore encounters";
	}

	protected static class TreasureThree extends TreasureClass {
		String name = "Scale of the Infinity Wurm";
		String effect = "Recover 1 HP each event day";
	}

	protected static class TreasureFour extends TreasureClass {
		String name = "The Ancient Record";
		String effect = "Change any one link value to zero (only once per game)";
		boolean used = false;
	}

	protected static class TreasureFive extends TreasureClass {
		String name = "The Molten Shard";
		String effect = "+1 to your attack range";
	}
}
