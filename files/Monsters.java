package files;

public class Monsters {
	protected static class Monster {
		String name;
		int inRegion;
		int level;
		int[] monsterAttackRange;
		int[] playerAttackRange;
		boolean hasComponent = true;
		boolean hasTreasure = false;
	}

	protected static class IceBear extends Monster {
		IceBear() {
			this.name = "Ice Bear";
			this.inRegion = Locations.Wilderness.HALEBEARD_PEAK;
			this.level = 1;
			this.monsterAttackRange = new int[]{1, 1};
			this.playerAttackRange = new int[]{5, 6};
		}
	}
}
