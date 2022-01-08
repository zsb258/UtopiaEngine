package files;

public class Treasures {
	protected static class Treasure {
		boolean found = false;
	}

	protected static class IcePlate extends Treasure {
		String name = "Ice Plate";
		String effect = "-1 to attack range of all Monsters";
		IcePlate() {

		}
	}
}
